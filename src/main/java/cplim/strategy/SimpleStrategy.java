package cplim.strategy;

import cplim.Card;
import cplim.Game;
import cplim.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleStrategy implements GameStrategy {
    private Game game;
    private Map<String, Card[]> pairs = new HashMap<String, Card[]>();
    private List<Card> allCards;

    public SimpleStrategy(Game game) {
        this.game = game;
        this.allCards = StrategyUtil.constructCards(game.getWidth(), game.getHeight());
    }

    public Result solve() {

        if(allCards.size() % 2 != 0 ) {
            throw new IllegalArgumentException("Uneven number of cards. The cards will never match!");
        }

        // find value for each card
        for(Card card: allCards) {
            game.guess(card);

            Card[] pair = pairs.get(card.getValue());
            if(pair == null) {
                pair = new Card[2];
                pairs.put(card.getValue(), pair);
                pair[0] = card;
            } else {
                pair[1] = card;
            }
        }

        // guess all known pairs bar one
        Map.Entry<String, Card[]> endPair = pairs.entrySet().iterator().next();
        pairs.remove(endPair.getKey());
        for(Card[] pair : pairs.values()) {
            game.guess(pair[0]);
            game.guess(pair[1]);
        }

        // end the game with the remaining pair
        return game.end(endPair.getValue()[0], endPair.getValue()[1]);
    }
}
