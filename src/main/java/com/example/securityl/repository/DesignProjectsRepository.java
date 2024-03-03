package com.example.securityl.repository;

import com.example.securityl.model.DesignProjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignProjectsRepository extends JpaRepository<DesignProjects, Integer> {

}