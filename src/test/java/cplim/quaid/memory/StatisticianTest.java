package cplim.quaid.memory;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public class StatisticianTest {
    private File file;
    private Statistician statistician;

    @Before
    public void setup() throws IOException {
        file = File.createTempFile("data", "txt");
        file.deleteOnExit();

        statistician = new Statistician(file);
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowExceptionOnNonExistantFile() throws IOException {
        statistician = new Statistician(new File("doesnotexist.txt"));
    }

    @Test
    public void shouldHaveAnEmptyHistoryWhenFileIsEmpty() throws IOException {
        statistician = new Statistician(file);
    }

    @Test
    public void shouldStoreOutcome() throws IOException {
        final Outcome outcome = new Outcome(99999, 0.1f, 0.3f);
        statistician.record(outcome);

        String contents = FileUtils.readFileToString(file);
        assertThat(contents, containsString("99999"));
    }

    @Test
    public void shouldStoreMultipleOutcomes() throws IOException {
        final Outcome outcome = new Outcome(99999, 0.1f, 0.3f);
        final Outcome outcome2 = new Outcome(11, 0.9f, 0.6f);
        statistician.record(outcome);
        statistician.record(outcome2);

        String contents = FileUtils.readFileToString(file);
        assertThat(contents, containsString("99999"));
        assertThat(contents, containsString("11"));
    }
}
