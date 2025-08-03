package com.project.hammer.service;

import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    String createNewCategory(String categoryName);
}
