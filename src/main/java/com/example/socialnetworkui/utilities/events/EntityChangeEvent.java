package com.example.socialnetworkui.utilities.events;

import com.example.socialnetworkui.domain.Entity;

public class EntityChangeEvent implements Event{
    private final ChangeEventType type;
    private final Entity data;
    private Entity oldData;

    public EntityChangeEvent(ChangeEventType type, Entity data) {
        this.type = type;
        this.data = data;
    }

    public EntityChangeEvent(ChangeEventType type, Entity data, Entity oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType(){
        return type;
    }

    public Entity getData(){
        return data;
    }

    public Entity getOldData(){
        return oldData;
    }
}
