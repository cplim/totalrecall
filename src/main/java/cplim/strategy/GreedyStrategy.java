package cplim.strategy;

import cplim.Card;
import cplim.Game;
import cplim.Result;
import cplim.quaid.Dealer;

import java.util.List;

public class GreedyStrategy implements GameStrategy {
    private final Game game;
    private List<Card> unKnownCards;
    private Dealer dealer;

    public GreedyStrategy(Game game) {
        this.game = game;
        this.unKnownCards = StrategyUtil.constructCards(game.getWidth(), game.getHeight());
        dealer = new Dealer(unKnownCards);
    }

    public Result solve() {
        if(unKnownCards.size() % 2 != 0 ) {
            throw new IllegalArgumentException("Uneven number of cards. The cards will never match!");
        }

        while(dealer.remainingCards().size() != 2) {

            // pick a card and turn it over
            final Card firstCard = dealer.pick(null);
            game.reveal(firstCard);
            final Card secondCard = dealer.pick(firstCard);
            game.reveal(secondCard);

            // cards that do not match will be returned to dealer for matching in later hands.
            if(!firstCard.getValue().equals(secondCard.getValue())) {
                dealer.collect(firstCard);
                dealer.collect(secondCard);
            }
        }

        List<Card> remainder = dealer.remainingCards();
        return game.end(remainder.get(0), remainder.get(1));
    }
}
