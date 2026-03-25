package com.spring.internal_working.internal_work.repositories;

import com.spring.internal_working.internal_work.dtos.prev.ProductSummaryDTO;
import com.spring.internal_working.internal_work.entities.Category;
import com.spring.internal_working.internal_work.entities.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCriteriaRepository, JpaSpecificationExecutor {



    List<Product> findAll(Example example);
    List<Product> findAll( Sort sort);
    Page<Product> findAll(Pageable pageable);
    List<Product> findByName(String name);


    List<Product> findByNameOrderByPrice(String name);

    List<Product> findByNameNull(String name);

    List<Product> findTop5ByName(String name);

    List<Product> findByPriceBetweenOrderByIdAsc(BigDecimal price, BigDecimal price2);

    @Procedure("findProductsByPriceBetween")
    List<Product> findProducts( BigDecimal priceAfter, BigDecimal priceBefore);


    @Query("select count(*) from Product p where p.price between :priceAfter and :priceBefore")
    long countProducts(@Param("priceAfter") BigDecimal priceAfter, @Param("priceBefore") BigDecimal priceBefore);

    @Modifying
    @Query("update Product p set p.price = :price where p.category.id = :categoryId")
    void updatePriceByCategory(BigDecimal price , Byte categoryId);

    @Query("select new com.spring.internal_working.internal_work.dtos.prev.ProductSummaryDTO(p.id , p.name)  from Product p where p.category = :category")
    List<ProductSummaryDTO> findByCategory(@Param("category") Category category);

    List<Product> findProductByCategory(Category category);

    List<Product> findProductByCategory_Id(Byte categoryId);
}