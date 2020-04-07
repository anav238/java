import java.util.Objects;

public class Token implements Comparable<Token> {
    int value;
    public Token(int value) {
        this.value = value;
    }
    public Token() {
        this.value = 0;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return value == token.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(Token o) {
        return ((Integer)this.getValue()).compareTo(o.getValue());
    }


}
