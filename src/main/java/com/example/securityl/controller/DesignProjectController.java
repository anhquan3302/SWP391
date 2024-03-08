package com.example.securityl.controller;

import com.example.securityl.dto.request.DesignProjectRequest;
import com.example.securityl.dto.request.response.DesignProjectResponse;
import com.example.securityl.service.DesignProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/DesignProjectController")
public class DesignProjectController {
    private final DesignProjectService designProjectService;

    @PostMapping("/createDesignProject")
    public ResponseEntity<DesignProjectResponse> createDesignProject(@RequestBody DesignProjectRequest designProjectRequest) {
        return designProjectService.createDesignProject(designProjectRequest);
    }

    @GetMapping("/findDesignProject/{projectId}")
    public ResponseEntity<DesignProjectResponse> findDesignProjectById(@PathVariable int projectId) {
        return designProjectService.findDesignProjectById(projectId);
    }

    @GetMapping("/findAllDesignProjects")
    public ResponseEntity<DesignProjectResponse> findAllDesignProjects() {
        return designProjectService.findAllDesignProjects();
    }

    @PutMapping("/updateDesignProject/{projectId}")
    public ResponseEntity<DesignProjectResponse> updateDesignProject(@PathVariable int projectId, @RequestBody DesignProjectRequest designProjectRequest) {
        return designProjectService.updateDesignProject(projectId, designProjectRequest);
    }

    @DeleteMapping("/deleteDesignProject/{projectId}")
    public ResponseEntity<DesignProjectResponse> deleteDesignProject(@PathVariable int projectId) {
        return designProjectService.deleteDesignProject(projectId);
    }
}
