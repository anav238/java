import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class Game {
    List<Player> players;
    Board board;
    int playerTurn = 0;
    boolean hasFinished = false;
    int winningProgressionSize;
    Timer timer;
    List<Thread> threads;
    int initialTokens;
    int timeLimit;

    public Game(List<Player> players, Board board, int winningProgressionSize, int timeLimit) {
        this.players = players;
        this.board = board;
        this.winningProgressionSize = winningProgressionSize;
        this.timeLimit = timeLimit;
        this.threads = new ArrayList<>();
        this.initialTokens = board.getNumberOfTokens();
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public Board getBoard() {
        return board;
    }

    public synchronized void start() {
        //Jucatorii au nevoie de o instanta de game pt a verifica daca acesta s-a incheiat, pt a obtine piesele
        //de pe board si, in cazul SmartPlayer, pt a vedea ce piese au ceilalti jucattori
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setTurn(i);
            players.get(i).setGame(this);
        }
        playerTurn = 0;

        //Durata maxima a unui joc va fi de timeLimit minute.
        Timekeeper timekeeper = new Timekeeper(this, 60 * timeLimit);
        Thread timekeeperThread = new Thread(timekeeper);
        //Deoarece thread-ul timekeeper este daemon, acesta se va inchide automat la incheierea celorlalte thread-uri
        timekeeperThread.setDaemon(true);
        timer = new Timer();
        //Programam actiunea din run a timekeeper-ului (afisarea timpului scurs de la inceputul jocului)
        //din 30 in 30 de secunde.
        timer.schedule(timekeeper, 1000, 1000 * 30);
        timekeeperThread.start();

        //Pornim thread-urile jucatorilor, care vor lua pe rand piese de pe board.
        for (int i = 0; i < players.size(); i++) {
            Thread playerThread = new Thread(players.get(i));
            threads.add(playerThread);
            playerThread.start();
        }
    }

    public boolean hasFinished() {
        return this.hasFinished;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn % players.size();
    }

    public boolean checkIfPlayerFinished(Player player) {
        if (board.getNumberOfTokens() == 0) {
            finish();
            return true;
        }
        List<List<Token>> longestProgressions = GameUtil.getLongestProgressions(player);
        if (longestProgressions.get(0).size() == this.winningProgressionSize) {
            finish();
            System.out.println(player.getName() + " has won with " + initialTokens + " points!");
            return true;
        }
        return false;
    }

    public void finish() {
        this.hasFinished = true;
        timer.cancel();
        int maxProgression = 0;
        for (Player currPlayer:players) {
            List<List<Token>> playerProgression = GameUtil.getLongestProgressions(currPlayer);
            if (playerProgression.get(0).size() > maxProgression)
                maxProgression = playerProgression.get(0).size();
            System.out.println(currPlayer.getName() + " has " + playerProgression.get(0).size() + " points!");
        }
    }
}
