package ui;


import model.TriviaGame;
import model.TriviaGameHistory;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// PLay game application
public class PlayGameApp {
    private static final String JSON_STORE = "./data/TriviaGame.json";
    private static final String JSON_STORE1 = "./data/QuestionBank.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JsonReader jsonReader1;
    private int plives = 3;
    private int clives = 3;
    private double total = 0;
    private double corr = 0;
    private TriviaGameHistory tgh = new TriviaGameHistory();
    private Scanner scan = new Scanner(System.in);
    private String [] arr = {"A","B","C","D","E"};


    // Runs Trivia Game Application
    public PlayGameApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        jsonReader1 = new JsonReader(JSON_STORE1);
        System.out.println("Hello and Welcome to the Super Amazing Trivia Game! The rules are simple,");
        System.out.println("There are lives in this trivia game and when you get a question wrong you lose a life....");
        System.out.println("Unless the computer also answers wrong, then it is a draw.");
        System.out.println("Between you and the computer whoever loses all of their lives first loses");
        System.out.println("Good luck Challenger!");
        System.out.println("Answer in (A, B, C, D, E)");
        System.out.println("n to start a new profile\nl to load a previous profile");
        String ans = scan.nextLine();
        if (ans.equals("l")) {
            loadTriviaGameHistory();
        }
        System.out.println("1 to start new game, 2 to view past games, 3 to quit");
        startGame();
    }

    // MODIFIES: this
    // EFFECTS: Runs Game, 1 starts new game, 2 views past games, 3 ends game
    public void startGame() {
        String ans = scan.nextLine();
        while (!ans.equals("3")) {
            plives = 3;
            clives = 3;
            total = 0;
            corr = 0;
            if (ans.equals("1")) {
                runGame();
                ans = scan.nextLine();
            } else if (ans.equals("2")) {
                viewPastGames();
                ans = scan.nextLine();
            } else {
                System.out.println("That's not an option!");
                System.out.println("1 to start new game, 2 to view past games, 3 to quit");
                ans = scan.nextLine();
            }
            if (ans.equals("3")) {
                askSaveGame();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Starts a new game, ends when player lives or computer lives hits 0
    public void runGame() {
        TriviaGame player = new TriviaGame();
        setQuestions(player);
        while ((!(plives == 0)) && (!(clives == 0))) {
            playRound(player);
            System.out.println("");
        }
        player.setAccuracy(corr, total);
        tgh.addGame(player);
        if (plives == 0) {
            System.out.println("Oh No You Lost D:");
        } else {
            System.out.println("You Won!");
        }
        System.out.println("1 to start new game, 2 to view past games, 3 to quit");
    }

    public void askSaveGame() {
        System.out.println("Would you like to save your game history? Y/N");
        String ans = scan.nextLine();
        if (ans.equals("Y")) {
            saveTriviaGameHistory();
        }
    }

    // EFFECTS: Prints to console all games played with accuracy for each game and personal stats based on all games
    public void viewPastGames() {
        List<TriviaGame> temp = tgh.getListOfGames();
        if (temp.size() == 0) {
            System.out.println("No Games played");
        } else {
            for (TriviaGame t : temp) {
                System.out.println(t.getName() + ", Accuracy: " + t.getAccuracy() + "%,");
            }
            System.out.println("\nPersonal Stats:\n"
                    + "Total Games Played: "
                    + tgh.totalGamesPlayed() + ", Total Accuracy: " + tgh.averageAccuracy() + "%");
        }
        System.out.println("1 to start new game, 2 to view past games, 3 to quit");
    }

    // MODIFIES: this
    // EFFECTS: Sets Question Category
    public void setQuestions(TriviaGame player) {
        System.out.println("What category would you like to choose?\nEosc");
        String cat = scan.nextLine();
        if (cat.equals("Eosc")) {
            player.setQuestions(loadQuestions());
        }
    }

    // MODIFIES: this
    // EFFECTS: Plays a round of the trivia game
    public void playRound(TriviaGame player) {
        String question = player.getQuestion();
        String correct = player.getAnswer(question);
        System.out.println(question);
        String answer = scan.nextLine();
        System.out.println("Player Answer: " + answer);
        if (answer.equals(correct)) {
            if (computerChoice(correct).equals(correct)) {
                System.out.println("Tie!");
            } else {
                System.out.println("Good job!");
                clives--;
            }
            corr++;
            afterRound(correct);
        } else {
            if (computerChoice(correct).equals(correct)) {
                System.out.println("Noooooo!");
                plives--;
            } else {
                System.out.println("Tie!");
            }
            afterRound(correct);
        }
    }

    // EFFECTS: returns a randomized computer answer from (A, B, C, D, E)
    public String computerChoice(String ans) {
        int rand = (int) (Math.random() * 2);
        int rand1 = (int) (Math.random() * 5);
        if (rand == 0) {
            System.out.println("Computer choice: " + ans);
            return ans;
        } else {
            System.out.println("Computer choice: " + arr[rand1]);
            return (arr[rand1]);
        }
    }

    // EFFECTS: Returns lives updates after each round and increments total questions answered
    public void afterRound(String correct) {
        total++;
        System.out.println("Correct Answer: " + correct);
        System.out.println("Player Lives: " + plives);
        System.out.println("Computer Lives: " + clives);
    }

    // EFFECTS: saves the Game History to file
    private void saveTriviaGameHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(tgh);
            jsonWriter.close();
            System.out.println("Saved play history to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: loads Game History from file
    private void loadTriviaGameHistory() {
        try {
            tgh = jsonReader.read();
            System.out.println("Loaded play history from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Questions from file
    private HashMap<String, String> loadQuestions() {
        try {
            return jsonReader1.ready();
        } catch (IOException e) {
            //
        }
        return null;
    }
}
