package cplim.quaid;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import cplim.Card;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    final List<Card> unRevealedCards = new ArrayList<Card>();
    final List<Card> revealedCards = new ArrayList<Card>();
    final Approach approach;

    public Dealer(List<Card> newCards) {
        this.unRevealedCards.addAll(newCards);
        if(newCards.size() % 2 != 0) {
            throw new IllegalArgumentException("Uneven number of cards. The cards will never match!");
        }
        approach = new Approach(0.25f, 0.75f);
    }

    public Card pick(Card reference) {
        final CardPreference preference = (reference == null ? approach.firstPickPreference() : approach.secondPickPreference());

        if(preference == CardPreference.REVEALED) {
            // prefer revealed card iff it isn't empty
            if(!revealedCards.isEmpty()) {
                return pickRevealedCard(reference);
            }
            return pickUnRevealedCard();
        }

        // prefer unrevealed card iff it isn't empty
        if(!unRevealedCards.isEmpty()) {
            return pickUnRevealedCard();
        }
        return pickRevealedCard(reference);
    }

    private Card pickUnRevealedCard() {
        return unRevealedCards.remove(0);
    }

    private Card pickRevealedCard(Card reference) {
        // by default, if the revealed cards contains a card with the same value, return it (the point of the game!)
        CorrespondingCard matchingFirstCard = new CorrespondingCard(reference);
        final Optional<Card> match = Iterables.tryFind(revealedCards, matchingFirstCard);
        final Card revealedCard = match.or(revealedCards.get(0)); // if there are no matches, just get a revealed card
        revealedCards.remove(revealedCard);

        return revealedCard;
    }

    public void collect(Card card) {
        revealedCards.add(card);
    }

    public boolean holds(Card card) {
        return unRevealedCards.contains(card) || revealedCards.contains(card);
    }

    public List<Card> remainingCards() {
        List<Card> remainder = new ArrayList<Card>();
        remainder.addAll(unRevealedCards);
        remainder.addAll(revealedCards);
        return remainder;
    }
}
