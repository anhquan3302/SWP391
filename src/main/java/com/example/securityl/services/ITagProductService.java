package com.example.securityl.services;



import com.example.securityl.dtos.TagProductDto;
import com.example.securityl.models.TagsProduct;

import java.util.List;

public interface ITagProductService {
    TagsProduct createTagsProduct(TagProductDto tagProductDto);

    TagsProduct updateTagsProduct(Long id, TagProductDto brandDto);

    TagsProduct getTagsProductById(long id);

    List<TagsProduct> getAllTagsProducts();
}
