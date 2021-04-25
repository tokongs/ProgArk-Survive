package com.mygdx.progarksurvive.di;

import com.mygdx.progarksurvive.PlayServices;
import dagger.assisted.AssistedFactory;

@AssistedFactory
public interface GameContainerFactory {
    GameContainer create(PlayServices playServices);
}
