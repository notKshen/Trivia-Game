package ui;


import model.TriviaGame;
import model.TriviaGameHistory;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


// PLay game application
public class PlayGameApp extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/TriviaGame.json";
    private static final String JSON_STORE1 = "./data/QuestionBank.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JsonReader jsonReader1;
    private int plives = 3;
    private int clives = 3;
    private double total = 0;
    private double corr = 0;
    private int counter = 0;
    private TriviaGameHistory tgh = new TriviaGameHistory();
    private Scanner scan = new Scanner(System.in);
    private String [] arr = {"A","B","C","D","E"};
    private JTextField textField = new JTextField();
    private JTextArea textArea = new JTextArea();
    private JButton button1 = new JButton();
    private JButton button2 = new JButton();
    private JButton button3 = new JButton();
    private JButton button4 = new JButton();
    private JLabel answerLabelA = new JLabel();
    private JLabel answerLabelB = new JLabel();
    private JLabel answerLabelC = new JLabel();
    private JLabel answerLabelD = new JLabel();





    // Runs Trivia Game Application
    public PlayGameApp() throws FileNotFoundException {
        super("Trivia Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200,850));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        getContentPane().setBackground(new Color(238,250,253));
        setLayout(null);
        title();
        rules();
        newOrLoad();

//        textField.setBounds(0,0,1200,100);
//        textField.setBackground(new Color(238,250,253));
//        textField.setBorder(null);
//        textField.setHorizontalAlignment(SwingConstants.CENTER);
//        textField.setEditable(false);
//        textField.setFont(new Font("Arial",Font.BOLD,50));
//        textField.setText("TEST");
//
//        textArea.setBounds(0,100,1200,100);
//        textArea.setBackground(new Color(238,250,253));
//        textArea.setBorder(null);
//        textArea.setEditable(false);
//        textArea.setFont(new Font("Arial",Font.BOLD,25));
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);
//        textArea.setText("SAMPLE TEXT");
//
//        button1.setBounds(0,150,100,100);
//        button1.setFont(new Font("Arial",Font.BOLD,50));
//        button1.setFocusable(false);
//        button1.addActionListener(this);
//        button1.setText("A");
//
//        button2.setBounds(0,250,100,100);
//        button2.setFont(new Font("Arial",Font.BOLD,50));
//        button2.setFocusable(false);
//        button2.addActionListener(this);
//        button2.setText("B");
//
//        button3.setBounds(0,350,100,100);
//        button3.setFont(new Font("Arial",Font.BOLD,50));
//        button3.setFocusable(false);
//        button3.addActionListener(this);
//        button3.setText("C");
//
//        button4.setBounds(0,450,100,100);
//        button4.setFont(new Font("Arial",Font.BOLD,50));
//        button4.setFocusable(false);
//        button4.addActionListener(this);
//        button4.setText("D");
//
//        answerLabelA.setBounds(150,150,500,100);
//        answerLabelA.setBackground(new Color(238,250,253));
//        answerLabelA.setFont(new Font("Arial",Font.PLAIN,20));
//        answerLabelA.setText("TEST");
//
//        answerLabelB.setBounds(150,250,500,100);
//        answerLabelB.setBackground(new Color(238,250,253));
//        answerLabelB.setFont(new Font("Arial",Font.PLAIN,20));
//        answerLabelB.setText("TEST");
//
//        answerLabelC.setBounds(150,350,500,100);
//        answerLabelC.setBackground(new Color(238,250,253));
//        answerLabelC.setFont(new Font("Arial",Font.PLAIN,20));
//        answerLabelC.setText("TEST");
//
//        answerLabelD.setBounds(150,450,500,100);
//        answerLabelD.setBackground(new Color(238,250,253));
//        answerLabelD.setFont(new Font("Arial",Font.PLAIN,20));
//        answerLabelD.setText("TEST");



        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        jsonReader1 = new JsonReader(JSON_STORE1);
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

    // EFFECTS: load title
    public void title() {
        textField.setBounds(0,10,1200,100);
        textField.setBackground(new Color(238,250,253));
        textField.setBorder(null);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setEditable(false);
        textField.setFont(new Font("Ariel",Font.BOLD,100));
        textField.setText("Cool Trivia Game !");
        add(textField);
    }

    // EFFECTS: rules
    public void rules() {
        textArea.setBounds(10,175,1100,200);
        textArea.setBackground(new Color(238,250,253));
        textArea.setBorder(null);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial",Font.PLAIN,25));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText("Hello and Welcome to the Super Amazing Trivia Game! The rules are simple, "
                + "There are lives in this trivia game and when you get a question wrong you lose a life.... "
                + "Unless the computer also answers wrong, then it is a draw. "
                + "Between you and the computer whoever loses all of their lives first loses.\n"
                + "Good luck Challenger! \nAnswer in (A, B, C, D, E)");
        add(textArea);
    }

    // EFFECTS: new or load profile button
    public void newOrLoad() {
        button1.setBounds(350,400,500, 200);
        button1.setFont(new Font("Arial",Font.BOLD,25));
        button1.setFocusable(false);
        button1.addActionListener(this);
        button1.setActionCommand("new");
        button1.setText("New Profile");
        add(button1);

        button2.setBounds(350,600,500, 200);
        button2.setFont(new Font("Arial",Font.BOLD,25));
        button2.setFocusable(false);
        button2.addActionListener(this);
        button2.setActionCommand("load");
        button2.setText("Load Profile");
        add(button2);
    }

    public void nextQuestion() {
        textField.setBounds(0,0,1200,100);
        textField.setFont(new Font("Arial",Font.BOLD,50));
        textField.setText("Question " + counter);
        textArea.setBounds(0,100,1200,100);
        textArea.setFont(new Font("Arial",Font.BOLD,25));
        textArea.setText("SAMPLE TEXT");
        setButton();
        setAnswer();
    }

    // EFFECTS: sets button
    public void setButton() {
        button1.setBounds(0,150,100,100);
        button1.setFont(new Font("Arial",Font.BOLD,50));
        button1.setActionCommand("b1");
        button1.setText("A");
        button2.setBounds(0,250,100,100);
        button2.setFont(new Font("Arial",Font.BOLD,50));
        button2.setActionCommand("b2");
        button2.setText("B");
        button3.setBounds(0,350,100,100);
        button3.setFont(new Font("Arial",Font.BOLD,50));
        button3.setFocusable(false);
        button3.addActionListener(this);
        button3.setActionCommand("b3");
        button3.setText("C");
        button4.setBounds(0,450,100,100);
        button4.setFont(new Font("Arial",Font.BOLD,50));
        button4.setFocusable(false);
        button4.addActionListener(this);
        button4.setActionCommand("b4");
        button4.setText("D");
    }

    // EFFECTS: set answer
    public void setAnswer() {
        answerLabelA.setBounds(150,150,500,100);
        answerLabelA.setBackground(new Color(238,250,253));
        answerLabelA.setFont(new Font("Arial",Font.PLAIN,20));
        answerLabelA.setText("TEST");

        answerLabelB.setBounds(150,250,500,100);
        answerLabelB.setBackground(new Color(238,250,253));
        answerLabelB.setFont(new Font("Arial",Font.PLAIN,20));
        answerLabelB.setText("TEST");

        answerLabelC.setBounds(150,350,500,100);
        answerLabelC.setBackground(new Color(238,250,253));
        answerLabelC.setFont(new Font("Arial",Font.PLAIN,20));
        answerLabelC.setText("TEST");

        answerLabelD.setBounds(150,450,500,100);
        answerLabelD.setBackground(new Color(238,250,253));
        answerLabelD.setFont(new Font("Arial",Font.PLAIN,20));
        answerLabelD.setText("TEST");
    }

    // EFFECTS: next past of game
    public void nextStep() {
        button1.setBounds(0,170,1200,200);
        button1.setFont(new Font("Arial",Font.BOLD,50));
        button1.setActionCommand("new game");
        button1.setText("New Game");
        button2.setBounds(0,370,1200,200);
        button2.setFont(new Font("Arial",Font.BOLD,50));
        button2.setActionCommand("past game");
        button2.setText("View Past Games");
        button3.setBounds(0,570,1200,200);
        button3.setFont(new Font("Arial",Font.BOLD,50));
        button3.setFocusable(false);
        button3.addActionListener(this);
        button3.setActionCommand("quit");
        button3.setText("Quit");
        add(button3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            getContentPane().remove(textArea);
            loadTriviaGameHistory();
            nextStep();
        } else if (e.getActionCommand().equals("new")) {
            getContentPane().remove(textArea);
            nextStep();
        } else if (e.getActionCommand().equals("new game")) {
            getContentPane().removeAll();
        } else if (e.getActionCommand().equals("past game")) {
            getContentPane().removeAll();
        } else if (e.getActionCommand().equals("quit")) {
            getContentPane().removeAll();
        }
    }
}
