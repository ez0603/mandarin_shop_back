package com.example.mandarin_shop_back.service.admin;

import com.example.mandarin_shop_back.dto.product.request.*;
import com.example.mandarin_shop_back.dto.product.response.AdminSearchProductRespDto;
import com.example.mandarin_shop_back.dto.product.response.OptionTitlesRespDto;
import com.example.mandarin_shop_back.dto.product.response.OptionsRespDto;
import com.example.mandarin_shop_back.entity.product.Category;
import com.example.mandarin_shop_back.entity.product.OptionName;
import com.example.mandarin_shop_back.entity.product.OptionTitle;
import com.example.mandarin_shop_back.entity.product.Product;
import com.example.mandarin_shop_back.repository.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<AdminSearchProductRespDto> getProducts() {
        List<Product> products = productMapper.getProducts();

        return products.stream().map(Product::toSearchProductRespDto).collect(Collectors.toList());
    }

    public List<AdminSearchProductRespDto> getProductCategory(int categoryId) {
        List<Product> products = productMapper.getProductCategory(categoryId);

        return products.stream().map(Product::toSearchProductRespDto).collect(Collectors.toList());
    }

    public Product getProductDetail(int productId) {
        List<Product> products = productMapper.getProductDetail(productId);

        if (products != null && !products.isEmpty()) {
            return products.get(0);
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
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

    public void insertOptionTitle(AddOptionTitleReqDto addOptionTitleReqDto) {
        productMapper.saveOptionTitle(addOptionTitleReqDto.toEntity());
    }

    // 제품 옵션 타이틀 조회
    @Transactional(rollbackFor = Exception.class)
    public OptionTitlesRespDto getOptionTitles(int productId) {
        List<OptionTitle> optionTitles = productMapper.getOptionTitleByproductId(productId);
        OptionTitlesRespDto optionTitlesRespDto = new OptionTitlesRespDto();

        List<Integer> optionTitleIds = new ArrayList<>();
        List<String> optionTitleNames = new ArrayList<>();

        for (OptionTitle optionTitle : optionTitles) {
            optionTitleIds.add(optionTitle.getOptionTitleId());
            optionTitleNames.add(optionTitle.getTitleName());
        }

        optionTitlesRespDto.setOptionTitlesId(optionTitleIds);
        optionTitlesRespDto.setOptionTitleNames(optionTitleNames);

        return optionTitlesRespDto;
    }

    // 옵션 타이틀 수정
    public void editOptionTitle(UpdateOptionTitleReqDto updateOptionTitleReqDto) {
        productMapper.updateOptionTitle(updateOptionTitleReqDto.toEntity());
    }

    // 옵션 타이틀 삭제
    @Transactional(rollbackFor = Exception.class)
    public void deleteOptionTitle(DeleteOptionTitleReqDto deleteOptionTitleReqDto) {
        productMapper.deleteOptionTitle(deleteOptionTitleReqDto.toEntity());
    }

    public void insertOptionName(AddOptionNameReqDto addOptionNameReqDto) {
        productMapper.saveOptionName(addOptionNameReqDto.toEntity());
    }

    // 제품 별 옵션 조회
    @Transactional(rollbackFor = Exception.class)
    public OptionsRespDto getOptionsByMenuId(int productId) {
        List<OptionName> options = productMapper.getOptionsByMenuId(productId);
        Set<Integer> optionTitlesIdSet = new HashSet<>();
        Set<String> optionTitleNamesSet = new HashSet<>();
        List<Integer> optionNameIds = new ArrayList<>();
        List<String> optionNames = new ArrayList<>();

        for (OptionName optionName : options) {
            optionTitlesIdSet.add(optionName.getOptionTitle().getOptionTitleId());
            optionTitleNamesSet.add(optionName.getOptionTitle().getTitleName());
            optionNameIds.add(optionName.getOptionNameId());
            optionNames.add(optionName.getOptionName());
        }

        return OptionsRespDto.builder()
                .productId(productId)
                .optionTitlesId(new ArrayList<>(optionTitlesIdSet))
                .optionTitleNames(new ArrayList<>(optionTitleNamesSet))
                .optionNameIds(optionNameIds)
                .optionNames(optionNames)
                .build();
    }

    public void editOptionName(UpdateOptionNameReqDto updateOptionNameReqDto) {
        productMapper.updateOptionName(updateOptionNameReqDto.toEntity());
    }
}