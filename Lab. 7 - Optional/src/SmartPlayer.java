import java.util.List;
import java.util.Random;

public class SmartPlayer extends Player {

    public SmartPlayer(String name) {
        super(name);
    }

    @Override
    public Token chooseToken(List<Token> tokens) {
        //Cel mai avantajos este mereu token-ul blank, deci intai verificam daca exista pe board, ca sa il
        //putem lua.
        Token zeroToken = new Token();
        if (tokens.contains(zeroToken))
            return zeroToken;

        //Daca nu exista token-ul blank, alegem, daca este posibil un token de care are nevoie jucatorul si/sau
        //de care au nevoie si alti jucatori.
        Token usefulForPlayer = null;
        Token usefulForOthers = null;
        for (Token token:tokens) {
            if (GameUtil.isNeededByPlayer(token, this)) {
                if (GameUtil.isNeededByOthers(token, this, game.getPlayers()))
                    return token;
                else
                    usefulForPlayer = token;
            }
            else if (GameUtil.isNeededByOthers(token, this, game.getPlayers()))
                usefulForOthers = token;
        }
        if (usefulForPlayer == null && usefulForOthers == null)
            return tokens.get(0);
        if (usefulForPlayer == null)
            return usefulForOthers;
        return usefulForPlayer;
    }
}
