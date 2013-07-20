package cplim;

public class GameStub extends Game {


    private static final int WIDTH = 6;
    private static final int HEIGHT = 5;
    private final String[][] DATA = new String[WIDTH][HEIGHT];

    public static Game stubGame() {
        return new GameStub();
    }

    public GameStub() {
        super(null, null);
        initData();
    }

    @Override
    public void guess(Card card) {
        card.setValue(DATA[card.getX()][card.getY()]);
    }

    @Override
    public String getId() {
        return "51e7ecc46924fe1e7bc9c70f";
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    private void initData() {
        DATA[0][0] = "a";
        DATA[0][1] = "k";
        DATA[0][2] = "e";
        DATA[0][3] = "l";
        DATA[0][4] = "o";
        DATA[0][5] = "c";

        DATA[1][0] = "n";
        DATA[1][1] = "m";
        DATA[1][2] = "b";
        DATA[1][3] = "b";
        DATA[1][4] = "g";
        DATA[1][5] = "l";

        DATA[2][0] = "h";
        DATA[2][1] = "i";
        DATA[2][2] = "a";
        DATA[2][3] = "i";
        DATA[2][4] = "f";
        DATA[2][5] = "j";

        DATA[3][0] = "o";
        DATA[3][1] = "c";
        DATA[3][2] = "n";
        DATA[3][3] = "h";
        DATA[3][4] = "e";
        DATA[3][5] = "k";

        DATA[4][0] = "d";
        DATA[4][1] = "j";
        DATA[4][2] = "f";
        DATA[4][3] = "d";
        DATA[4][4] = "g";
        DATA[4][5] = "m";

    }
}
