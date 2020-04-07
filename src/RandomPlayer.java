import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player {
    public RandomPlayer(String name) {
        super(name);
    }

    @Override
    public Token chooseToken(List<Token> tokens) {
        Random random = new Random();
        int randomIndex = random.nextInt(tokens.size());
        return tokens.get(randomIndex);
    }
}
