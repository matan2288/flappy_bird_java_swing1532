package GameLogic;

public class User {
    private String username = "";
    private int userScore = 0;

    public String getUserName() {
        return username;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserName(String passedUsername) {
        username = passedUsername;
    }

    public void setUserScore(int passedUserScore) {
        userScore = passedUserScore;
    }

    public void resetCurrentUserObject() {
        this.username = "";
        this.userScore = 0;
    }
}
