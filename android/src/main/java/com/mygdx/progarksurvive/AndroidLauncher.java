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
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.ConnectionResult;
import com.mygdx.progarksurvive.di.DependencyRoot;


public class AndroidLauncher extends AndroidApplication implements PlayServices{
	private GoogleSignInClient mGoogleSignInClient;

	private LeaderboardsClient mLeaderboardsClient;

	public ConnectionResult connectionResult;

	private static final int RC_SIGN_IN = 9001;
	private static final int RC_LEADERBOARD_UI = 9004;

	private static final String TAG = "Playservices test";
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new DependencyRoot(this), config);
		mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
		this.signIn();
	}

	@Override
	public void signIn() {

		if(!isSignedIn()){
			Intent signInIntent = mGoogleSignInClient.getSignInIntent();
			startActivityForResult(signInIntent, RC_SIGN_IN);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if (result.isSuccess()) {
				onConnected();
			}
			else {

				String message = String.valueOf(result.getStatus().getStatusCode());

				if (message == null || message.isEmpty()) {
					message = getString(R.string.signin_other_error);
				}
				new AlertDialog.Builder(this).setMessage(message)
						.setNeutralButton(android.R.string.ok, null).show();
			}
		}
	}
	private void onConnected() {
		Log.d(TAG, "onConnected(): connected to Google APIs");
	}
	private void onDisconnected() {
		Log.d(TAG, "onDisconnected()");
	}

	@Override
	public void signOut() {
		mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						onDisconnected();
					}
				});
	}

	@Override
	public void submitScore(String leaderboardId, int highScore) {
		if (isSignedIn()) {
			Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this)).submitScore(leaderboardId, highScore);
		}
	}

	@Override
	public void showLeaderboard(String leaderboardId) {
		if (isSignedIn()) {
			Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
					.getLeaderboardIntent(leaderboardId)
					.addOnSuccessListener(new OnSuccessListener<Intent>() {
						@Override
						public void onSuccess(Intent intent) {
							startActivityForResult(intent, RC_LEADERBOARD_UI);
						}
					});
		}
	}

	@Override
	public boolean isSignedIn() {
		return GoogleSignIn.getLastSignedInAccount(this) != null;
	}
	@Override
	public void revokeAccess() {
		mGoogleSignInClient.revokeAccess()
				.addOnCompleteListener(this, new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						// ...
					}
				});
	}
}
