package cplim;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class Game {
    private final HttpClient client;
    private final ObjectMapper mapper;
    private String id;
    private int width;
    private int height;

    public Game(HttpClient client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
        this.mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public static Game newGame() {
        return new Game(new HttpClient(), new ObjectMapper());
    }

    public void start() {
        System.out.println("Calling start");
        PostMethod httpPost = null;
        GetMethod httpGet = null;
        try {
            httpPost = new PostMethod(String.format("http://totalrecall.99cluster.com/games/"));
            httpPost.addParameter("name", "cp");
            httpPost.addParameter("email", "cheenpin.lim@gmail.com");
            final int statusCode = client.executeMethod(httpPost);

            if(statusCode != 302) {
                throw new RuntimeException("Failed to post, got HTTP failure: "+statusCode);
            }

            // follow redirect
            final Header location = httpPost.getResponseHeader("Location");
            httpGet = new GetMethod(location.getValue());
            client.executeMethod(httpGet);

            final Map<String, Object> gameData = mapper.readValue(httpGet.getResponseBodyAsString(), Map.class);
            id = String.valueOf(gameData.get("id"));
            width = Integer.valueOf(gameData.get("width").toString());
            height = Integer.valueOf(gameData.get("height").toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to make a guess!", e);
        } finally {
            if(httpPost != null) {
                httpPost.releaseConnection();
            }
            if(httpGet != null) {
                httpGet.releaseConnection();
            }
        }
    }

    public void guess(Card card) {
        System.out.println("Calling guess");
        GetMethod httpGet = null;
        try {
            httpGet = new GetMethod(String.format("http://totalrecall.99cluster.com/games/%s/cards/%d,%d", id, card.getX(), card.getY()));
            client.executeMethod(httpGet);
            card.setValue(httpGet.getResponseBodyAsString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to make a guess!", e);
        } finally {
            if(httpGet != null) {
                httpGet.releaseConnection();
            }
        }
    }

    public Result end(Card first, Card second) {
        System.out.println("Calling end");
        PostMethod httpPost = null;
        try {
            httpPost = new PostMethod(String.format("http://totalrecall.99cluster.com/games/%s/end", id));
            httpPost.addParameter("x1", String.valueOf(first.getX()));
            httpPost.addParameter("y1", String.valueOf(first.getY()));
            httpPost.addParameter("x2", String.valueOf(second.getX()));
            httpPost.addParameter("y2", String.valueOf(second.getY()));
            client.executeMethod(httpPost);

            return mapper.readValue(httpPost.getResponseBodyAsString(), Result.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to make a guess!", e);
        } finally {
            if(httpPost != null) {
                httpPost.releaseConnection();
            }
        }
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
