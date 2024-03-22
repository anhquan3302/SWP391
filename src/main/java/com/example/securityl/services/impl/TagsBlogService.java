//package com.example.securityl.services.impl;
//
//
//import com.example.securityl.models.TagsBlog;
//import com.example.securityl.repositories.TagsBlogRepository;
//import com.example.securityl.services.ITagsBlogService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class TagsBlogService implements ITagsBlogService {
//    private final TagsBlogRepository tagsBlogRepository;
//    @Override
//    public List<TagsBlog> getAllTags() {
//        return tagsBlogRepository.findAll();
//    }
//
//    @Override
//    public TagsBlog getTagById(Long id) {
//        return tagsBlogRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public TagsBlog saveTag(TagsBlog tag) {
//        return tagsBlogRepository.save(tag);
//    }
//
//    @Override
//    public void deleteTag(Long id) {
//        tagsBlogRepository.deleteById(id);
//    }
//}
