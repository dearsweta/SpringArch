package com.spring.internal_working.internal_work.repositories;

import com.spring.internal_working.internal_work.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}