package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.service;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.data.elasticsearch.core.ResultsExtractor;

public class AggregationsResultsExtractor implements ResultsExtractor<Aggregations> {

    public AggregationsResultsExtractor(){
        //Para Elastic Search
    }

    @Override
    public Aggregations extract(SearchResponse response) {
        return response.getAggregations();
    }
}
