package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import model.TriviaGame;
import model.TriviaGameHistory;
import org.json.*;

// Represents a reader that reads TriviaGameHistory from JSON data stored in file, from JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads TriviaGameHistory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TriviaGameHistory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTriviaGameHistory(jsonObject);
    }

    // EFFECTS: reads Questions from file and returns it;
    // throws IOException if an error occurs reading data from file
    public HashMap<String, String> ready() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return addQuestions(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses TriviaGameHistory from JSON object and returns it
    private TriviaGameHistory parseTriviaGameHistory(JSONObject jsonObject) {
        TriviaGameHistory tgh = new TriviaGameHistory();
        addloG(tgh, jsonObject);
        return tgh;
    }

    // MODIFIES: tgh
    // EFFECTS: parses loG from JSON object and adds them to TriviaGameHistory
    private void addloG(TriviaGameHistory tgh, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("loG");
        for (Object json : jsonArray) {
            JSONObject nextTriviaGame = (JSONObject) json;
            addTriviaGame(tgh, nextTriviaGame);
        }
    }

    // EFFECTS: parses Questions from JSON object and adds them to TriviaGame
    private HashMap<String,String> addQuestions(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("QuestionBank");
        HashMap<String,String> hashme = new HashMap<>();
        for (Object json : jsonArray) {
            JSONObject nextString = (JSONObject) json;
            add(hashme, nextString);

        }
        return hashme;
    }

    // MODIFIES: tgh
    // EFFECTS: parses TriviaGame from JSON object and adds it to TriviaGameHistory
    private void addTriviaGame(TriviaGameHistory tgh, JSONObject jsonObject) {
        int accuracy = jsonObject.getInt("accuracy");
        TriviaGame tg = new TriviaGame();
        tg.setAccuracy(accuracy);
        tgh.addGame(tg);
    }

    // MODIFIES: tgh
    // EFFECTS: parses Strings and adds to hashmap
    private void add(HashMap<String,String> hashme, JSONObject jsonObject) {
        String question = jsonObject.getString("question");
        String answer = jsonObject.getString("answer");
        hashme.put(question,answer);
    }

}
