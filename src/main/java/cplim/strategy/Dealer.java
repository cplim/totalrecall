package cplim.strategy;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import cplim.Card;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    final List<Card> unRevealedCards;
    final List<Card> revealedCards = new ArrayList<Card>();

    public Dealer(List<Card> newCards) {
        this.unRevealedCards = newCards;
        if(newCards.size() % 2 != 0) {
            throw new IllegalArgumentException("Uneven number of cards. The cards will never match!");
        }
    }

    public Card pick(Card reference) {
        if(!revealedCards.isEmpty()) {
            // by default, if the revealed cards contains a card with the same value, return it (the point of the game!)
            CorrespondingCard matchingFirstCard = new CorrespondingCard(reference);
            final Optional<Card> match = Iterables.tryFind(revealedCards, matchingFirstCard);
            final Card revealedCard = match.or(revealedCards.get(0)); // if there are no matches, just get a revealed card
            revealedCards.remove(revealedCard);

            return revealedCard;
        }

        return unRevealedCards.remove(0);
    }

    public void collect(Card card) {
        revealedCards.add(card);
    }

    public boolean holds(Card card) {
        return unRevealedCards.contains(card) || revealedCards.contains(card);
    }
}
