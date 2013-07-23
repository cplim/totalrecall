package cplim;

import cplim.quaid.memory.Outcome;
import cplim.quaid.memory.Statistician;
import cplim.strategy.GameStrategy;
import cplim.strategy.GreedyStrategy;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class Main {
    public static void main(String... args) throws Exception {
        // keep track of results from each run
        File memory = new File(FileUtils.getTempDirectory(), "totalrecall.json");
        System.out.println("Storing data in "+memory.getCanonicalPath());
        Statistician statistician = new Statistician(memory);

        // run the game and then print out the result each time
        int runs = Integer.valueOf(args[0]);
        System.out.println("Going to solve game "+runs+" times");
        for(int i=0;i<runs;i++) {
            Game game = Game.newGame();
            game.start("cp", "cheenpin.lim@gmail.com");
            GameStrategy solver = new GreedyStrategy(game, statistician);
            final Result result = solver.solve();

            System.out.println("Success: "+result.isSuccess());
            System.out.println("message: "+result.getMessage());
            System.out.println("number of guesses: "+result.getStatistic().numberOfGuesses());
        }

        // print out the best outcome so far.
        final Outcome best = statistician.bestOutcomeSoFar();
        System.out.println(
            String.format(
                "Best outcome so far - [guesses %d, first pick bias %f, second pick bias %f]",
                best.getGuesses(),
                best.getFirstPickBias(),
                best.getSecondPickBias()
            )
        );
    }
}
