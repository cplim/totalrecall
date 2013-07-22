package cplim.quaid;

public class Approach {
    private final Behaviour firstPick;
    private final Behaviour secondPick;

    public Approach(float firstPickBias, float secondPickBias) {
        firstPick = new Behaviour(firstPickBias);
        secondPick = new Behaviour(secondPickBias);
    }

    public CardPreference firstPickPreference() {
        return firstPick.preference();
    }

    public CardPreference secondPickPreference() {
        return secondPick.preference();
    }
}
