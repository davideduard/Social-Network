package com.example.socialnetworkui.domain;
import java.io.Serial;
import java.io.Serializable;

public class Entity<ID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 12345L;
    protected ID id;

    public Entity(ID id) {
        this.id = id;
    }

    public ID getID() {
        return id;
    }

}