package cplim.quaid.memory;

public class Outcome {
    private int guesses;
    private float firstPickBias;
    private float secondPickBias;

    public Outcome(int guesses, float firstPickBias, float secondPickBias) {
        this.guesses = guesses;
        this.firstPickBias = firstPickBias;
        this.secondPickBias = secondPickBias;
    }

    public int getGuesses() {
        return guesses;
    }

    public void setGuesses(int guesses) {
        this.guesses = guesses;
    }

    public float getFirstPickBias() {
        return firstPickBias;
    }

    public void setFirstPickBias(float firstPickBias) {
        this.firstPickBias = firstPickBias;
    }

    public float getSecondPickBias() {
        return secondPickBias;
    }

    public void setSecondPickBias(float secondPickBias) {
        this.secondPickBias = secondPickBias;
    }
}
