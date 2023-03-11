package persistence;

import model.TriviaGame;
import model.TriviaGameHistory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTestQuestions {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            HashMap<String,String> hash = reader.ready();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTriviaGameHistory() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyQuestion.json");
        try {
            HashMap<String,String> hash = reader.ready();
            assertEquals(0, hash.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTriviaGameHistory() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralQuestion.json");
        try {
            HashMap<String,String> hash = reader.ready();
            assertEquals(2, hash.size());
            assertTrue(hash.containsValue("E"));
            assertFalse(hash.containsValue("Z"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
