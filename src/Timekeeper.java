import java.util.TimerTask;

public class Timekeeper extends TimerTask {
    long startTime;
    int timeLimit;
    Game game;

    public Timekeeper(Game game, int timeLimit) {
        this.startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.game = game;
    }

    @Override
    public void run() {
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("Elapsed time:  " + elapsedTime + " seconds. ");
        if (this.timeLimit <= elapsedTime) {
            System.out.println("Time's up!");
            game.finish();
        }
    }
}
