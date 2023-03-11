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
        assertEquals(0, tg.getAccuracy());
        assertEquals(new HashMap<String, String>(), tg.getQuestionBank());
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
    public void testGetQuestions() {
        HashMap<String,String> temp = new HashMap<>();
        temp.put("a","1");
        temp.put("b","2");
        tg.setQuestions(temp);
        assertEquals(temp,tg.getQuestionBank());
        assertTrue(tg.getQuestionBank().containsKey("a"));
    }

    @Test
    public void testGetAnswer() {
        HashMap<String,String> temp = new HashMap<>();
        temp.put("a","1");
        temp.put("b","2");
        tg.setQuestions(temp);
        assertEquals("1",tg.getAnswer("a"));
    }

    @Test
    public void testGetQuestion() {
        HashMap<String,String> temp = new HashMap<>();
        temp.put("a","1");
        tg.setQuestions(temp);
        assertEquals("a" ,tg.getQuestion());
    }

    @Test
    public void testGetQuestionMultiple() {
        HashMap<String,String> temp = new HashMap<>();
        temp.put("a","1");
        temp.put("b","1");
        tg.setQuestions(temp);
        assertTrue(tg.getQuestion() != null);
    }

    @Test
    public void testGetQuestionMultiple2() {
        HashMap<String,String> temp = new HashMap<>();
        temp.put("a","1");
        temp.put("b","1");
        tg.setQuestions(temp);
        String str = tg.getQuestion();
        while(!str.equals("a")) {
            str = tg.getQuestion();
        }
        assertEquals("a",str);
    }
}