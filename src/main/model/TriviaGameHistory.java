package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents an account having an id, owner name and balance (in dollars)
public class TriviaGameHistory implements Writable {
    private List<TriviaGame> loG;

    public TriviaGameHistory() {
        loG = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a Trivia Game to a list of Trivia Games
    public void addGame(TriviaGame tg) {
        loG.add(tg);
    }

    // EFFECTS: returns total amount of games played
    public int totalGamesPlayed() {
        return loG.size();
    }

    // calculates average accuracy of all Trivia Games in the list
    public int averageAccuracy() {
        int total = 0;
        int acc = 0;
        for (TriviaGame t : loG) {
            acc += t.getAccuracy();
            total++;
        }
        return (acc / total);
    }

    // returns list of Trivia Games
    public List<TriviaGame> getListOfGames() {
        return loG;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("loG", loGToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray loGToJson() {
        JSONArray jsonArray = new JSONArray();

        for (TriviaGame t : loG) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
