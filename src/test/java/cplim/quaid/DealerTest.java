package cplim.quaid;

import com.google.common.collect.Lists;
import cplim.Card;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class DealerTest {

    private Dealer dealer;

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTotalCardsAreUneven() {
        dealer = new Dealer(Arrays.asList(new Card(0, 1)));
    }

    @Test
    public void shouldRemoveCardWhenCardIsPicked() {
        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards);

        final Card picked = dealer.pick(null);

        assertThat(dealer.holds(picked), is(false));
    }

    @Test
    public void shouldReturnCardWithSameValueIfAvailable() {
        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards);

        final Card x1 = new Card(2,2);
        x1.setValue("x");
        final Card x2 = new Card(3,3);
        x2.setValue("x");

        dealer.collect(x1);

        final Card picked = dealer.pick(x2);
        assertThat(picked, sameInstance(x1));
    }

    @Test
    public void dealerShouldOnlyKnowAboutTheirCards() {
        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards);
        final Card newCard = new Card(2,2);

        assertThat(dealer.holds(card1), is(true));
        assertThat(dealer.holds(card2), is(true));
        assertThat(dealer.holds(newCard), is(false));

        dealer.collect(newCard);
        assertThat(dealer.holds(newCard), is(true));

    }

    @Test
    public void shouldAddToRevealedCardsWhenCollectingCardWithKnownValue() {
        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards);

        final Card newCard = new Card(2,2);
        newCard.setValue("x");

        dealer.collect(newCard);

        assertThat(dealer.holds(newCard), is(true));
        assertThat(dealer.revealedCards, contains(newCard));
        assertThat(dealer.unRevealedCards, not(contains(newCard)));
    }

}
