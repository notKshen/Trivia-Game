package model;

import java.util.*;

// Represents an account having an id, owner name and balance (in dollars)
public class TriviaGame {
    private static int counter = 0;
    private int counterDisplay;
    private int accuracy;
    private HashMap<String, String> questionBank;

    public TriviaGame() {
        questionBank = new HashMap<>();
        counter++;
        counterDisplay = counter;
        accuracy = 0;
    }

    public HashMap<String, String> getQuestions() {
        return questionBank;
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

    // REQUIRES: Input be a question from questionBank
    // EFFECTS: Gets Answer from question bank
    public String getAnswer(String str) {
        return questionBank.get(str);
    }

    // EFFECTS: Sets the question bank with EOSC questions
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void setEoscQuestions() {
        questionBank.put("In a lightning strikes, what does the stepped leader do?\n"
                + "A) It steps up the voltage like a transformer\n"
                + "B) It produces a flickering that you can see by eye\n"
                + "C) It is a luminescent sphere of plasma that expands step by step\n"
                + "D) It is part of the lightning that makes the bright flash and thunder\n"
                + "E) It makes the first electrical connection between the cloud and ground", "E");
        questionBank.put("The location of greatest lightning frequency in the world is?\n"
                + "A) Alberta Canada\n"
                + "B) Oklahoma\n"
                + "C) Florida\n"
                + "D) Central Africa\n"
                + "E) Southern China", "D");
        questionBank.put("In a lightning storm, which is normally the safest option?\n"
                + "A) Hide under a metal vehicle\n"
                + "B) Walk in the middle of a large open area\n"
                + "C) Hide under a tree\n"
                + "D) Hide inside a metal vehicle\n"
                + "E) Hold up your umbrella to act as a lightning rod", "D");
        questionBank.put("Which Statement is true?\n"
                + "A) Multicell storms don't usually have lightning\n"
                + "B) A multicell thunderstorm can contain 1 or more cells at the same time\n"
                + "C) In a multicell storm, the life-cycle of each cell is synchronized\n"
                + "D) The cumulus stage of thunderstorm is the stage likely to have overshooting top or dome\n"
                + "E) In multicell storms violent weather for you while your friend across town has mild conditions",
                "E");
        questionBank.put("Most of our storms happen in the?\n"
                + "A) Mesosphere\n"
                + "B) Stratopause\n"
                + "C) Stratosphere\n"
                + "D) Tropopause\n"
                + "E) Troposphere", "E");
        questionBank.put("Which statement is true?\n"
                + "A) A dBZ is a disaster scale indicating the speed of damaging winds in thunderstorms\n"
                + "B) Weather satellites give forecasts of future thunderstorm locations\n"
                + "C) Weather radar can see the cumulus stage of thunderstorm cells\n"
                + "D) Visible images from satellites see inside the cloud to locate most violent part of thunderstorm\n"
                + "E) Weather radar and satellite are remote sensors", "E");
        questionBank.put("Which statement is true?\n"
                + "A) A classic supercell is another name for a squall-line storm\n"
                + "B) In a classic supercell, a curtain of rain usually obscures tornadoes\n"
                + "C) High-precipitation supercells often produce lots of hail\n"
                + "D) You can usually see mesocyclone rotation by eye\n"
                + "E) The most violent tornadoes come from supercell thunderstorms", "E");
        questionBank.put("Precipitation that completely evaporates before hitting the ground is called\n"
                + "A) a dud\n"
                + "B) virga\n"
                + "C) dry precipitation\n"
                + "D) a haboob\n"
                + "E) a sand storm", "B");
        questionBank.put("Which statement is true?\n"
                + "A) Mixing ratio is ratio of water vapour in air to max amount of water vapour that could be held\n"
                + "B) Colder air can hold less water vapour at saturation than warmer air\n"
                + "C) If air is saturated then 100% of the air molecules are water vapour\n"
                + "D) Unsaturated air contains more water vapour than the air could hold, at any given temp\n"
                + "E) The main component of air is water vapour", "B");
        questionBank.put("As a tornado approaches you what happens?\n"
                + "A) tornado, downpour, rainbow, lightning, anvil\n"
                + "B) anvil, gust front, lightning, downpour, tornado\n"
                + "C) downpour, gust front, anvil, tornado, lightning\n"
                + "D) supercell, tornado, mesocyclone, wall cloud, lightning\n"
                + "E) None of the above", "B");
        questionBank.put("Tornado outbreaks:\n"
                + "A) are rare\n"
                + "B) happen almost every day in North America\n"
                + "C) happen almost every year in North America\n"
                + "D) are usually caused by a single strong supercell storm\n"
                + "E) none of the above", "C");
    }
}

