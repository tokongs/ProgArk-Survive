package com.mygdx.progarksurvive;

public interface PlayServices {
    public void signIn();
    public void signOut();
    public void submitScore(String leaderboardId, int highScore);
    public void showLeaderboard(String leaderboardId);
    public void revokeAccess();
    public boolean isSignedIn();
}
