package persistence;

import model.TriviaGame;
import model.TriviaGameHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TriviaGameHistory tgh = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTriviaGameHistory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTriviaGameHistory.json");
        try {
            TriviaGameHistory tgh = reader.read();
            assertEquals(0, tgh.totalGamesPlayed());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTriviaGameHistory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTriviaGameHistory.json");
        try {
            TriviaGameHistory tgh = reader.read();
            List<TriviaGame> lot = tgh.getListOfGames();
            assertEquals(2, lot.size());
            assertEquals("Game 1", lot.get(0).getName());
            assertEquals("Game 2", lot.get(1).getName());
            assertEquals(0, lot.get(0).getAccuracy());
            assertEquals(100, lot.get(1).getAccuracy());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
