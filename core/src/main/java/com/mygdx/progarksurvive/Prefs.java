package com.mygdx.progarksurvive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Prefs {

    private Preferences prefs;
    private boolean hasSound;
    private boolean  hasMusic;

    public Prefs(){
        prefs = Gdx.app.getPreferences("My Preferences");
        hasSound = prefs.getBoolean("hasSound",true);
        hasMusic = prefs.getBoolean("hasMusic",true);
    }


    public void setSound(boolean hasSound){
        this.hasSound=hasSound;
        prefs.putBoolean("hasSound",hasSound);
        prefs.flush();
    }
    public void setMusic(boolean hasMusic){
        this.hasMusic=hasMusic;
        prefs.putBoolean("hasMusic",hasMusic);
        prefs.flush();
    }
    public boolean hasSound(){
        return hasSound;
    }
    public boolean hasMusic(){
        return hasMusic;
    }

}
