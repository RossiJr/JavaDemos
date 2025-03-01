package org.rossijr.basiccrud;

import org.rossijr.basiccrud.model.Project;
import org.rossijr.basiccrud.service.ProjectService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BasicCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicCrudApplication.class, args);
    }

    /**
     * Initializes the database with a sample project on startup.
     *
     * @param projectService The service to handle project operations.
     * @return A CommandLineRunner to execute the initialization logic.
     */
    @Bean
    CommandLineRunner initializeDatabase(ProjectService projectService) {
        return args -> {
            Project sampleProject = new Project();
            sampleProject.setName("Sample Project");
            sampleProject.setDescription("This is a sample project created at startup.");
            sampleProject.setCreatedAt(java.time.ZonedDateTime.now());
            projectService.createProject(sampleProject);
        };
    }

}
