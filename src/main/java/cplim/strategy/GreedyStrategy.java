package cplim.strategy;

import cplim.Card;
import cplim.Game;
import cplim.Result;
import cplim.quaid.Approach;
import cplim.quaid.Dealer;
import cplim.quaid.memory.Outcome;
import cplim.quaid.memory.Statistician;

import java.util.List;

public class GreedyStrategy implements GameStrategy {
    private final Game game;
    private final Statistician statistician;
    private final List<Card> unKnownCards;
    private final Dealer dealer;
    private final Approach approach;

    public GreedyStrategy(Game game, Statistician statistician) {
        this.game = game;
        this.statistician = statistician;
        this.unKnownCards = StrategyUtil.constructCards(game.getWidth(), game.getHeight());
        this.approach = statistician.recommendApproachFromStatistics();
        this.dealer = new Dealer(unKnownCards, approach);
    }

    public Result solve() throws Exception {
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

        // end the game
        List<Card> remainder = dealer.remainingCards();
        final Result result = game.end(remainder.get(0), remainder.get(1));

        // statistician will record statistic
        statistician.record(new Outcome(
            result.getStatistic().numberOfGuesses(),
            approach.getFirstPickBias(),
            approach.getSecondPickBias()
        ));
        return result;
    }
}
