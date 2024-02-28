package com.example.securityl.controller;

import com.example.securityl.model.Category;
import com.example.securityl.request.CategoryRequest.RequestCategory;
import com.example.securityl.response.ObjectResponse.ResponseObject;
import com.example.securityl.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
@PreAuthorize("hasAnyRole('USER','ADMIN','STAFF')")
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/getAllCategory")
    private ResponseEntity<ResponseObject> getAllCategory(){
        return categoryService.findAllCategory();
    }

    @PostMapping("/createCategory")
    private ResponseEntity<ResponseObject> createCategory(@RequestBody RequestCategory requestCategory){
        var category = categoryService.createCategory(requestCategory);
        return ResponseEntity.ok().body(new ResponseObject("Success","Create Category succes",category));
    }

    @GetMapping("/getCategory/{categoryId}")
    private ResponseEntity<ResponseObject> getCategoryById(@PathVariable Integer categoryId){
        List<Category> category=  categoryService.findCategoryById(categoryId);
        if(category!= null){
        return ResponseEntity.ok().body(new ResponseObject("Success","List category success",category));
        }
        return  ResponseEntity.badRequest().body(new ResponseObject("Fail","Not found category",null));
    }

    @PutMapping("/updateCategory/{categoryId}")
    private ResponseEntity<ResponseObject> updateCategory(@PathVariable Integer categoryId,
                                                          @RequestBody RequestCategory requestCategory){
       return categoryService.updateCategory(categoryId,requestCategory);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    private ResponseEntity<ResponseObject> deleteCategory(@PathVariable Integer categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
