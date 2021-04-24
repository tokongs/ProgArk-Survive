package com.mygdx.progarksurvive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.progarksurvive.networking.NetworkedGameClient;
import com.mygdx.progarksurvive.networking.NetworkedGameHost;
import com.mygdx.progarksurvive.networking.kryo.KryoClientDiscoveryHandler;
import com.mygdx.progarksurvive.networking.kryo.KryoNetworkedGameClient;
import com.mygdx.progarksurvive.networking.kryo.KryoNetworkedGameHost;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface GameModule {

    @Provides @Singleton
    static SpriteBatch provideSpriteBatch() { return new SpriteBatch(); }

    @Provides @Singleton
    static ShapeRenderer provideShapeRenderer() { return new ShapeRenderer(); }

    @Provides @Singleton
    static World provideWorld() { return new World(new Vector2(0, 0), true); }

    @Provides @Singleton
    static Engine provideEngine() {return new Engine(); }

    @Provides @Singleton
    static Client provideClient() {
        return new Client();
    }

    @Provides @Singleton
    static Server provideServer() {
        return new Server();
    }

    @Provides @Singleton
    static AssetManager provideAssetManager() {
        AssetManager assetManager = new AssetManager();
        assetManager.load("images/player.png", Texture.class);
        for(int i=1; i <= 9; i++){
            assetManager.load("images/PlayerTexture" + i + ".png", Texture.class);
            assetManager.load("images/Zombie1Texture" + i + ".png", Texture.class);
        }
        assetManager.load("images/BulletTexture.png", Texture.class);
        assetManager.load("skin/uiskin.json", Skin.class);
        return assetManager;
    }

    @Provides
    static KryoClientDiscoveryHandler provideKryoClientDiscoveryHandler() {
        return new KryoClientDiscoveryHandler();
    }

    @Binds
    NetworkedGameClient bindNetworkedGameClient(KryoNetworkedGameClient impl);

    @Binds
    NetworkedGameHost bindNetworkedGameHost(KryoNetworkedGameHost impl);

    @Binds
    Game bindGame(Main impl);
}