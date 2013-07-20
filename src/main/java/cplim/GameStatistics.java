package cplim;

public class GameStatistics {
    private int guesses = 0;

    public void incrementGuess() {
        guesses++;
    }

    public int numberOfGuesses() {
        return guesses;
    }
}
