import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Generam tokenii pentru board
        int n = 15;
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token());
        tokens.add(new Token());
        for (int i = 1; i <= n - 2; i++)
            tokens.add(new Token(i));

        //Cream board-ul si jucatorii
        Board board = new Board(tokens);
        List<Player> players = new ArrayList<>();
        players.add(new RandomPlayer("Random Player"));
        players.add(new SmartPlayer("Smart Player"));
        players.add(new ManualPlayer("Manual Player"));

        //Cream si pornim un joc nou cu progresia castigatoare egala cu 4 si limita de timp de 5 minute.
        Game game = new Game(players, board, 4, 5);
        board.setGame(game);
        game.start();
    }
}
