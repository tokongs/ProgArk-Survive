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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


public class AndroidLauncher extends AndroidApplication implements PlayServices{
	private GoogleSignInClient mGoogleSignInClient;
	private GoogleSignInAccount mGoogleSignInAccount;

	private LeaderboardsClient mLeaderboardsClient;



	private static final int RC_SIGN_IN = 9001;
	private static final int RC_LEADERBOARD_UI = 9004;

	private static final String TAG = "Playservices test";
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new DependencyRoot(this), config);
		mGoogleSignInClient = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());
	}

	@Override
	public void signIn() {
		if(!isSignedIn()){
			Intent signInIntent = mGoogleSignInClient.getSignInIntent();
			startActivityForResult(signInIntent, RC_SIGN_IN);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == RC_SIGN_IN) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);

			try {
				GoogleSignInAccount account = task.getResult(ApiException.class);
				onConnected(account);
			} catch (ApiException apiException) {
				String message = apiException.getMessage();
				if (message == null || message.isEmpty()) {
					message = "Mistet vist koblingen";
				}

				onDisconnected();

				new AlertDialog.Builder(this)
						.setMessage(message)
						.setNeutralButton(android.R.string.ok, null)
						.show();
			}
		}
	}
	private void onConnected(GoogleSignInAccount googleSignInAccount) {
		Log.d(TAG, "onConnected(): connected to Google APIs");

		mGoogleSignInAccount = googleSignInAccount;

		mLeaderboardsClient = Games.getLeaderboardsClient(this, googleSignInAccount);


	}
	private void onDisconnected() {
		Log.d(TAG, "onDisconnected()");

		mLeaderboardsClient = null;
	}

	@Override
	public void signOut() {
		Log.d(TAG, "signOut()");

		if (!isSignedIn()) {
			Log.w(TAG, "signOut() called, but was not signed in!");
			return;
		}
		mGoogleSignInClient.signOut().addOnCompleteListener(this,
				new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						boolean successful = task.isSuccessful();
						Log.d(TAG, "signOut(): " + (successful ? "success" : "failed"));

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
}
