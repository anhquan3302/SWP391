package com.example.securityl.serviceimpl;

import com.example.securityl.model.DesignProjects;
import com.example.securityl.repository.DesignProjectsRepository;
import com.example.securityl.dto.request.DesignProjectRequest;
import com.example.securityl.dto.request.response.DesignProjectResponse;
import com.example.securityl.service.DesignProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DesignProjectServiceImpl implements DesignProjectService {

    private final DesignProjectsRepository designProjectsRepository;

    @Autowired
    public DesignProjectServiceImpl(DesignProjectsRepository designProjectsRepository) {
        this.designProjectsRepository = designProjectsRepository;
    }

    @Override
    public ResponseEntity<DesignProjectResponse> createDesignProject(DesignProjectRequest designProjectRequest) {
        try {
            // Kiểm tra xem các trường cần thiết có được cung cấp không
            if (designProjectRequest.getProjectName() == null || designProjectRequest.getProjectName().isEmpty() ||
                    designProjectRequest.getUserId() == 0) {
                return ResponseEntity.badRequest().body(new DesignProjectResponse("Fail", "Project name and user id are required", null));
            }

            // Tạo một đối tượng DesignProjects từ request
            DesignProjects designProjects = new DesignProjects();
            designProjects.setUserId(designProjectRequest.getUserId());
            designProjects.setProjectName(designProjectRequest.getProjectName());
            designProjects.setStartDate(designProjectRequest.getStartDate());
            designProjects.setEndDate(designProjectRequest.getEndDate());
            designProjects.setStatus(designProjectRequest.getStatus());

            // Lưu đối tượng vào cơ sở dữ liệu
            DesignProjects savedProject = designProjectsRepository.save(designProjects);

            return ResponseEntity.ok(new DesignProjectResponse("Success", "Create design project success", savedProject));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new DesignProjectResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<DesignProjectResponse> updateDesignProject(int projectId, DesignProjectRequest designProjectRequest) {
        try {
            DesignProjects existingProject = designProjectsRepository.findById(projectId).orElse(null);
            if (existingProject == null) {
                return ResponseEntity.badRequest().body(new DesignProjectResponse("Fail", "Project not found", null));
            }

            // Cập nhật thông tin của dự án từ request
            existingProject.setProjectName(designProjectRequest.getProjectName());
            existingProject.setStartDate(designProjectRequest.getStartDate());
            existingProject.setEndDate(designProjectRequest.getEndDate());
            existingProject.setStatus(designProjectRequest.getStatus());

            // Lưu thay đổi vào cơ sở dữ liệu
            DesignProjects updatedProject = designProjectsRepository.save(existingProject);

            return ResponseEntity.ok(new DesignProjectResponse("Success", "Update design project success", updatedProject));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new DesignProjectResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<DesignProjectResponse> deleteDesignProject(int projectId) {
        try {
            DesignProjects existingProject = designProjectsRepository.findById(projectId).orElse(null);
            if (existingProject == null) {
                return ResponseEntity.badRequest().body(new DesignProjectResponse("Fail", "Project not found", null));
            }

            // Xóa dự án từ cơ sở dữ liệu
            designProjectsRepository.delete(existingProject);

            return ResponseEntity.ok(new DesignProjectResponse("Success", "Delete design project success", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new DesignProjectResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<DesignProjectResponse> findDesignProjectById(int projectId) {
        try {
            DesignProjects project = designProjectsRepository.findById(projectId).orElse(null);
            if (project == null) {
                return ResponseEntity.badRequest().body(new DesignProjectResponse("Fail", "Project not found", null));
            }

            return ResponseEntity.ok(new DesignProjectResponse("Success", "Find design project success", project));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new DesignProjectResponse("Fail", "Internal Server Error", null));
        }
    }

    @Override
    public ResponseEntity<DesignProjectResponse> findAllDesignProjects() {
        try {
            List<DesignProjects> projects = designProjectsRepository.findAll();
            if (projects.isEmpty()) {
                return ResponseEntity.ok(new DesignProjectResponse("Success", "List is empty", projects));
            }
            return ResponseEntity.ok(new DesignProjectResponse("Success", "List design projects", projects));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new DesignProjectResponse("Fail", "Internal Server Error", null));
        }
    }
}