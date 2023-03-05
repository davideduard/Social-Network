package com.example.socialnetworkui.utilities.observer;

import com.example.socialnetworkui.utilities.events.Event;

public interface Observer <E extends Event>{
    void update(E e);
}
