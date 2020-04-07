import java.util.*;

public abstract class Player implements Runnable {
    String name;
    Game game;
    Board board;
    int turn;
    List<Token> playerTokens;

    public Player(String name) {
        this.name = name;
        playerTokens = new ArrayList<>();
    }

    @Override
    public synchronized void run() {
        while (!game.hasFinished()) {
            List<Token> tokens = board.getTokens(this);
            if (tokens.size() == 0)
                break;
            board.takeToken(this, chooseToken(tokens));
        }
    }

    public abstract Token chooseToken(List<Token> tokens);

    public void setGame(Game game) {
        this.game = game;
        this.board = game.getBoard();
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return this.turn;
    }

    public String getName() {
        return name;
    }

    public List<Token> getPlayerTokens() {
        return playerTokens;
    }
}
