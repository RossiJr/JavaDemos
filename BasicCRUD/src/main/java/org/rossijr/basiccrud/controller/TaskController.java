package org.rossijr.basiccrud.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.rossijr.basiccrud.dto.TaskCreateRequestDTO;
import org.rossijr.basiccrud.dto.TaskGetResponseDTO;
import org.rossijr.basiccrud.dto.TaskUpdateRequestDTO;
import org.rossijr.basiccrud.model.Task;
import org.rossijr.basiccrud.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <ul>
 * <li>REST controller for managing {@link Task} entities.</li>
 * <li>In this layer, the API endpoints are defined, which means that this layer interacts with the client.</li>
 * <ul>
 * <li>{@code @RestController}: Indicates that this class is a Spring REST controller.</li>
 * <li>{@code @RequestMapping("/api/v1/task")}: Specifies the base URL path for all endpoints in this controller.</li>
 * </ul>
 * </ul>
 */
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    /**
     * Constructor-based dependency injection.
     * <ul>
     * <li>{@code @Autowired}: Indicates that this dependency is automatically injected by Spring.</li>
     * </ul>
     *
     * @param taskService the {@link TaskService} instance to be injected.
     */
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * <ul>
     *     <li>GET endpoint to retrieve all tasks from the database.</li>
     *     <li>The annotation {@code @GetMapping} specifies that this method will handle HTTP GET requests.</li>
     * </ul>
     *
     * @return a {@link ResponseEntity} containing a list of {@link TaskGetResponseDTO} objects.
     */
    @GetMapping
    public ResponseEntity<List<TaskGetResponseDTO>> getAllTasks() {
        // Pass to the service layer the responsibility of retrieving all tasks from the database and converting them to DTOs (business logic)
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    /**
     * <ul>
     *     <li>GET endpoint to retrieve a task by its ID from the database.</li>
     *     <li>The annotation {@code @GetMapping("/{id}")} specifies that this method will handle HTTP GET requests with a path variable. Example:
     *     {@code /api/v1/tasks/1} will retrieve the task with ID 1.</li>
     *     <li>It could also be done using a request parameter ({@code @RequestParam}) instead of a path variable ({@code PathVariable}).</li>
     *     <ul>
     *         <li>To choose depends on the use case</li>
     *         <ul>
     *             <li>Usually, path variables are used when the parameter is part of the URL path (like an ID)</li>
     *             <li>Request parameters are used when the parameter is optional (like filters and pagination)</li>
     *         </ul>
     *         <li>Example of request parameter: {@code @GetMapping("/search") public ResponseEntity<List<TaskGetResponseDTO>> searchTasks(@RequestParam String query)} - {@code
     *         /api/v1/tasks/search?query=example}</li>
     *     </ul>
     *
     * @param id the ID of the task to be retrieved.
     * @return a {@link ResponseEntity} containing a {@link TaskGetResponseDTO} object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskGetResponseDTO> getTaskById(@PathVariable Long id) {
        // Pass to the service layer the responsibility of retrieving a task by its ID from the database and converting it to a DTO (business logic)
        TaskGetResponseDTO task = taskService.getTaskById(id);

        // Return the task if it exists, or a 404 Not Found response if it does not
        return task != null ? ResponseEntity.ok(task)
                : ResponseEntity.notFound().build();
    }

    /**
     * <ul>
     *     <li>POST endpoint to create a new task in the database.</li>
     *     <li>The annotation {@code @PostMapping} specifies that this method will handle HTTP POST requests.</li>
     *     <li>The annotation {@code @Valid} validates the request body based on the constraints defined in the DTO class.</li>
     *     <li>The annotation {@code @RequestBody} binds the request body to the {@link TaskCreateRequestDTO} object.</li>
     * </ul>
     *
     * @param task a {@link TaskCreateRequestDTO} object containing the details of the task to be created.
     * @return a {@link ResponseEntity} containing a {@link TaskGetResponseDTO} object.
     */
    @PostMapping
    public ResponseEntity<TaskGetResponseDTO> createTask(@Valid @RequestBody TaskCreateRequestDTO task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task));
    }

    /**
     * <ul>
     *     <li>PUT endpoint to update a task in the database.</li>
     *     <li>The annotation {@code @PutMapping} specifies that this method will handle HTTP PUT requests.</li>
     *     <li>The annotation {@code @Valid} validates the request body based on the constraints defined in the DTO class.</li>
     *     <li>The annotation {@code @RequestBody} binds the request body to the {@link TaskUpdateRequestDTO} object.</li>
     *     <li>It is important to note that the ID of the task to be updated must be present in the request body.</li>
     * </ul>
     *
     * @param task a {@link TaskUpdateRequestDTO} object containing the details of the task to be updated.
     * @return a {@link ResponseEntity} containing a {@link TaskGetResponseDTO} object.
     */
    @PutMapping
    public ResponseEntity<TaskGetResponseDTO> updateTask(@Valid @RequestBody TaskUpdateRequestDTO task) {
        return ResponseEntity.ok(taskService.updateTask(task));
    }

    /**
     * <ul>
     *     <li>DELETE endpoint to delete a task by its ID from the database.</li>
     *     <li>The annotation {@code @DeleteMapping("/{id}")} specifies that this method will handle HTTP DELETE requests with a path variable.
     * <ul>
     *
     * @param id the ID of the task to be deleted.
     * @return a {@link ResponseEntity} with a 200 OK status if the task was deleted successfully, or a 404 Not Found status if the task does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable @Min(1) Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
