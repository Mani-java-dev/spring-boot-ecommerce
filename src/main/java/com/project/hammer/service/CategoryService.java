package com.project.hammer.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    String createNewCategory(String categoryName);

    String deleteCategroy(String categoryName);

    List<String> getAllCatogory();
}
