package com.mygdx.progarksurvive.networking;

public interface UpdateEventHandler<T> {
    void handleEvent(T event);
}
