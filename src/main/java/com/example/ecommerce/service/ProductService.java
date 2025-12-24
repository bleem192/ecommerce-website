package com.example.ecommerce.service;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 查询所有商品
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 根据ID查询商品
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在：" + id));
    }

    // 保存商品（初始化数据用）
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 删除商品
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}