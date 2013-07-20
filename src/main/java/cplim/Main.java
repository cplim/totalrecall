package cplim;

public class Main {
    public static void main(String[] args) {
        Game game = Game.newGame();
        game.start();
        GameSolver solver = new GameSolver(game);
        final Result result = solver.solve();

        System.out.println("Success: "+result.isSuccess());
        System.out.println("message: "+result.getMessage());
    }
}
