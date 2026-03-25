package com.spring.internal_working.internal_work.repositories;

import com.spring.internal_working.internal_work.entities.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductCriteriaRepository {

    List<Product> findProductsByCriteria(String name , BigDecimal minPrice , BigDecimal maxPrice);
}
