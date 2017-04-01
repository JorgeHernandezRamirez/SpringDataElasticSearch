package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch;

import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity.TeamEntity;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity.UserEntity;
import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class AbstractElasticSearchTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractElasticSearchTest.class);

    @Autowired
    protected ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    protected UserRepository userRepository;

    @Test
    public void shouldBeNotNullObjects(){
        assertNotNull(userRepository);
        assertNotNull(elasticsearchTemplate);
    }

    @Before
    public void initialization(){
        elasticsearchTemplate.deleteIndex(UserEntity.class);
        elasticsearchTemplate.createIndex(UserEntity.class);
        elasticsearchTemplate.putMapping(UserEntity.class);
        elasticsearchTemplate.refresh(UserEntity.class);
        insertMockUsers();
    }

    private void insertMockUsers() {
        userRepository.save(new UserEntity("1", "Admin", "male", "Admin", 0, Arrays.asList("ROLE_ADMIN"), Collections.<TeamEntity>emptyList()));
        userRepository.save(new UserEntity("2", "Jorge", "male", "Hernández Ramírez", 1000, Arrays.asList("ROLE_ADMIN"), Arrays.asList(new TeamEntity("UD. Las Palmas", "Football"), new TeamEntity("Real Madrid", "Football"), new TeamEntity("McLaren", "F1"))));
        userRepository.save(new UserEntity("3", "Jose", "male", "Hernández Ramírez", 500, Arrays.asList("ROLE_USER"), Arrays.asList(new TeamEntity("UD. Las Palmas", "Football"), new TeamEntity("Magnus Carlen", "Chess"))));
        userRepository.save(new UserEntity("4", "Raul", "male", "González Blanco", 10000000, Arrays.asList("ROLE_USER"), Arrays.asList(new TeamEntity("Real Madrid", "Football"), new TeamEntity("Real Madrid", "Basketball"))));
        userRepository.save(new UserEntity("5", "Constanza", "female", "Ramírez Rodríguez", 700, Arrays.asList("ROLE_USER"), Arrays.asList(new TeamEntity("UD. Las Palmas", "Football"))));
    }
}
