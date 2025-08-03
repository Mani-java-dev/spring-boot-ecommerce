package com.project.hammer.service;

import com.project.hammer.model.NewProductModel;
import com.project.hammer.model.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    String createNewProduct(NewProductModel productModel);

    List<ProductResponse> getAllProducts();

    String updateProduct(NewProductModel newProductModel);
}
