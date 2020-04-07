import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameUtil {

    public static boolean isNeededByOthers(Token token, Player currentPlayer, List<Player> players) {
        for (Player player:players)
            if (!player.equals(currentPlayer) && isNeededByPlayer(token, player))
                return true;
        return false;
    }

    public static boolean isNeededByPlayer(Token token, Player player) {
        //Unui jucator ce are o singura piesa ii este utila orice piesa.
        if (player.getPlayerTokens().size() == 1)
            return true;

        int tokenValue = token.getValue();
        List<List<Token>> longestProgressions = getLongestProgressions(player);
        for (List<Token> progression:longestProgressions) {
            int diff = progression.get(1).getValue() - progression.get(0).getValue();
            //Verificam daca putem extinde progresia curenta adaugand token-ul primit ca parametru la inceputul
            //sau la finalul ei
            if (tokenValue == progression.get(0).getValue() - diff
                    || tokenValue == progression.get(progression.size() - 1).getValue() + diff)
                return true;
        }
        return false;
    }

    public static List<List<Token>> getLongestProgressions(Player player) {
        List<Token> tokens = player.getPlayerTokens();

        //Sortam colectia pentru a putea determina mai eficient progresiile.
        Collections.sort(tokens);

        //Calculam numarul de zerouri din lista de tokens, care vor creste dimensiunea progresiei maxime.
        int zeroes = 0;
        while (zeroes < tokens.size() && tokens.get(zeroes).getValue() == 0)
            zeroes++;

        //Pentru liste de lungime minimum 2, lungimea minima a unei progresii va fi egala cu 2 (putem forma oricand
        //o progresie cu 2 elemente), la care se adauga nr. de piese blank.
        int minProgression = Math.min(tokens.size(), 2 + zeroes);

        int maxProgressionSize = 0;
        List<List<Token>> maxProgressions = new ArrayList<>();

        if (tokens.size() == 1) {
            List<Token> progression = new ArrayList<>();
            progression.add(tokens.get(0));
            maxProgressions.add(progression);
            return maxProgressions;
        }

        for (int i = zeroes; i < tokens.size(); i++) {
            int progressionSize;

            for (int j = i + 1; j < tokens.size(); j++) {
                //Incepem sa calculam lungimea maxima a unei progresii ce incepe cu tokenii de pe pozitiile i si j.
                List<Token> currentProgression = new ArrayList<>();
                currentProgression.add(tokens.get(i));
                currentProgression.add(tokens.get(j));

                //Diff reprezinta ratia progresiei
                int diff = tokens.get(j).getValue() - tokens.get(i).getValue();

                //Calculam valoarea pe care ar trebui sa o aiba urmatorul termen al progresiei si verificam
                //daca exista in lista. In caz afirmativ, repetam procesul cu urmatorul termen al progresiei,
                //pana cand acesta nu se mai regaseste in lista (progresia s-a incheiat)
                Token curr = new Token(tokens.get(j).getValue() + diff);
                while (tokens.contains(curr)) {
                    currentProgression.add(curr);
                    curr.setValue(curr.getValue() + diff);
                }

                //Dimensiunea reala a progresiei curente este egala cu dimensiunea progresiei gasite (nr de tokeni
                //din secventa) la care se adauga zerourile din lista
                if (currentProgression.size() + zeroes > maxProgressionSize) {
                    maxProgressionSize = currentProgression.size();
                    maxProgressions.clear();
                    maxProgressions.add(currentProgression);
                }
                else if (currentProgression.size() + zeroes == maxProgressionSize)
                    maxProgressions.add(currentProgression);
            }

        }

        //Cream lista cu piese blank ale jucatorului pt a le putea adauga la progresia maxima
        List<Token> zeroesList = new ArrayList<>();
        for (int i = 0; i < zeroes; i++)
            zeroesList.add(new Token(0));

        if (maxProgressions.size() == 0) {
            maxProgressions.add(zeroesList);
            if (tokens.size() - zeroes == 1)
                maxProgressions.get(0).add(tokens.get(zeroes));
        }
        else
            for(List<Token> progression:maxProgressions)
                progression.addAll(zeroesList);

        return maxProgressions;
    }
}
