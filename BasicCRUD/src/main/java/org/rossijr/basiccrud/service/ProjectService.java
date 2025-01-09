package org.rossijr.basiccrud.service;

import org.rossijr.basiccrud.model.Project;
import org.rossijr.basiccrud.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public boolean isValidProjectId(Long projectId) {
        return projectRepository.existsById(projectId);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

}
