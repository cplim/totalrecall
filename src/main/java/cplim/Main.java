package cplim;

import cplim.strategy.GameStrategy;
import cplim.strategy.GreedyStrategy;

public class Main {
    public static void main(String[] args) {
        Game game = Game.newGame();
        game.start("cp", "cheenpin.lim@gmail.com");
        GameStrategy solver = new GreedyStrategy(game);
        final Result result = solver.solve();

        System.out.println("Success: "+result.isSuccess());
        System.out.println("message: "+result.getMessage());
        System.out.println("number of guesses: "+result.getStatistic().numberOfGuesses());
    }
}
