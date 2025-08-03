package com.example.metalpurity.repository.custom;

import com.example.metalpurity.dto.FilterCriteriaDTO;
import com.example.metalpurity.model.Metal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class MetalRepositoryImpl implements MetalRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

  @Override
public List<Metal> findWithFilters(FilterCriteriaDTO criteria) {
    Query query = new Query();
    boolean hasFilters = false;

    if (StringUtils.hasText(criteria.getSearchText())) {
        query.addCriteria(Criteria.where("name").regex(criteria.getSearchText(), "i"));
        hasFilters = true;
    }
    if (criteria.getMetalType() != null) {
        query.addCriteria(Criteria.where("metalType").is(criteria.getMetalType()));
        hasFilters = true;
    }
    if (criteria.getPurityLevel() != null) {
        query.addCriteria(Criteria.where("purityLevel").is(criteria.getPurityLevel()));
        hasFilters = true;
    }
    if (criteria.getFromDate() != null && criteria.getToDate() != null) {
        query.addCriteria(Criteria.where("createdAt")
            .gte(criteria.getFromDate())
            .lte(criteria.getToDate()));
        hasFilters = true;
    }

    if (StringUtils.hasText(criteria.getSortField())) {
        Sort.Direction direction = "desc".equalsIgnoreCase(criteria.getSortDirection())
            ? Sort.Direction.DESC
            : Sort.Direction.ASC;
        query.with(Sort.by(direction, criteria.getSortField()));
    }

    System.out.println("🧠 Final Mongo query: " + query.getQueryObject().toJson());

    if (!hasFilters) {
        List<Metal> fallback = mongoTemplate.findAll(Metal.class);
        System.out.println("🔄 No filters — returning all (" + fallback.size() + ") metals");
        return fallback;
    }

    List<Metal> result = mongoTemplate.find(query, Metal.class);
    System.out.println("✅ Filtered result size: " + result.size());
    return result;
}
}