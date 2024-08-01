package com.example.mandarin_shop_back.repository;

import com.example.mandarin_shop_back.entity.product.Category;
import com.example.mandarin_shop_back.entity.product.OptionName;
import com.example.mandarin_shop_back.entity.product.OptionTitle;
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

    int saveOptionTitle(OptionTitle optionTitle);

    List<OptionTitle> getOptionTitleByproductId(@Param("productId") int productId);

    int updateOptionTitle(OptionTitle optionTitle);

    int deleteOptionTitle(OptionTitle optionTitle);

    int saveOptionName(OptionName optionName);

    List<OptionName> getOptionsByMenuId(@Param("productId") int productId);

    int updateOptionName(OptionName optionName);
}
