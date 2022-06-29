package edu.lemon_school;

public enum Action {
    CROSS('X', 'x'),
    ZERO('0', 'O'),
    EMPTY('\u0020', ' ');

    private final char sign1;
    private final char sign2;

    Action(char sign1, char sign2 ) {
        this.sign1 = sign1;
        this.sign2 = sign2;
    }

    public char getSign1() {
        return sign1;
    }

    public char getSign2() {
        return sign2;
    }

    public static Action getSign(char sign) {
        return switch (sign) {
            case 'X', 'x' -> CROSS;
            case '0', 'O' -> ZERO;
            default -> EMPTY;
        };
    }

    public static char getOppositeSign(char sign) {
        return switch (sign) {
            case 'X', 'x' -> ZERO.getSign1();
            case '0', 'O' -> CROSS.getSign1();
            default -> EMPTY.getSign1();
        };
    }
}
