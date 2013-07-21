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

        while(unKnownCards.size() != 2) {

            // make a guess
            final Card[] pair = pickPair();
            for(Card card: pair) {
                game.guess(card);
            }

            // add the cards back to the known list
            updateKnownCards(pair);
        }

        return game.end(unKnownCards.get(0), unKnownCards.get(1));
    }

    private void updateKnownCards(Card[] cards) {
        knownCards.addAll(Arrays.asList(cards));
        Collections.sort(knownCards);
    }

    private Card[] pickPair() {
        Card[] pair = new Card[2];

        pair[0] = pickFirst();
        pair[1] = pickSecond(pair[0]);

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

    private Card pickSecond(Card firstPick) {
        List<Card> list = new ArrayList<Card>();

        // preference known first
        if(!knownCards.isEmpty()) {
            list.addAll(knownCards);
        } else {
            list.addAll(unKnownCards);
        }

        list.remove(firstPick); // can't pick card at the same position
        return list.remove(0); // FIXME: Remove from the known/unknown, not the temp list.
    }
}
