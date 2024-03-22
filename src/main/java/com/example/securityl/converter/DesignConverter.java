package com.example.securityl.converter;



import com.example.securityl.Responses.DesignResponse;
import com.example.securityl.models.Design;
import org.springframework.stereotype.Component;

@Component
public class DesignConverter {
    public static DesignResponse toResponse(Design design) {
        DesignResponse designResponse = DesignResponse.builder()
                .id(design.getId())
                .codeDesign(design.getCodeDesign())
                .imageUrls(design.getImageUrls())
                .note(design.getNote())
                .status(design.getStatus())
                .projectBooking(design.getProjectBooking())
                .fileName(design.getFileName())
                .fileType(design.getFileType())
                .staffName(design.getStaffName())
                .build();
        return designResponse;
    }

}
