package org.rossijr.basiccrud.repository;

import org.rossijr.basiccrud.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <ul>
 * <li>Repository interface for managing {@link Project} entities.</li>
 * <li>In this layer, the database operations are defined, which means that this layer interacts with the database.</li>
 * <ul>
 * <li>{@code @Repository}: Indicates that this interface is a Spring repository.</li>
 * <li>{@code JpaRepository<Project, Long>}: A Spring Data interface that provides CRUD operations for the {@link Project} entity.</li>
 * <ul>
 *     <li>{@code Task}: The entity type this repository manages.</li>
 *     <li>{@code Long}: The type of the entity's ID field.</li>
 * </ul>
 * </ul>
 * </ul>
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
