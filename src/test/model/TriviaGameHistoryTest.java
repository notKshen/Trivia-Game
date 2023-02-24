package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TriviaGameHistoryTest {
    private TriviaGameHistory tgh;
    private TriviaGame tg1;
    private TriviaGame tg2;

    @BeforeEach
    public void setUp() {
        tgh = new TriviaGameHistory();
        tg1 = new TriviaGame();
        tg2 = new TriviaGame();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, tgh.getListOfGames().size());
    }

    @Test
    public void testAddGame() {
        tgh.addGame(tg1);
        assertEquals(1, tgh.totalGamesPlayed());
    }

    @Test
    public void testAddGameMultiple() {
        List<TriviaGame> temp = tgh.getListOfGames();
        tgh.addGame(tg1);
        assertEquals(1, temp.size());
        assertEquals(tg1, temp.get(0));
        tgh.addGame(tg2);
        assertEquals(2, temp.size());
        assertEquals(tg2, temp.get(1));
    }

    @Test
    public void testAverageAccuracyZero() {
        tgh.addGame(tg1);
        tgh.addGame(tg2);
        assertEquals(0, tgh.averageAccuracy());
    }

    @Test
    public void testAverageAccuracy() {
        tg1.setAccuracy(10,10);
        tg2.setAccuracy(1, 10);
        tgh.addGame(tg1);
        tgh.addGame(tg2);
        assertEquals(55, tgh.averageAccuracy());
    }

}
