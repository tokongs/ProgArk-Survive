package com.mygdx.progarksurvive.networking;

/**
 * Generic interface for handling events received by the client or host of a game.
 * @param <T>
 */
public interface UpdateEventHandler<T> {
    void handleEvent(T event);
}
