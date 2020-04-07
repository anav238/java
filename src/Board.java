import java.util.List;

public class Board {
    Game game;
    List<Token> tokens;

    public Board(List<Token> tokens) {
        this.tokens = tokens;
    }

    public void setGame(Game game) { this.game = game; }

    public synchronized boolean takeToken(Player player, Token token) {
        //Un jucator are voie sa ia un token doar cand e randul lui.
        while (game.getPlayerTurn() != player.getTurn()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (tokens.contains(token)) {
            tokens.remove(token);
            player.getPlayerTokens().add(token);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {};

            //Daca intre timp jocul s-a terminat (a expirat timpul), ne oprim.
            if (game.hasFinished())
                return false;

            //Afisam ultimul token luat de jucator si lista de tokeni pe care acesta ii are.
            System.out.println(player.getName() + " took token " + token.getValue() + ". ");
            System.out.print(player.getName() + " tokens: ");
            for (Token playerToken:player.getPlayerTokens())
                System.out.print(playerToken.getValue() + " ");
            System.out.print('\n');

            //Verificam daca jucatorul a incheiat jocul (fie a luat ultima piesa de pe tabla, fie are progresia
            //necesara. In caz afirmativ, nu va mai veni randul urmatorului jucator
            if (!game.checkIfPlayerFinished(player))
                game.setPlayerTurn(player.getTurn() + 1);
            notifyAll();
            return true;
        }
        return false;
    }

    public synchronized List<Token> getTokens(Player player) {
        //Jucatorul poate vedea piesele de pe tabla doar cand e randul lui, pentru a nu
        //incerca sa ia o piesa care urmeaza sa fie luata de alt jucator.
        while (game.getPlayerTurn() != player.getTurn()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return tokens;
    }

    public int getNumberOfTokens() {
        return tokens.size();
    }
}
