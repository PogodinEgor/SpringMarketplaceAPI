package com.example.restproductservice.repository;

import com.example.restproductservice.dto.ProductFilter;
import com.example.restproductservice.model.Product;

import java.util.List;

public interface FilterProductRepository {
    List<Product> findAllByFilter(ProductFilter filter);
}
