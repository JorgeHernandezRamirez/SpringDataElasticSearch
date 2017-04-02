package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch;

import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.configuration.ElasticSearchConfiguration;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity.TeamEntity;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity.UserEntity;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.repository.UserRepository;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.service.AggregationsResultsExtractor;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.sum.InternalSum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.elasticsearch.action.search.SearchType.COUNT;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.search.aggregations.AggregationBuilders.sum;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ElasticSearchConfiguration.class,loader=AnnotationConfigContextLoader.class)
public class ElasticSearchTest extends AbstractElasticSearchTest{

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchTest.class);

    @Test
    public void shouldReadAllDocumentsFromElasticsearch(){
        final List<UserEntity> userEntities = getListUserEntity(userRepository.findAll());
        assertEquals(5, userEntities.size());
    }

    @Test
    public void shouldFindDocumentsBySurname(){
        final List<UserEntity> userEntities = userRepository.findBySurname("Ramírez");
        assertEquals(3, userEntities.size());
    }

    @Test
    public void shouldFindDocumentsByName(){
        final List<UserEntity> userEntities = userRepository.findByName("Jorge");
        assertEquals(1, userEntities.size());
    }

    @Test
    public void shouldFindDocumentsBySurnameAndGender(){
        final List<UserEntity> userEntities = userRepository.findBySurnameAndGender("Ramírez", "female");
        assertEquals(1, userEntities.size());
    }

    @Test
    public void shouldFindDocumentsByRoles(){
        final List<UserEntity> userEntities = userRepository.findByRoles("ROLE_ADMIN");
        assertEquals(2, userEntities.size());
    }

    @Test
    public void shouldFindDocumentsUsingNestedQuery(){
        final QueryBuilder queryBuilder = nestedQuery("teams",
                boolQuery().must(matchQuery("teams.name","Magnus Carlen"))
                           .must(matchQuery("teams.sport", "Chess")));
        final List<UserEntity> persons = getUserListFromQueryBuilder(queryBuilder);
        assertEquals(1, persons.size());
    }

    @Test
    public void shouldNotFindDocumentsUsingNestedQuery(){
        final QueryBuilder queryBuilder = nestedQuery("teams",
                boolQuery().must(matchQuery("teams.name","Magnus Carlen"))
                        .must(matchQuery("teams.sport", "Football")));
        final List<UserEntity> persons = getUserListFromQueryBuilder(queryBuilder);
        assertEquals(0, persons.size());
    }

    private List<UserEntity> getUserListFromQueryBuilder(final QueryBuilder queryBuilder){
        final SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
        return userRepository.search(searchQuery).getContent();
    }

    private List<UserEntity> getListUserEntity(final Iterable<UserEntity> userEntityIterable){
        final List<UserEntity> userEntities = new ArrayList<UserEntity>();
        userEntityIterable.iterator().forEachRemaining(userEntities::add);
        return userEntities;
    }
}
