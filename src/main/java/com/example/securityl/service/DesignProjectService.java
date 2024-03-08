package com.example.securityl.service;

import com.example.securityl.dto.request.DesignProjectRequest;
import com.example.securityl.dto.request.response.DesignProjectResponse;
import org.springframework.http.ResponseEntity;

public interface DesignProjectService {
    ResponseEntity<DesignProjectResponse> createDesignProject(DesignProjectRequest designProjectRequest);

    ResponseEntity<DesignProjectResponse> updateDesignProject(int projectId, DesignProjectRequest designProjectRequest);

    ResponseEntity<DesignProjectResponse> deleteDesignProject(int projectId);

    ResponseEntity<DesignProjectResponse> findDesignProjectById(int projectId);

    ResponseEntity<DesignProjectResponse> findAllDesignProjects();

}
