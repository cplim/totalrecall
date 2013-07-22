package cplim.quaid;

import com.google.common.base.Predicate;
import cplim.Card;

public class CorrespondingCard implements Predicate<Card> {
    private final Card reference;

    public CorrespondingCard(Card reference) {
        this.reference = reference;
    }

    @Override
    public boolean apply(Card card) {
        if(reference == null || reference.getValue() == null) {
            return false;
        }

        return reference.getValue().equals(card.getValue());
    }
}
