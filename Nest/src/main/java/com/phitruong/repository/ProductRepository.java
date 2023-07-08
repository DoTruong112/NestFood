package com.phitruong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.phitruong.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	@Query("SELECT p.id, p.name, p.price, p.description,p.image,p.mfg, "
			+ "c.name AS category_name FROM Product p JOIN p.category c")
	List<Object[]> findAllProductsWithCategoryName();
}
