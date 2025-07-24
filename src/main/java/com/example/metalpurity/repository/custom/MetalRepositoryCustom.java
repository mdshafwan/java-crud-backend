package com.example.metalpurity.repository.custom;

import com.example.metalpurity.dto.FilterCriteriaDTO;
import com.example.metalpurity.model.Metal;
import java.util.List;

public interface MetalRepositoryCustom {
    List<Metal> findWithFilters(FilterCriteriaDTO criteria);
}