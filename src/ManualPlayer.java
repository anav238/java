import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ManualPlayer extends Player {
    Scanner scanner;

    public ManualPlayer(String name) {
        super(name);
        scanner = new Scanner(System.in);
    }

    @Override
    public Token chooseToken(List<Token> tokens) {
        Token tokenToTake = null;
        System.out.print("Available tokens: ");
        for (Token token:tokens)
            System.out.print(token.getValue() + " ");
        System.out.print('\n');
        do {
            System.out.print("Enter a token number from the available ones: ");
            int tokenNumber = scanner.nextInt();
            for (Token token : tokens) {
                if (token.getValue() == tokenNumber) {
                    tokenToTake = token;
                    break;
                }
            }
        } while (tokenToTake == null);
        return tokenToTake;
    }
}
