package com.example.socialnetworkui.service;

import com.example.socialnetworkui.domain.Entity;
import com.example.socialnetworkui.repository.Repository;

import java.util.Map;

public class Service<ID, E extends Entity<ID>> implements ServiceInterface<ID, E>{
    protected final Repository<ID, E> repository;

    public Service(Repository<ID, E> Repository) {
        this.repository = Repository;
    }

    public Map<ID, E> getAll() {
        return repository.getEntities();
    }

    public Integer size() {
        return repository.size();
    }

    public E addService(E entity) {
        if (entity == null) {
            throw new ServiceException("entity cannot be null!");
        }
        if (repository.add(entity) != null) {
            return entity;
        }
        return null;
    }

    public E removeService(ID id) {
        if (id == null) {
            throw new ServiceException("id cannot be null!");
        }
        return repository.remove(id);
    }

    public E searchService(ID id) {
        return repository.search(id);
    }

}
