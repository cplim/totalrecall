package cplim.strategy;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import cplim.Card;
import cplim.Game;
import cplim.Result;

import java.util.ArrayList;
import java.util.List;

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

            // pick a card and turn it over
            final Card firstCard = firstPick();
            game.reveal(firstCard);
            final Card secondCard = secondPick(firstCard);
            game.reveal(secondCard);

            // only add the cards back into the known cards if they don't match
            if(firstCard.getValue().equals(secondCard.getValue())) {
                solved += 2;
            } else {
                turnOverCards(firstCard, secondCard);
            }
        }

        List<Card> remainder = new ArrayList<Card>(unKnownCards);
        remainder.addAll(knownCards);
        return game.end(remainder.get(0), remainder.get(1));
    }

    private void turnOverCards(Card firstCard, Card secondCard) {
        knownCards.add(firstCard);
        knownCards.add(secondCard);
    }

    private Card firstPick() {
        // preference unknown first
        if(!unKnownCards.isEmpty()) {
            return unKnownCards.remove(0);
        }

        // otherwise use the known cards
        return knownCards.remove(0);
    }

    /**
     * @param firstCard is a card with a known value!
     * @return secondCard to turn over
     */
    private Card secondPick(Card firstCard) {
        // preference known first
        if(!knownCards.isEmpty()) {
            // by default, if the known cards contains a card with the same value, return it (the point of the game!)
            CorrespondingCard matchingFirstCard = new CorrespondingCard(firstCard);
            final Optional<Card> match = Iterables.tryFind(knownCards, matchingFirstCard);
            final Card secondCard = match.or(knownCards.get(0)); // if there are no matches, just get a known card
            knownCards.remove(secondCard);

            return secondCard;
        }

        // otherwise use unknown
        return unKnownCards.remove(0);
    }
}
