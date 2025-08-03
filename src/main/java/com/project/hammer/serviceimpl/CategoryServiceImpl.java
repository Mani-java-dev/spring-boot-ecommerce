package com.project.hammer.serviceimpl;

import com.project.hammer.entity.Category;
import com.project.hammer.exceptions.BadRequestCustomException;
import com.project.hammer.repository.CategoryRepo;
import com.project.hammer.service.CategoryService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private EntityManager entityManager;

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
}
