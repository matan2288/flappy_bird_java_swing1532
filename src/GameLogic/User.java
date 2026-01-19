package GameLogic;

public class User {
    private String username = "";
    private int userScore = 0;
    private int userDifficulty = 3;
    private static boolean musicEnabled = true; // Static so it persists across games

    public String getUserName() {
        return username;
    }

    public int getUserScore() {
        return userScore;
    }

    public int getUserDifficulty() {
        return userDifficulty;
    }

    public void setUserName(String passedUsername) {
        username = passedUsername;
    }

    public void setUserScore(int passedUserScore) {
        userScore = passedUserScore;
    }

    public void setUserDifficulty(int passedUserDifficulty) {
        userDifficulty = passedUserDifficulty;
    }

    public static boolean isMusicEnabled() {
        return musicEnabled;
    }

    public static void setMusicEnabled(boolean enabled) {
        musicEnabled = enabled;
    }

    public void resetCurrentUserObject() {
        this.username = "";
        this.userScore = 0;
        this.userDifficulty = 3;
    }
}
