package edu.lemon_school;

import java.util.Objects;

public enum Sign {
    CROSS('X', 'x'),
    ZERO('0', 'O'),
    EMPTY('\u0020', ' ');

    private final char sign1;
    private final char sign2;

    Sign(char sign1, char sign2 ) {
        this.sign1 = sign1;
        this.sign2 = sign2;
    }

    public char getSign1() {
        return sign1;
    }

    public char getSign2() {
        return sign2;
    }

    public static Sign getSign(char sign) {
        return switch (sign) {
            case 'X', 'x' -> CROSS;
            case '0', 'O' -> ZERO;
            default -> EMPTY;
        };
    }

    public static Sign getOppositeSign(Sign sign) {
        return Objects.equals(sign, CROSS) ? ZERO : CROSS;
    }
}
