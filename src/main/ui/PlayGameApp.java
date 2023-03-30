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
    private TriviaGame player;
    private TriviaGameHistory tgh = new TriviaGameHistory();
    private Scanner scan = new Scanner(System.in);
    private String temp;
    private String [] arr = {"A","B","C","D","E"};
    private JTextField textField = new JTextField();
    private JTextArea textArea = new JTextArea();
    private JButton button1 = new JButton();
    private JButton button2 = new JButton();
    private JButton button3 = new JButton();
    private JButton button4 = new JButton();
    private JButton button5 = new JButton();
    private JLabel answerLabelA = new JLabel();
    private JLabel answerLabelB = new JLabel();
    private JLabel answerLabelC = new JLabel();
    private JLabel answerLabelD = new JLabel();
    private JLabel answerLabelE = new JLabel();
    private String[] header = {"Games Played", "Accuracy","Total Games Played","Total Accuracy"};
    private ImageIcon lives;
    private ImageIcon robot;
    private ImageIcon stickman;
    private JLabel imageAsLabel1;
    private JLabel imageAsLabel2;
    private JLabel imageAsLabel3;
    private JLabel imageAsLabel4;
    private JLabel imageAsLabel5;
    private JLabel imageAsLabel6;
    private JLabel imageAsLabel7;
    private JLabel imageAsLabel8;
    private JTable jt;




    // Runs Trivia Game Application
    public PlayGameApp() throws FileNotFoundException {
        super("Trivia Game");
        loadImages();
        setUpGui();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        jsonReader1 = new JsonReader(JSON_STORE1);
        System.out.println("New Profile or Load Profile N/L");
        String ans = scan.nextLine();
        if (ans.equals("L")) {
            loadTriviaGameHistory();
        }
        System.out.println("1 to start new game, 2 to view past games and 3 to quit.");
        startGame();
    }

    // EFFECTS: Sets up Lives Pic
    private void setLivesPic() {
        imageAsLabel1 = new JLabel(lives);
        imageAsLabel1.setBounds(10,600,150,150);
        imageAsLabel2 = new JLabel(lives);
        imageAsLabel2.setBounds(110,600,150,150);
        imageAsLabel3 = new JLabel(lives);
        imageAsLabel3.setBounds(210,600,150,150);
        imageAsLabel4 = new JLabel(lives);
        imageAsLabel4.setBounds(1030,600,150,150);
        imageAsLabel5 = new JLabel(lives);
        imageAsLabel5.setBounds(930,600,150,150);
        imageAsLabel6 = new JLabel(lives);
        imageAsLabel6.setBounds(830,600,150,150);
        add(imageAsLabel1);
        add(imageAsLabel2);
        add(imageAsLabel3);
        add(imageAsLabel4);
        add(imageAsLabel5);
        add(imageAsLabel6);
    }

    // EFFECTS: Sets up Robot Pic
    private void setRobotPic() {
        imageAsLabel7 = new JLabel(robot);
        imageAsLabel7.setBounds(615,550,200,200);
        add(imageAsLabel7);
    }

    // EFFECTS: Sets up StickMan Pic
    private void setStickmanPic() {
        imageAsLabel8 = new JLabel(stickman);
        imageAsLabel8.setBounds(375,550,200,200);
        add(imageAsLabel8);
    }

    // EFFECTS: load images
    private void loadImages() {
        String sep = System.getProperty("file.separator");
        lives = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "lives.png");
        robot = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "robot.png");
        stickman = new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "stickman.png");
    }

    // EFFECTS: Set up GUI Components
    private void setUpGui() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200,850));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        getContentPane().setBackground(new Color(238,250,253));
        setLayout(null);
        title();
        rules();
        newOrLoad();
        answerLabelA.setBackground(new Color(238,250,253));
        answerLabelB.setBackground(new Color(238,250,253));
        answerLabelC.setBackground(new Color(238,250,253));
        answerLabelD.setBackground(new Color(238,250,253));
        answerLabelE.setBackground(new Color(238,250,253));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        pack();
        button4.setFont(new Font("Arial",Font.BOLD,50));
        button4.setFocusable(false);
        button4.addActionListener(this);
        button5.setFont(new Font("Arial",Font.BOLD,50));
        button5.setFocusable(false);
        button5.addActionListener(this);
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

    // buttons to save game
    public void askSaveGame() {
        getContentPane().removeAll();
        button1.setBounds(450,350,300,200);
        button1.setFont(new Font("Arial",Font.BOLD,50));
        button1.setActionCommand("saveY");
        button1.setText("Yes");
        button2.setBounds(450,550,300,200);
        button2.setFont(new Font("Arial",Font.BOLD,50));
        button2.setActionCommand("saveN");
        button2.setText("No");
        textArea.setBounds(10,175,1100,100);
        textArea.setText("Would you like to save your game history?");
        add(textArea);
        add(button1);
        add(button2);
        revalidate();
        repaint();
        System.out.println("Would you like to save game? Y/N");
//        String ans = scan.nextLine();
//        if (ans.equals("Y")) {
//            saveTriviaGameHistory();
//        }
    }


    // EFFECTS: Prints to console all games played with accuracy for each game and personal stats based on all games
    public void viewPastGames() {
        JFrame jf = new JFrame();
        setJF(jf);
        setButtonPast();
        List<TriviaGame> temp = tgh.getListOfGames();
        String [][] data = new String[temp.size()][4];
        if (temp.size() == 0 || temp == null) {
            textField.setText("No Games Played");
        } else {
            for (int i = 0; i < data.length; i++) {
                data[i][0] = temp.get(i).getName();
                data[i][1] = Integer.toString(temp.get(i).getAccuracy());
                data[i][2] = "";
                data[i][3] = "";
            }
            data[temp.size() - 1][2] = Integer.toString(tgh.totalGamesPlayed());
            data[temp.size() - 1][3] = Integer.toString(tgh.averageAccuracy());
            jt = new JTable(data,header);
            jt.setBounds(0,0, 300, 200);
            jt.setAutoCreateRowSorter(true);
            JScrollPane sp = new JScrollPane(jt);
            jf.add(sp);
//            for (TriviaGame t : temp) {
//                model.addRow(new Object[] {t.getName(), t.getAccuracy()});
//                System.out.println(t.getName() + ", Accuracy: " + t.getAccuracy() + "%,");
//            }
//            model.addRow(new Object[] {});
//            model.addRow(new Object[] {tgh.totalGamesPlayed(),tgh.averageAccuracy()});
//            System.out.println("\nPersonal Stats:\n"
//                    + "Total Games Played: "
//                    + tgh.totalGamesPlayed() + ", Total Accuracy: " + tgh.averageAccuracy() + "%");
//            add(model);
        }
//        System.out.println("1 to start new game, 2 to view past games, 3 to quit");
    }

    // sets button for filter wins
    private void setButtonPast() {
        button4.setText("Show Wins");
        button4.setBounds(450,100,300,60);
        button4.setActionCommand("filter");
        add(button4);
    }

    // EFFECTS: sets jframe
    private void setJF(JFrame jf) {
        jf.setTitle("Past Games");
        jf.setSize(600,300);
        jf.setVisible(true);
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

        button3.setFocusable(false);
        button3.addActionListener(this);
    }

    // EFFECTS: Gets Next question for gui
    public void nextQuestion() {
        textField.setBounds(0,0,1200,100);
        textField.setFont(new Font("Arial",Font.BOLD,50));
        String question = player.getQuestion();
        String correct = player.getAnswer(question);
        textField.setText("Question " + counter);
        counter++;
        textArea.setBounds(10,100,1200,50);
        textArea.setFont(new Font("Arial",Font.BOLD,20));
        textArea.setText(question.substring(0,(question.indexOf("?") + 1)));
        add(textArea);
        setButton(correct);
        setAnswer(question);
    }

    // EFFECTS: sets button
    public void setButton(String correct) {
        setButtonAns(correct);
        button1.setBounds(0,150,75,75);
        button1.setFont(new Font("Arial",Font.BOLD,50));
        button1.setText("A");
        button2.setBounds(0,225,75,75);
        button2.setFont(new Font("Arial",Font.BOLD,50));
        button2.setText("B");
        button3.setBounds(0,300,75,75);
        button3.setText("C");
        button4.setBounds(0,375,75,75);
        button4.setText("D");
        button5.setBounds(0,450,75,75);
        button5.setText("E");
        add(button4);
        add(button5);
    }

    // EFFECTS: Sets button answers
    private void setButtonAns(String correct) {
        button1.setActionCommand("wrong");
        button2.setActionCommand("wrong");
        button3.setActionCommand("wrong");
        button4.setActionCommand("wrong");
        button5.setActionCommand("wrong");
        temp = computerChoice(correct);
        if (correct.equals("A")) {
            setButA(temp);
        } else if (correct.equals("B")) {
            setButB(temp);
        } else if (correct.equals("C")) {
            setButC(temp);
        } else if (correct.equals("D")) {
            setButD(temp);
        } else if (correct.equals("E")) {
            setButE(temp);
        }

    }

    // EFFECTS: Sets Button a
    private void setButA(String temp) {
        if (temp.equals("A")) {
            button1.setActionCommand("tie");
        } else {
            button1.setActionCommand("right");
            corr++;
        }
        total++;
    }

    // EFFECTS: Sets Button b
    private void setButB(String temp) {
        if (temp.equals("B")) {
            button2.setActionCommand("tie");
        } else {
            button2.setActionCommand("right");
            corr++;
        }
        total++;
    }

    // EFFECTS: Sets Button c
    private void setButC(String temp) {
        if (temp.equals("C")) {
            button3.setActionCommand("tie");
        } else {
            button3.setActionCommand("right");
            corr++;
        }
        total++;
    }

    // EFFECTS: Sets Button d
    private void setButD(String temp) {
        if (temp.equals("D")) {
            button4.setActionCommand("tie");
        } else {
            button4.setActionCommand("right");
            corr++;
        }
        total++;
    }

    // EFFECTS: Sets Button e
    private void setButE(String temp) {
        if (temp.equals("E")) {
            button5.setActionCommand("tie");
        } else {
            button5.setActionCommand("right");
            corr++;
        }
        total++;
    }


    // EFFECTS: set answer
    public void setAnswer(String question) {
        answerLabelA.setBounds(110,150,1000,75);
        answerLabelA.setFont(new Font("Arial",Font.PLAIN,20));
        answerLabelA.setText(question.substring(question.indexOf("A)") + 2,question.indexOf("B)")));

        answerLabelB.setBounds(110,225,1000,75);
        answerLabelB.setFont(new Font("Arial",Font.PLAIN,20));
        answerLabelB.setText(question.substring(question.indexOf("B)") + 2,question.indexOf("C)")));

        answerLabelC.setBounds(110,300,1000,75);
        answerLabelC.setFont(new Font("Arial",Font.PLAIN,20));
        answerLabelC.setText(question.substring(question.indexOf("C)") + 2,question.indexOf("D)")));

        answerLabelD.setBounds(110,375,1000,75);
        answerLabelD.setFont(new Font("Arial",Font.PLAIN,20));
        answerLabelD.setText(question.substring(question.indexOf("D)") + 2,question.indexOf("E)")));

        answerLabelE.setBounds(110,450,1000,75);
        answerLabelE.setFont(new Font("Arial",Font.PLAIN,20));
        answerLabelE.setText(question.substring(question.indexOf("E)") + 2));

        add(answerLabelA);
        add(answerLabelB);
        add(answerLabelC);
        add(answerLabelD);
        add(answerLabelE);
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
        button3.setActionCommand("quit");
        button3.setText("Quit");
        add(button3);
    }

    // EFFECTS: sets lives
    public void setLives() {
        plives = 3;
        clives = 3;
        total = 0;
        corr = 0;
        counter = 1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("load")) {
            loadG();
        } else if (e.getActionCommand().equals("new")) {
            newG();
        } else if (e.getActionCommand().equals("new game")) {
            newGame();
        } else if (e.getActionCommand().equals("past game")) {
            viewPastGames();
        } else if (e.getActionCommand().equals("quit")) {
            askSaveGame();
        } else if (e.getActionCommand().equals("saveY")) {
            saveGame();
        } else if (e.getActionCommand().equals("saveN")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("right")) {
            right();
        } else if (e.getActionCommand().equals("wrong")) {
            wrong();
        } else if (e.getActionCommand().equals("tie")) {
            nextQuestion();
        } else if (e.getActionCommand().equals("filter")) {
            highlight();
        }
    }

    private void highlight() {
        for (int i = 0; i < tgh.totalGamesPlayed(); i++) {
            if (tgh.getListOfGames().get(i).getWin()) {
                jt.addRowSelectionInterval(i,i);
            }
        }
    }

    // EFFECTS: Sets wrong button
    private void wrong() {
        plives--;
        if (plives == 2) {
            getContentPane().remove(imageAsLabel3);
            reset();
        } else if (plives == 1) {
            getContentPane().remove(imageAsLabel2);
            reset();
        }
        if (plives == 0) {
            player.setAccuracy(corr, total);
            player.setWin(false);
            tgh.addGame(player);
            getContentPane().removeAll();
            textField.setText("YOU LOSE D:");
            add(button1);
            add(button2);
            add(textField);
            reset();
            newG();
        } else {
            nextQuestion();
        }

    }

    // EFFECTS: Sets right button
    private void right() {
        clives--;
        if (clives == 2) {
            getContentPane().remove(imageAsLabel6);
            reset();
        } else if (clives == 1) {
            getContentPane().remove(imageAsLabel5);
            reset();
        }
        if (clives == 0) {
            player.setAccuracy(corr, total);
            player.setWin(true);
            tgh.addGame(player);
            getContentPane().removeAll();
            textField.setText("YOU WIN :D");
            add(button1);
            add(button2);
            add(textField);
            reset();
            newG();
        } else {
            nextQuestion();
        }

    }

    // EFFECTS: Saves game
    private void saveGame() {
        saveTriviaGameHistory();
        System.exit(0);
    }

    // EFFECTS: Resets
    public void reset() {
        revalidate();
        repaint();
    }

    // EFFECTS: New Game
    private void newGame() {
        player = new TriviaGame();
        player.setQuestions(loadQuestions());
        setLives();
        setLivesPic();
        setRobotPic();
        setStickmanPic();
        nextQuestion();
    }

    // EFFECTS: new profile
    private void newG() {
        getContentPane().remove(textArea);
        nextStep();
    }

    // EFFECTS: load profile
    private void loadG() {
        getContentPane().remove(textArea);
        loadTriviaGameHistory();
        nextStep();
    }
}
