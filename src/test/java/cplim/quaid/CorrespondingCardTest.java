package cplim.quaid;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import cplim.Card;
import cplim.quaid.CorrespondingCard;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.nullValue;

public class CorrespondingCardTest {

    private Card card1;
    private Card card2;
    private CorrespondingCard correspondingCard;

    @Before
    public void setup() {
        card1 = new Card(0,0);
        card1.setValue("x");
        card2 = new Card(0,1);
        card2.setValue("y");
    }

    @Test
    public void shouldNeverMatchWhenReferenceIsNull() {
        correspondingCard = new CorrespondingCard(null);

        List<Card> cards = Arrays.asList(card1, card2);
        final Optional<Card> optional = Iterables.tryFind(cards, correspondingCard);

        assertThat(optional.orNull(), is(nullValue()));
    }

    @Test
    public void shouldNeverMatchWhenReferenceValueIsNull() {
        final Card reference = new Card(9,9);
        correspondingCard = new CorrespondingCard(reference);

        List<Card> cards = Arrays.asList(card1, card2);
        final Optional<Card> optional = Iterables.tryFind(cards, correspondingCard);

        assertThat(optional.orNull(), is(nullValue()));
    }

    @Test
    public void shouldFindCardWhenReferenceValueExists() {
        final Card reference = new Card(9,9);
        reference.setValue("y");
        correspondingCard = new CorrespondingCard(reference);

        List<Card> cards = Arrays.asList(card1, card2);
        final Optional<Card> optional = Iterables.tryFind(cards, correspondingCard);

        assertThat(optional.orNull(), is(card2));
    }
}
