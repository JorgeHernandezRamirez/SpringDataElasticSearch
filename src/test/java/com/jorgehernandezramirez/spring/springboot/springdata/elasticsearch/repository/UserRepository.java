package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.repository;

import com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<UserEntity, String> {

    Page<UserEntity> findByUserId(String userId, Pageable pageable);

    List<UserEntity> findByBankId(String bankId);

    List<UserEntity> findByUserIdAndBankId(String userId, String bankId);

    @Query("{\"query\":{\"match\":{\"userId\":\"?0\"}}}")
    List<UserEntity> findByUser(String userId);
}
