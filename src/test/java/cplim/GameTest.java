package cplim;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Game.class)
public class GameTest {
    private HttpClient httpClient;
    private ObjectMapper objectMapper;
    private Game game;

    @Before
    public void setup() {
        httpClient = mock(HttpClient.class);
        objectMapper = new ObjectMapper();
        game = new Game(httpClient, objectMapper);
    }

    @Test
    public void shouldCreateNewGame() throws Exception {
        final String id = "51ea0ddd6924fe1e7bc9c755";
        final int width = 6;
        final int height = 5;
        final String jsonData = "{\n" +
                "width: " + width + ",\n" +
                "id: \"" + id + "\",\n" +
                "height: " + height + "\n" +
                "}";
        // mock http interaction
        PostMethod httpPost = mock(PostMethod.class);
        whenNew(PostMethod.class).withParameterTypes(String.class).withArguments(isA(String.class)).thenReturn(httpPost);
        when(httpClient.executeMethod(httpPost)).thenReturn(HttpStatus.SC_OK);
        when(httpPost.getResponseBodyAsString()).thenReturn(jsonData);

        game.start();

        assertThat(game.getId(), is(id));
        assertThat(game.getWidth(), is(width));
        assertThat(game.getHeight(), is(height));

        verify(httpPost, times(1)).getResponseBodyAsString();
    }

    @Test
    public void shouldAssignValueToCard() throws Exception {
        GetMethod httpGet = mock(GetMethod.class);
        whenNew(GetMethod.class).withParameterTypes(String.class).withArguments(isA(String.class)).thenReturn(httpGet);
        when(httpClient.executeMethod(httpGet)).thenReturn(HttpStatus.SC_OK);
        when(httpGet.getResponseBodyAsString()).thenReturn("c");

        Card card = new Card(0,1);
        game.guess(card);

        assertThat(card.getValue(), is("c"));

        verify(httpGet, times(1)).getResponseBodyAsString();
    }

    @Test
    public void shouldEndGameUnsuccessfully() throws Exception {
        final String jsonData = "{\n" +
                "message: \"You LOST! You ended too early!\",\n" +
                "success: false\n" +
                "}";
        final Card first = new Card(1,1);
        final Card second = new Card(2,2);
        // mock http interaction
        PostMethod httpPost = mock(PostMethod.class);
        whenNew(PostMethod.class).withParameterTypes(String.class).withArguments(isA(String.class)).thenReturn(httpPost);
        when(httpClient.executeMethod(httpPost)).thenReturn(HttpStatus.SC_OK);
        when(httpPost.getResponseBodyAsString()).thenReturn(jsonData);

        Result result = game.end(first, second);

        assertThat(result.isSuccess(), is(false));

        verify(httpPost, times(1)).getResponseBodyAsString();
    }

    @Test
    public void shouldEndGameSuccessfully() throws Exception {
        final String jsonData = "{\n" +
                "message: \"Success!\",\n" +
                "success: true\n" +
                "}";
        final Card first = new Card(1,1);
        final Card second = new Card(2,2);
        // mock http interaction
        PostMethod httpPost = mock(PostMethod.class);
        whenNew(PostMethod.class).withParameterTypes(String.class).withArguments(isA(String.class)).thenReturn(httpPost);
        when(httpClient.executeMethod(httpPost)).thenReturn(HttpStatus.SC_OK);
        when(httpPost.getResponseBodyAsString()).thenReturn(jsonData);

        Result result = game.end(first, second);

        assertThat(result.isSuccess(), is(true));

        verify(httpPost, times(1)).getResponseBodyAsString();
    }

}
