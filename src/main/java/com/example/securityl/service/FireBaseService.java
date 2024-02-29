package com.example.securityl.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FireBaseService {
//    void uploadImage(MultipartFile file);

    List<String> uploadImages(MultipartFile[] files);
}
