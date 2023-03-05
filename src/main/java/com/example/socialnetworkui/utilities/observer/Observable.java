package com.example.socialnetworkui.utilities.observer;

import com.example.socialnetworkui.utilities.events.Event;

public interface Observable <E extends Event>{
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObserver(E e);
}
