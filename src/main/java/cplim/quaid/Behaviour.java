package cplim.quaid;

import java.util.Random;

public class Behaviour {

    private final Random random;
    private final float revealedBias;

    /**
     *
     * @param revealedBias number between 0.0 and 1.0 to bias towards revealed card. 1.0 -> towards revealed card,
     *                     0.0 -> towards unrevealed card.
     */
    public Behaviour(float revealedBias) {
        this(new Random(System.currentTimeMillis()), revealedBias);
    }

    Behaviour(Random random, float revealedBias) {
        this.random = random;
        this.revealedBias = revealedBias;
    }

    public CardPreference preference() {
        if(random.nextFloat() < revealedBias) {
            return CardPreference.REVEALED;
        }

        return CardPreference.UNREVEALED;
    }
}
