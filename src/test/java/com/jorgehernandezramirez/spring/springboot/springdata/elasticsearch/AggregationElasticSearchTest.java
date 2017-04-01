package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch;

import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.configuration.ElasticSearchConfiguration;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity.UserEntity;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.repository.UserRepository;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.service.AggregationsResultsExtractor;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.elasticsearch.action.search.SearchType.COUNT;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.avg;
import static org.elasticsearch.search.aggregations.AggregationBuilders.sum;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ElasticSearchConfiguration.class,loader=AnnotationConfigContextLoader.class)
public class AggregationElasticSearchTest extends AbstractElasticSearchTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationElasticSearchTest.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void shouldPrintNumberOfDocumentsFromGender(){
        final SearchQuery searchQuery = buildSearchQuery(terms("counter_gender").field("gender"));
        final Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new AggregationsResultsExtractor());
        final StringTerms topTags = (StringTerms)aggregations.getAsMap().get("counter_gender");
        topTags.getBuckets().forEach(bucket -> {
            LOGGER.info("{}, {}", bucket.getKeyAsString(), bucket.getDocCount());
        });
    }

    @Test
    public void shouldPrintMoneySumOfAllDocumentes(){
        final SearchQuery searchQuery = buildSearchQuery(sum("sum_money").field("money"));
        final Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new AggregationsResultsExtractor());
        assertNotNull(aggregations);
        assertNotNull(aggregations.asMap().get("sum_money"));
        final InternalSum internalSum = (InternalSum)aggregations.getAsMap().get("sum_money");
        LOGGER.info("{}", internalSum.getValue());
    }

    @Test
    public void shouldPrintNumberOfDocumentsFromGenderAndSumAndAvgMoneyOfEveryone(){
        final SearchQuery searchQuery = buildSearchQuery(terms("counter_gender").field("gender")
                .subAggregation(sum("sum_money").field("money"))
                .subAggregation(avg("avg_money").field("money")));
        final Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new AggregationsResultsExtractor());
        final StringTerms counterBankIds = (StringTerms)aggregations.getAsMap().get("counter_gender");
        counterBankIds.getBuckets().forEach(bucket -> {
            LOGGER.info("{}, {}, {}, {}", bucket.getKeyAsString(), bucket.getDocCount(), ((InternalSum)bucket.getAggregations().getAsMap().get("sum_money")).getValue(),
                    ((InternalAvg)bucket.getAggregations().getAsMap().get("avg_money")).getValue());
        });
    }

    private SearchQuery buildSearchQuery(AbstractAggregationBuilder abstractAggregationBuilder){
        return new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .withIndices("myindex").withTypes("user")
                .addAggregation(abstractAggregationBuilder)
                .build();
    }
}
