package Server;

import sample.Record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UpdateScore {

    public ArrayList<Record> ranking = new ArrayList<>();

    public void sortHighscore () {
        ranking.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.getScore() - o2.getScore();
            }
        });
    }
}

