package com.example.socialnetworkui.service;

import com.example.socialnetworkui.domain.Entity;

/**
 * Service interface that implements more complex operations
 *
 * @ID each entity must have an ID
 * @E type of entities that are added to the service
 */
public interface ServiceInterface<ID, E extends Entity<ID>> {
    /**
     * Function that adds an entity to the service
     *
     * @param entity must not be null
     * @return null, if the element was added successfully. Returns the entity otherwise
     * @throws ServiceException if entity is null
     */
    E addService(E entity);

    /**
     * Function that removes an entity from the service with a specified id
     *
     * @param id must not be null
     * @return null, if the entity wasn't deleted. Returns the entity otherwise
     * @throws ServiceException if id is null
     */
    E removeService(ID id);

    /**
     * Function that searches an entity in the service
     *
     * @param id must not be null
     * @return the entity at the specified id, null otherwise
     * @throws ServiceException if id is null
     */
    E searchService(ID id);
}

