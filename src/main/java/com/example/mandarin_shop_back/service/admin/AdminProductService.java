package com.example.mandarin_shop_back.service.admin;

import com.example.mandarin_shop_back.dto.product.request.*;
import com.example.mandarin_shop_back.dto.product.response.*;
import com.example.mandarin_shop_back.entity.inventory.Inventory;
import com.example.mandarin_shop_back.entity.product.Category;
import com.example.mandarin_shop_back.entity.product.OptionName;
import com.example.mandarin_shop_back.entity.product.OptionTitle;
import com.example.mandarin_shop_back.entity.product.Product;
import com.example.mandarin_shop_back.repository.InventoryMapper;
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
    @Autowired
    private InventoryMapper inventoryMapper;

    public List<AdminSearchProductRespDto> getProducts() {
        List<Product> products = productMapper.getProducts();

        return products.stream().map(Product::toSearchProductRespDto).collect(Collectors.toList());
    }

    public List<AdminSearchProductRespDto> getProductCategory(int categoryId) {
        List<Product> products = productMapper.getProductCategory(categoryId);

        return products.stream().map(Product::toSearchProductRespDto).collect(Collectors.toList());
    }

    @Transactional(rollbackFor = Exception.class)
    public ProductDetailRespDto getProductDetail(int productId) {
        List<Product> products = productMapper.getProductDetail(productId);

        if (products == null || products.isEmpty()) {
            throw new RuntimeException("Product not found with id: " + productId);
        }

        Product product = products.get(0);

        Map<Integer, ProductDetailRespDto.OptionTitleDetail> optionTitleMap = new HashMap<>();
        List<ProductDetailRespDto.OptionNameDetail> optionNames = new ArrayList<>();

        for (Product prod : products) {
            int optionTitleId = prod.getOptionTitleId();
            if (!optionTitleMap.containsKey(optionTitleId)) {
                ProductDetailRespDto.OptionTitleDetail optionTitle = ProductDetailRespDto.OptionTitleDetail.builder()
                        .optionTitleId(optionTitleId)
                        .titleName(prod.getTitleName())
                        .build();
                optionTitleMap.put(optionTitleId, optionTitle);
            }

            ProductDetailRespDto.OptionNameDetail optionName = ProductDetailRespDto.OptionNameDetail.builder()
                    .optionNameId(prod.getOptionNameId())
                    .optionName(prod.getOptionName())
                    .optionTitleId(optionTitleId)
                    .build();
            optionNames.add(optionName);
        }

        return ProductDetailRespDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .categoryId(product.getCategoryId())
                .categoryName(product.getCategoryName())
                .productPrice(product.getProductPrice())
                .productImg(product.getProductImg())
                .productDescription(product.getProductDescription())
                .createDate(product.getCreateDate())
                .updateDate(product.getUpdateDate())
                .optionTitles(new ArrayList<>(optionTitleMap.values()))
                .optionNames(optionNames)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveProduct(AdminRegisterProductReqDto adminRegisterProductReqDto) {
        // 1. product_tb에 상품 저장
        Product product = adminRegisterProductReqDto.toEntity();
        productMapper.saveProduct(product);  // 여기서 productId가 생성됩니다.

        // 2. inventory_tb에 재고 정보 저장
        Inventory inventory = adminRegisterProductReqDto.toInventoryEntity(product.getProductId());
        inventoryMapper.saveInventory(inventory);

        // 3. 옵션 타이틀과 옵션 이름 저장
        List<OptionTitlesRespDto> optionTitles = adminRegisterProductReqDto.getOptionTitles();
        if (optionTitles != null) {
            for (OptionTitlesRespDto optionTitleDto : optionTitles) {
                if (optionTitleDto.getOptionTitleNames() != null) {
                    for (String titleName : optionTitleDto.getOptionTitleNames()) {
                        // OptionTitle 엔티티 생성 및 저장
                        OptionTitle optionTitle = new OptionTitle();
                        optionTitle.setTitleName(titleName); // 옵션 타이틀 이름 설정
                        optionTitle.setProductId(product.getProductId()); // 상품 ID 설정
                        productMapper.saveOptionTitle(optionTitle);

                        // 옵션 타이틀 ID가 생성된 후 옵션 이름 저장
                        if (optionTitleDto.getOptionNames() != null) {
                            for (AddOptionNameReqDto optionNameDto : optionTitleDto.getOptionNames()) {
                                OptionName optionNameEntity = new OptionName();
                                optionNameEntity.setOptionName(optionNameDto.getOptionName());
                                optionNameEntity.setProductId(product.getProductId());
                                optionNameEntity.setOptionTitleId(optionTitle.getOptionTitleId()); // 옵션 타이틀 ID 설정
                                productMapper.saveOptionName(optionNameEntity);
                            }
                        }
                    }
                }
            }
        }
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