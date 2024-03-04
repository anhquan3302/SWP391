package com.example.securityl.service;

import com.example.securityl.model.DesignProjects;

import com.example.securityl.request.DesignProjectRequest;
import com.example.securityl.response.DesignProjectResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DesignProjectService {
    ResponseEntity<DesignProjectResponse> createDesignProject(DesignProjectRequest designProjectRequest);

    ResponseEntity<DesignProjectResponse> updateDesignProject(int projectId, DesignProjectRequest designProjectRequest);

    ResponseEntity<DesignProjectResponse> deleteDesignProject(int projectId);

    ResponseEntity<DesignProjectResponse> findDesignProjectById(int projectId);

    ResponseEntity<DesignProjectResponse> findAllDesignProjects();

}
