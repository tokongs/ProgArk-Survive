package com.mygdx.progarksurvive;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidGraphics;





public class AndroidLauncher extends AndroidApplication implements PlayServices{



	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new DependencyRoot(this), config);

	}

	@Override
	public void signIn() {

	}

	@Override
	public void signOut() {
		this.finishAffinity();
	}

	@Override
	public void submitScore(String leaderboardId, int highScore) {

	}

	@Override
	public void showLeaderboard(String leaderboardId) {

	}

	@Override
	public boolean isSignedIn() {
		return false;
	}
}
