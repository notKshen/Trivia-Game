package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Represents an account having an id, owner name and balance (in dollars)
public class TriviaGame implements Writable {
    private static int counter = 0;
    private int counterDisplay;
    private int accuracy;
    private HashMap<String, String> questionBank;
    private boolean win;

    public TriviaGame() {
        questionBank = new HashMap<>();
        counter++;
        counterDisplay = counter;
        accuracy = 0;
        win = false;
    }

    // EFFECTS sets Win
    public void setWin(Boolean b) {
        this.win = b;
    }

    // EFFECTS gets Win
    public boolean getWin() {
        return win;
    }

    // REQUIRES: Input be a question from questionBank
    // EFFECTS: Gets Answer from question bank
    public String getAnswer(String str) {
        return questionBank.get(str);
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    // REQUIRES: total > 0
    // EFFECTS: calculates and sets accuracy
    public void setAccuracy(double corr, double total) {
        accuracy = (int) (100 * (corr / total));
    }

    public int getAccuracy() {
        return accuracy;
    }

    // EFFECTS: returns Game 1, Game 2, (increments game for every new Trivia Game)
    public String getName() {
        return "Game " + counterDisplay;
    }

    public void setQuestions(HashMap<String, String> hash) {
        questionBank = hash;
    }

    public HashMap<String, String> getQuestionBank() {
        return questionBank;
    }

    // EFFECTS: Returns a random question from the question bank or empty string
    public String getQuestion() {
        int rand = (int) (Math.random() * questionBank.size());
        int i = 0;
        for (String s : questionBank.keySet()) {
            if (i == rand) {
                return s;
            }
            i++;
        }
        return "";
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("accuracy", accuracy);
        json.put("game", counterDisplay);
        return json;
    }
}

