package persistence;

import model.TriviaGame;
import model.TriviaGameHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            TriviaGameHistory tgh = new TriviaGameHistory();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTriviaGameHistory() {
        try {
            TriviaGameHistory tgh  = new TriviaGameHistory();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTriviaGameHistory.json");
            writer.open();
            writer.write(tgh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTriviaGameHistory.json");
            tgh = reader.read();
            assertEquals(0, tgh.totalGamesPlayed());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTriviaGameHistory() {
        try {
            TriviaGameHistory tgh = new TriviaGameHistory();
            TriviaGame tg = new TriviaGame();
            tg.setAccuracy(100);
            tgh.addGame(new TriviaGame());
            tgh.addGame(tg);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTriviaGameHistory.json");
            writer.open();
            writer.write(tgh);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTriviaGameHistory.json");
            tgh = reader.read();
            List<TriviaGame> t = tgh.getListOfGames();
            assertEquals(2, t.size());
            assertEquals(0, t.get(0).getAccuracy());
            assertEquals(100, t.get(1).getAccuracy());


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}