package cplim.strategy;

import cplim.Card;
import cplim.Game;
import cplim.Result;

import java.util.*;

public class GreedyStrategy implements GameStrategy {
    private final Game game;
    private List<Card> unKnownCards;
    private List<Card> knownCards = new ArrayList<Card>();

    public GreedyStrategy(Game game) {
        this.game = game;
        this.unKnownCards = StrategyUtil.constructCards(game.getWidth(), game.getHeight());
    }

    public Result solve() {
        if(unKnownCards.size() % 2 != 0 ) {
            throw new IllegalArgumentException("Uneven number of cards. The cards will never match!");
        }

        int limit = unKnownCards.size() - 2; // maximum number of cards to have picked
        int solved = 0; // keep count of number of cards matched
        while(solved < limit) {

            // make a guess
            final Card[] pair = pickPair();
            for(Card card: pair) {
                game.guess(card);
            }

            // only add the cards back into the known cards if they don't match
            if(pair[0].getValue().equals(pair[1].getValue())) {
                solved += 2;
            } else {
                updateKnownCards(pair);
            }
        }

        return game.end(knownCards.get(0), knownCards.get(1));
    }

    private void updateKnownCards(Card[] cards) {
        knownCards.addAll(Arrays.asList(cards));
        Collections.sort(knownCards);
    }

    private Card[] pickPair() {
        Card[] pair = new Card[2];

        pair[0] = pickFirst();
        pair[1] = pickSecond();

        return pair;
    }

    private Card pickFirst() {
        // preference unknown first
        if(!unKnownCards.isEmpty()) {
            return unKnownCards.remove(0);
        }

        // otherwise use the known cards
        return knownCards.remove(0);
    }

    private Card pickSecond() {
        // preference known first
        if(!knownCards.isEmpty()) {
            return knownCards.remove(0);
        }

        return unKnownCards.remove(0);
    }
}
