package com.example.socialnetworkui.repository;

import com.example.socialnetworkui.domain.Entity;
import com.example.socialnetworkui.domain.User;
import com.example.socialnetworkui.validator.Validator;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    Map<ID, E> entities;
    private final Validator<E> validator;

    public Map<ID, E> getEntities() {
        return entities;
    }

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public E add(E entity) {
        if (entity == null) {
            throw new RepositoryException("id must not be null!");
        }
        if (entities.get(entity.getID()) != null) {
            return entity;
        }
        validator.validate(entity);
        entities.put(entity.getID(), entity);
        return null;
    }

    @Override
    public E remove(ID id) {
        if (id == null) {
            throw new RepositoryException("id must not be null!");
        }
        if (entities.get(id) != null) {
            E entity = entities.get(id);
            entities.remove(id);
            return entity;
        }
        return null;
    }

    @Override
    public int size() {
        return entities.size();
    }

    @Override
    public E search(ID id) {
        if (id == null) {
            throw new RepositoryException("id must not be null!");
        }
        if (entities.get(id) != null) {
            return entities.get(id);
        }
        return null;
    }

}

