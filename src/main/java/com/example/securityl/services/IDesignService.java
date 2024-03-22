package com.example.securityl.services;



import com.example.securityl.models.Design;

import java.util.List;
import java.util.Optional;

public interface IDesignService {

    List<Design> getAllDesigns();

    Optional<Design> getDesignById(Long id);

    public Design createDesign(Design design);
    Design updateDesign(Long id, Design designDetails);

    void deleteDesign(Long id);
    public List<Design> getDesignsByProjectBookingId(Long projectId);
    String findLatestCodeDesign();
}
