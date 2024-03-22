package com.example.securityl.dtos;


import com.example.securityl.models.ProjectBooking;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DesignDto {
    private Long id;
    private String codeDesign;
    private String imageUrls;
    private String status;
    private String note;
    private byte[] fileData;
    private String fileName;
    private String fileType;
    private ProjectBooking projectBooking;
    private String staffName;
}
