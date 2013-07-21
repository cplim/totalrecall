package cplim.strategy;

import com.google.common.base.Predicate;
import cplim.Card;

public class CorrespondingCard implements Predicate<Card> {
    private final Card reference;

    public CorrespondingCard(Card reference) {
        this.reference = reference;
        if(reference.getValue() == null) {
            throw new IllegalArgumentException("Reference card("+reference.getX()+","+reference.getY()+") cannot have a null value");
        }
    }

    @Override
    public boolean apply(Card card) {
        return reference.getValue().equals(card.getValue());
    }
}
