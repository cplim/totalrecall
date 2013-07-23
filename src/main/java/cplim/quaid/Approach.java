package cplim.quaid;

public class Approach {
    private final Behaviour firstPick;
    private final Behaviour secondPick;
    private final float firstPickBias;
    private final float secondPickBias;

    public Approach(float firstPickBias, float secondPickBias) {
        this.firstPickBias = firstPickBias;
        this.secondPickBias = secondPickBias;
        firstPick = new Behaviour(firstPickBias);
        secondPick = new Behaviour(secondPickBias);
    }

    public CardPreference firstPickPreference() {
        return firstPick.preference();
    }

    public CardPreference secondPickPreference() {
        return secondPick.preference();
    }

    public float getFirstPickBias() {
        return firstPickBias;
    }

    public float getSecondPickBias() {
        return secondPickBias;
    }

}
