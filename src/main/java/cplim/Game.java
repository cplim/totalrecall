package cplim;

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

    public void start() {
        try {
            PostMethod httpPost = new PostMethod(String.format("http://totalrecall.99cluster.com/games/"));
            httpPost.addParameter("name", "cp");
            httpPost.addParameter("email", "cheenpin.lim@gmail.com");
            client.executeMethod(httpPost);

            final Map<String, Object> gameData = mapper.readValue(httpPost.getResponseBodyAsString(), Map.class);
            id = String.valueOf(gameData.get("id"));
            width = Integer.valueOf(gameData.get("width").toString());
            height = Integer.valueOf(gameData.get("height").toString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to make a guess!", e);
        }
    }

    public void guess(Card card) {
        try {
            GetMethod httpGet = new GetMethod(String.format("http://totalrecall.99cluster.com/games/%s/cards/%d,%d", id, card.getX(), card.getY()));
            client.executeMethod(httpGet);
            card.setValue(httpGet.getResponseBodyAsString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to make a guess!", e);
        }
    }

    public Result end(Card first, Card second) {
        try {
            PostMethod httpPost = new PostMethod(String.format("http://totalrecall.99cluster.com/games/%s/end", id));
            httpPost.addParameter("x1", String.valueOf(first.getX()));
            httpPost.addParameter("y1", String.valueOf(first.getY()));
            httpPost.addParameter("x2", String.valueOf(second.getX()));
            httpPost.addParameter("y2", String.valueOf(second.getY()));
            client.executeMethod(httpPost);

            return mapper.readValue(httpPost.getResponseBodyAsString(), Result.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to make a guess!", e);
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
