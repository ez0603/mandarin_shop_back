package com.example.mandarin_shop_back.service.admin;

import com.example.mandarin_shop_back.dto.product.request.AdminRegisterProductReqDto;
import com.example.mandarin_shop_back.dto.product.request.UpdateProductCategoryReqDto;
import com.example.mandarin_shop_back.dto.product.request.UpdateProductReqDto;
import com.example.mandarin_shop_back.dto.product.request.AddProductCategoryReqDto;
import com.example.mandarin_shop_back.dto.product.response.AdminSearchProductRespDto;
import com.example.mandarin_shop_back.entity.product.Category;
import com.example.mandarin_shop_back.entity.product.Product;
import com.example.mandarin_shop_back.repository.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<AdminSearchProductRespDto> getProducts() {
        List<Product> products = productMapper.getProducts();

        return products.stream().map(Product::toSearchProductRespDto).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public int saveProduct(AdminRegisterProductReqDto adminRegisterProductReqDto) {
        return productMapper.saveProduct(adminRegisterProductReqDto.toEntity());
    }

    @Transactional
    public void editProduct(UpdateProductReqDto updateProductReqDto) {
        Product product = updateProductReqDto.toEntity();
        productMapper.updateProduct(product);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteProduct(int productId) {
        return productMapper.deleteProduct(productId);
    }

    public void insertProductCategory(AddProductCategoryReqDto addProductCategoryReqDto) {
        productMapper.saveProductCategory(addProductCategoryReqDto.toEntity());
    }

    public void editProductCategory(UpdateProductCategoryReqDto updateProductCategoryReqDto) {
        productMapper.updateProductCategory(updateProductCategoryReqDto.toEntity());
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteProductCategory(int categoryId) {
        return productMapper.deleteProductCategory(categoryId);
    }

    public List<Category> getCategory() {
        return productMapper.getCategory();
    }
}