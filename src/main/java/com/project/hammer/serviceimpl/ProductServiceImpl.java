package com.project.hammer.serviceimpl;

import com.project.hammer.entity.Category;
import com.project.hammer.entity.Product;
import com.project.hammer.exceptions.BadRequestCustomException;
import com.project.hammer.model.NewProductModel;
import com.project.hammer.model.ProductResponse;
import com.project.hammer.repository.CategoryRepo;
import com.project.hammer.repository.ProductRepo;
import com.project.hammer.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductServiceImpl(
            ProductRepo productRepo,
            CategoryRepo categoryRepo
    ) {
        this.productRepo = productRepo;
        this.categoryRepo=categoryRepo;
    }


    @Override
    public String createNewProduct(NewProductModel productModel){
            Integer isExist=productRepo.findProductNameIsExist(productModel.getProductName());
            if(isExist>0){
                throw new BadRequestCustomException("Product already exist please give different name");
            }
            Product newProduct = new Product();
            newProduct.setProductName(productModel.getProductName());
            newProduct.setDescription(productModel.getDescription());
            newProduct.setPrice(productModel.getPrice());
            newProduct.setIsActive(1);
            newProduct.setIsDeleted(0);
            newProduct.setImage(productModel.getImageLink());
            Optional<Category> fetchedCategory= Optional.ofNullable(categoryRepo.getCategoryByName(productModel.getCategory())
                        .orElseThrow(() -> new BadRequestCustomException("Category not found")));
            fetchedCategory.ifPresent(newProduct::setCategory);
            productRepo.save(newProduct);
            return "product added successfully";
    }

    @Override
    @Transactional
    public List<ProductResponse> getAllProducts() {
        List<Product> allProducts= productRepo.getAllProducts();

        return allProducts.stream().map(
                product -> ProductResponse
                .builder()
                .productName(product.getProductName())
                .description(product.getDescription())
                        .isActive(product.getIsActive())
                        .isDeleted(product.getIsDeleted())
                        .category(product.getCategory().getCategoryName())
                .price(product.getPrice())
                .image(product.getImage())
                .build()).toList();
    }

    @Override
    public String updateProduct(NewProductModel newProductModel) {
        if(Objects.isNull(newProductModel)){
            throw new BadRequestCustomException("Products details shouldn't be empty");
        }

        Product product=productRepo.findProductByProductName(newProductModel.getProductName());
        if(Objects.isNull(product)){
            throw new BadRequestCustomException("Product doesn't exist");
        }
        product.setDescription(newProductModel.getDescription());
        product.setPrice(newProductModel.getPrice());
        product.setImage(newProductModel.getImageLink());
        productRepo.save(product);

        return "Product updated successfully";
    }
}
