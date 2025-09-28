package com.project.hammer.serviceimpl;

import com.project.hammer.entity.Category;
import com.project.hammer.entity.Product;
import com.project.hammer.exceptions.BadRequestCustomException;
import com.project.hammer.repository.CategoryRepo;
import com.project.hammer.repository.ProductRepo;
import com.project.hammer.service.CategoryService;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProductRepo productRepo;

    @Override
    public String createNewCategory(String categoryName) {
        if(Objects.isNull(categoryName)){
            throw new BadRequestCustomException("CategoryName shouldn't be empty");
        }
        int isAlreadyExist=categoryRepo.isCategoryAlreadyExist(categoryName);
        if(isAlreadyExist>0){
            throw new BadRequestCustomException("Category already exist");
        }
        Category category=new Category();
        category.setCategoryName(categoryName);
        categoryRepo.save(category);
        return "category added successfully";
    }

    @Override
    public String deleteCategroy(String categoryName) {
        Optional<Category> category=categoryRepo.getCategoryByName(categoryName);
        if(category.isPresent()){
            List<Product> productList=productRepo.getProductsByCategory(category.get().getId());
            if(ObjectUtils.isNotEmpty(productList)){
                productList.parallelStream().forEach(product -> product.setCategory(null));
                productRepo.saveAll(productList);
            }
            categoryRepo.delete(category.get());
        }else{
            throw new BadRequestCustomException("Category not found !");
        }
        return "Category deleted successfully";
    }

    @Override
    public List<String> getAllCatogory() {

        List<Category> categories=categoryRepo.getAllCategory();

        return categories.parallelStream().map(Category::getCategoryName).collect(Collectors.toList());
    }
}
