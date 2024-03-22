package com.example.securityl.controllers;


import com.example.securityl.Responses.TagProductResponse;
import com.example.securityl.components.LocalizationUtils;
import com.example.securityl.dtos.TagProductDto;


import com.example.securityl.models.TagsProduct;
import com.example.securityl.services.impl.TagProductService;
import com.example.securityl.utills.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("${api.prefix}/tag_product")
@RequiredArgsConstructor
public class TagProductController {
    private final TagProductService tagProductService;
    private final LocalizationUtils localizationUtils;

    @PostMapping("")
    public ResponseEntity<TagProductResponse> createTagProduct(
            @Valid @RequestBody TagProductDto tagProductDTO,
            BindingResult result
    ) {
        TagProductResponse tagProductResponse = new TagProductResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            tagProductResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            tagProductResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(tagProductResponse);
        }
        TagsProduct tagsProduct = tagProductService.createTagsProduct(tagProductDTO);
//        tagProductResponse.setTagsProduct(tagsProduct);
        return ResponseEntity.ok(tagProductResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagProductResponse> updateBrand(
            @PathVariable Long id,
            @Valid @RequestBody TagProductDto tagProductDTO,
            BindingResult result
    ) {
        TagProductResponse tagProductResponse = new TagProductResponse();
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            tagProductResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.INSERT_BRAND_FAILED));
            tagProductResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(tagProductResponse);
        }
        TagsProduct tagsProduct = tagProductService.updateTagsProduct(id, tagProductDTO);
//        tagProductResponse.setTagsProduct(tagsProduct);
        return ResponseEntity.ok(tagProductResponse);
    }
//    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<TagsProduct>> getAllTagsProducts() {
        List<TagsProduct> tagsProducts = tagProductService.getAllTagsProducts();
        return ResponseEntity.ok(tagsProducts);
    }
}
