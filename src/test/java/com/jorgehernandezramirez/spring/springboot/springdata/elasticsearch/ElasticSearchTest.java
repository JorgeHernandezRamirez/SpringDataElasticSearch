package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch;

import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.configuration.ElasticSearchConfiguration;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity.UserEntity;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.repository.UserRepository;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.service.AggregationsResultsExtractor;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
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
import static org.elasticsearch.search.aggregations.AggregationBuilders.sum;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ElasticSearchConfiguration.class,loader=AnnotationConfigContextLoader.class)
public class ElasticSearchTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchTest.class);

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldBeNotNullObjects(){
        assertNotNull(userRepository);
        assertNotNull(elasticsearchTemplate);
    }

    @Test
    public void debeInsertarDatosYLeerlos(){
        elasticsearchTemplate.createIndex(UserEntity.class);
        userRepository.save(new UserEntity("1", "bd314026-ef4e-424f-a880-35238d9e17ae", "0073", 100));
        userRepository.save(new UserEntity("2", "bd314026-ef4e-424f-a880-35238d9e17ae", "0049", 20));
        userRepository.save(new UserEntity("3", "ad314026-ef4e-424f-a880-35238d9e17ae", "0073", 100));
        userRepository.save(new UserEntity("4", "ad314026-ef4e-424f-a880-35238d9e17ae", "0073", 20));
        userRepository.findAll().forEach(userEntity -> {
            LOGGER.info("{}", userEntity);
        });
    }

    @Test
    public void debeLeerLosDatosATravesDeFindByUserId(){
        final Page<UserEntity> userEntityPage = userRepository.findByUserId("bd314026-ef4e-424f-a880-35238d9e17ae", new PageRequest(0, 10));
        userEntityPage.forEach(userEntity -> {
            LOGGER.info("{}", userEntity);
        });
    }

    @Test
    public void debeLeerLosDatosATravesDeFindByUserIdAndBankId(){
        userRepository.findByUserIdAndBankId("bd314026-ef4e-424f-a880-35238d9e17ae", "0073").forEach(userEntity -> {
            LOGGER.info("{}", userEntity);
        });
    }

    @Test
    public void debeLeerLosDatosATravesDeFindByUser(){
        userRepository.findByUser("bd314026-ef4e-424f-a880-35238d9e17ae").forEach(userEntity -> {
            LOGGER.info("{}", userEntity);
        });
    }
}
