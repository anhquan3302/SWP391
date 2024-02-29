package com.example.securityl.serviceimpl;

import com.example.securityl.service.FireBaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FireBaseServiceImpl implements FireBaseService {

    private final Storage storage;
    private final String bucketName;

    public FireBaseServiceImpl() throws IOException {
        // Initialize Firebase Storage
        this.storage = StorageOptions.newBuilder()

                .build()
                .getService();

        this.bucketName = "eFurniture";
    }

    @Override
    public List<String> uploadImages(MultipartFile[] files) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String fileName = generateUniqueFileName(file.getOriginalFilename());

                BlobId blobId = BlobId.of(bucketName, fileName);
                try (InputStream inputStream = file.getInputStream()) {
                    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
                    Blob blob = storage.create(blobInfo, inputStream);

                    // Get URL of the uploaded image
                    String imageUrl = blob.getMediaLink();
                    imageUrls.add(imageUrl);
                }
            } catch (IOException e) {
                // Handle file upload error
                e.printStackTrace();
            }
        }
        return imageUrls;
    }

    private String generateUniqueFileName(String originalFilename) {
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueID = UUID.randomUUID().toString();
        String uniqueFileName = uniqueID + fileExtension;
        return uniqueFileName;
    }


}
