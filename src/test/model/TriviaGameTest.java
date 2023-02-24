package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TriviaGameTest {
    private TriviaGame tg;

    @BeforeEach
    public void setUp() {
        tg = new TriviaGame();
    }

    @Test
    public void testConstructor() {
        assertEquals("Game 6", tg.getName());
        assertEquals(0, tg.getAccuracy());
        assertEquals(new HashMap<String, String>(), tg.getQuestions());
    }

    @Test
    public void testSetAccuracyZero() {
        int curr = 0;
        int total = 5;
        tg.setAccuracy(curr, total);
        assertEquals(0, tg.getAccuracy());
    }

    @Test
    public void testSetAccuracyNumber() {
        int curr = 1;
        int total = 5;
        tg.setAccuracy(curr, total);
        assertEquals(20, tg.getAccuracy());
    }

    @Test
    public void testGetQuestionEmpty() {
        assertEquals("", tg.getQuestion());
    }

    @Test
    public void testGetQuestion() {
        tg.setEoscQuestions();
        assertFalse(tg.getQuestion().equals(""));
        assertEquals(11, tg.getQuestions().size());
    }

    @Test
    public void testGetAnswer() {
        String temp = "In a lightning strikes, what does the stepped leader do?\n"
                + "A) It steps up the voltage like a transformer\n"
                + "B) It produces a flickering that you can see by eye\n"
                + "C) It is a luminescent sphere of plasma that expands step by step\n"
                + "D) It is part of the lightning that makes the bright flash and thunder\n"
                + "E) It makes the first electrical connection between the cloud and ground";
        tg.setEoscQuestions();
        assertEquals("E", tg.getAnswer(temp));
    }
}