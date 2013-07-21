package cplim;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class CardTest {
    @Test
    public void shouldOrderByStringValue() {
        Card a = new Card(0, 0);
        a.setValue("a");
        Card j = new Card(1, 1);
        j.setValue("j");
        Card f = new Card(3, 7);
        f.setValue("f");
        Card o = new Card(-9, 98);
        o.setValue("o");

        List<Card> cards = new ArrayList<Card>();
        cards.add(f);
        cards.add(a);
        cards.add(j);
        cards.add(o);
        Collections.sort(cards);

        assertThat(cards.get(0), is(a));
        assertThat(cards.get(1), is(f));
        assertThat(cards.get(2), is(j));
        assertThat(cards.get(3), is(o));
    }

    @Test
    public void shouldKeepSameValueCardsTogether() {
        Card j1 = new Card(0, 0);
        j1.setValue("j");
        Card j2 = new Card(0, 0);
        j2.setValue("j");
        Card f = new Card(0, 0);
        f.setValue("f");

        List<Card> cards = new ArrayList<Card>();
        cards.add(j1);
        cards.add(f);
        cards.add(j2);
        Collections.sort(cards);

        assertThat(cards.get(0), is(f));
        List<Card> jCards = cards.subList(1, cards.size());
        assertThat(jCards.size(), is(2));
        assertThat(jCards, contains(j1, j2));
    }
}
