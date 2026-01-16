package GameLogic;

public class User {
    private String username;
    private int userScore;

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
    
}
