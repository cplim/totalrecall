package cplim.quaid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BehaviourTest {
    @Mock
    private Random random;
    private Behaviour behaviour;

    @Test
    public void shouldReturnRevealedPreferenceWhenRandomIsLessThanBias() {
        float bias = 0.8f;
        behaviour = new Behaviour(random, bias);
        when(random.nextFloat()).thenReturn(0.1f);

        final CardPreference preference = behaviour.preference();
        assertThat(preference, is(CardPreference.REVEALED));

        verify(random).nextFloat();
    }

    @Test
    public void shouldReturnUnrevealedPreferenceWhenRandomIsMoreThanBias() {
        float bias = 0.2f;
        behaviour = new Behaviour(random, bias);
        when(random.nextFloat()).thenReturn(0.4f);

        final CardPreference preference = behaviour.preference();
        assertThat(preference, is(CardPreference.UNREVEALED));

        verify(random).nextFloat();
    }
}
