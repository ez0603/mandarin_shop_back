package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.product.Category;
import com.example.mandarin_shop_back.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    public int saveProduct(Product product);

    public int updateProduct(Product product);

    public int deleteProduct(int productId);

    public List<Product> getProducts();

    public int saveProductCategory(Category category);

    public int updateProductCategory(Category category);

    public int deleteProductCategory(int categoryId);

    public List<Category> getCategory();
}
