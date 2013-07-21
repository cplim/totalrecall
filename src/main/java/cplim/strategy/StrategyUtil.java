package cplim.strategy;

import cplim.Card;

import java.util.ArrayList;
import java.util.List;

class StrategyUtil {
    public static List<Card> constructCards(int width, int height) {
        List<Card> cards = new ArrayList<Card>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cards.add(new Card(x, y));
            }
        }
        return cards;
    }
}