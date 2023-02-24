package ui;


import model.TriviaGame;
import model.TriviaGameHistory;
import java.util.List;
import java.util.Scanner;

// PLay game application
public class PlayGameApp {
    private int plives = 3;
    private int clives = 3;
    private double total = 0;
    private double corr = 0;
    private TriviaGameHistory tgh = new TriviaGameHistory();
    private Scanner scan = new Scanner(System.in);
    private String [] arr = {"A","B","C","D","E"};


    // Runs Trivia Game Application
    public PlayGameApp() {
        System.out.println("Hello and Welcome to the Super Amazing Trivia Game! The rules are simple,");
        System.out.println("There are lives in this trivia game and when you get a question wrong you lose a life....");
        System.out.println("Unless the computer also answers wrong, then it is a draw.");
        System.out.println("Between you and the computer whoever loses all of their lives first loses");
        System.out.println("Good luck Challenger!");
        System.out.println("Answer in (A, B, C, D, E)");
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
            System.out.println("1 to start new game, 2 to view past games, 3 to quit");
        } else {
            System.out.println("You Won!");
            System.out.println("1 to start new game, 2 to view past games, 3 to quit");
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
            player.setEoscQuestions();
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
}
