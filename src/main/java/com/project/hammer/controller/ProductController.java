package com.project.hammer.controller;

import com.project.hammer.constants.APIResponse;
import com.project.hammer.constants.Constant;
import com.project.hammer.model.NewProductModel;
import com.project.hammer.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.project.hammer.constants.Constant.TRACKER;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addNewProduct(@RequestBody NewProductModel newProductModel,
                                                     HttpServletRequest httpServletRequest) throws IOException {
        log.info("{}{}", TRACKER, httpServletRequest.getRemoteAddr());
        return ResponseEntity.ok().body(new APIResponse(Constant.SUCCESS, productService.createNewProduct(newProductModel),null));
    }

    @GetMapping("/get/all")
    public ResponseEntity<APIResponse> getProduct(HttpServletRequest httpServletRequest) {
        log.info("{}{}", TRACKER, httpServletRequest.getRemoteAddr());
        return ResponseEntity.ok().body(new APIResponse(Constant.SUCCESS, "product fetched successfully", productService.getAllProducts()));
    }

    @PutMapping("/update")
    public ResponseEntity<APIResponse> updateExistingProduct(@RequestBody NewProductModel newProductModel,
                                                             HttpServletRequest httpServletRequest) throws IOException {
        log.info("{}{}", TRACKER, httpServletRequest.getRemoteAddr());
        return ResponseEntity.ok().body(new APIResponse(Constant.SUCCESS, productService.updateProduct(newProductModel),null));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<APIResponse> deleteProducts(@RequestParam(name = "productId") String productId, HttpServletRequest request) {
        log.info("{}{}", TRACKER, request.getRemoteAddr());
        return ResponseEntity.ok().body(new APIResponse(Constant.SUCCESS, productService.deleteProduct(productId),null));
    }


}
