package sample;

public class Record {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private String name;
    private int score;

    public Record ( int score, String name ) {
        this.name = name;
        this.score = score;
    }
}
