package cplim.quaid.memory;

public class Outcome implements Comparable<Outcome> {
    private int guesses;
    private float firstPickBias;
    private float secondPickBias;

    public Outcome() {
        this(0, 0.0f, 0.0f);
    }

    public Outcome(int guesses, float firstPickBias, float secondPickBias) {
        this.guesses = guesses;
        this.firstPickBias = firstPickBias;
        this.secondPickBias = secondPickBias;
    }

    public int getGuesses() {
        return guesses;
    }

    public float getFirstPickBias() {
        return firstPickBias;
    }

    public float getSecondPickBias() {
        return secondPickBias;
    }

    public void setGuesses(int guesses) {
        this.guesses = guesses;
    }

    public void setFirstPickBias(float firstPickBias) {
        this.firstPickBias = firstPickBias;
    }

    public void setSecondPickBias(float secondPickBias) {
        this.secondPickBias = secondPickBias;
    }

    @Override
    public int compareTo(Outcome other) {
        return this.guesses - other.guesses;
    }
}
