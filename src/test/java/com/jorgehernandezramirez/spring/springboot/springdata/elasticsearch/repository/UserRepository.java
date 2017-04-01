package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.repository;

import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<UserEntity, String> {

    List<UserEntity> findByRoles(String role);

    List<UserEntity> findBySurname(String name);

    List<UserEntity> findBySurnameAndGender(String surname, String gender);

    @Query("{\"query\":{\"match\":{\"name\":\"?0\"}}}")
    List<UserEntity> findByName(String name);
}
