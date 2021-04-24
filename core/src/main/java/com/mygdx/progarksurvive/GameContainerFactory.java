package com.mygdx.progarksurvive;

import dagger.assisted.AssistedFactory;

@AssistedFactory
public interface GameContainerFactory {
    GameContainer create(Test test);
}
