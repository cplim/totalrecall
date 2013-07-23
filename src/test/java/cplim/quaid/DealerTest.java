package cplim.quaid;

import com.google.common.collect.Lists;
import cplim.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DealerTest {

    @Mock
    private Approach approach;
    private Dealer dealer;

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTotalCardsAreUneven() {
        dealer = new Dealer(Arrays.asList(new Card(0, 1)), null);
    }

    @Test
    public void shouldUseFirstPickPreference() {
        when(approach.firstPickPreference()).thenReturn(CardPreference.UNREVEALED);

        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards, approach);

        dealer.pick(null);

        verify(approach, times(1)).firstPickPreference();
        verify(approach, times(0)).secondPickPreference();
    }

    @Test
    public void shouldUseSecondPickPreference() {
        when(approach.secondPickPreference()).thenReturn(CardPreference.UNREVEALED);

        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards, approach);

        dealer.pick(new Card(2,2));

        verify(approach, times(0)).firstPickPreference();
        verify(approach, times(1)).secondPickPreference();
    }

    @Test
    public void shouldReturnUnRevealedCardIfAvailable() {
        when(approach.firstPickPreference()).thenReturn(CardPreference.UNREVEALED);

        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards, approach);

        assertThat(dealer.unRevealedCards.size(), is(2));
        dealer.pick(null);
        assertThat(dealer.unRevealedCards.size(), is(1));
    }

    @Test
    public void shouldReturnRevealedCardIfNoUnRevealedCardsAreAvailable() {
        when(approach.firstPickPreference()).thenReturn(CardPreference.UNREVEALED);

        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards, approach);

        // pick and then return all cards from/to dealer
        assertThat(dealer.unRevealedCards.size(), is(2));
        final Card firstCard = dealer.pick(null);
        firstCard.setValue("x");
        final Card secondCard = dealer.pick(null);
        secondCard.setValue("y");
        dealer.collect(firstCard);
        dealer.collect(secondCard);
        assertThat(dealer.unRevealedCards.size(), is(0));

        // pick from revealed
        assertThat(dealer.revealedCards.size(), is(2));
        dealer.pick(null);
        assertThat(dealer.revealedCards.size(), is(1));
    }

    @Test
    public void shouldReturnRevealedCardIfAvailable() {
        when(approach.firstPickPreference()).thenReturn(CardPreference.REVEALED);

        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards, approach);

        // pick and then return all cards from/to dealer
        assertThat(dealer.unRevealedCards.size(), is(2));
        final Card firstCard = dealer.pick(null);
        firstCard.setValue("x");
        final Card secondCard = dealer.pick(null);
        secondCard.setValue("y");
        dealer.collect(firstCard);
        dealer.collect(secondCard);
        assertThat(dealer.unRevealedCards.size(), is(0));

        // pick from revealed
        assertThat(dealer.revealedCards.size(), is(2));
        dealer.pick(null);
        assertThat(dealer.revealedCards.size(), is(1));
    }

    @Test
    public void shouldReturnUnRevealedCardIfNoRevealedCardsAreAvailable() {
        when(approach.firstPickPreference()).thenReturn(CardPreference.REVEALED);

        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards, approach);

        assertThat(dealer.unRevealedCards.size(), is(2));
        dealer.pick(null);
        assertThat(dealer.unRevealedCards.size(), is(1));
    }

    @Test
    public void shouldRemoveCardWhenCardIsPicked() {
        when(approach.firstPickPreference()).thenReturn(CardPreference.REVEALED);

        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards, approach);

        final Card picked = dealer.pick(null);

        assertThat(dealer.holds(picked), is(false));
    }

    @Test
    public void shouldReturnCardWithSameValueIfAvailable() {
        when(approach.secondPickPreference()).thenReturn(CardPreference.REVEALED);

        final Card card1 = new Card(0,0);
        final Card card2 = new Card(0,1);
        final List<Card> cards = Lists.newArrayList(card1, card2);
        dealer = new Dealer(cards, approach);

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
        dealer = new Dealer(cards, null);
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
        dealer = new Dealer(cards, null);

        final Card newCard = new Card(2,2);
        newCard.setValue("x");

        dealer.collect(newCard);

        assertThat(dealer.holds(newCard), is(true));
        assertThat(dealer.revealedCards, contains(newCard));
        assertThat(dealer.unRevealedCards, not(contains(newCard)));
    }

}
