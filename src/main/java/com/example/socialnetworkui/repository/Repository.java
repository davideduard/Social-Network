package com.example.socialnetworkui.repository;

import com.example.socialnetworkui.domain.Entity;

import java.util.Map;

/**
 * Interface for a repository that does CRUD operations
 *
 * @ID -> type E must have an attribute of type ID
 * @E -> type of entity that is added to the repository
 */

public interface Repository<ID, E extends Entity<ID>> {
    /**
     * Function that adds an entity to the repository
     *
     * @param entity must not be null
     * @return null if the entity is saved
     * returns the entity if it already exists (same id)
     * @throws RepositoryException if id is null
     */
    E add(E entity);

    /**
     * Function that removes an entity from the repository
     *
     * @param id must not be null
     * @return returns the entity that has been removed
     * null otherwise
     * @throws RepositoryException if id is null
     */
    E remove(ID id);

    /**
     * Function that returns the size of the repository
     *
     * @return -> the size of the repository
     */
    int size();

    /**
     * Function that searches an entity into the repository
     *
     * @param id must not be null
     * @return returns the entity at the specified id
     * null if the element doesn't exist
     * @throws RepositoryException if  id is null
     */
    E search(ID id);

    /**
     * Function that returns the entities in the repository
     *
     * @return returns the entities in the repository
     */
    Map<ID, E> getEntities();
}