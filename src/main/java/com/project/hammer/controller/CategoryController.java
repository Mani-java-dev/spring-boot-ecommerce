package com.project.hammer.controller;

import com.project.hammer.constants.APIResponse;
import com.project.hammer.constants.Constant;
import com.project.hammer.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.project.hammer.constants.Constant.TRACKER;


@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addNewCategory(@RequestParam(name = "categoryName") String categoryName,
                                                      HttpServletRequest httpServletRequest) {
        log.info("{}{}", TRACKER, httpServletRequest.getRemoteAddr());
        return ResponseEntity.ok().body(new APIResponse(Constant.SUCCESS,categoryService.createNewCategory(categoryName)
        ));
    }
}
