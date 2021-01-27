package sample;

public class Record {

    private int userID;
    private String name;
    private int score;

    public Record ( int userID, String name ) {
        this.userID = userID;
        this.name = name;
        this.score = 0;
    }

    public Record ( int userID, String name, int score ) {
        this.userID = userID;
        this.name = name;
        this.score = score;
    }

    public Record(){
        this.userID = 0;
        this.name = "";
        this.score = 0;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public void updateScore(int score){this.score+=score;}
}
