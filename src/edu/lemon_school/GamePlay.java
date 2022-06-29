package edu.lemon_school;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static edu.lemon_school.Action.EMPTY;
import static edu.lemon_school.Draw.drawField;
import static edu.lemon_school.Utils.SIZE;

public class GamePlay {

    public static final String INPUT_SIGN_WELCOME = "Input sign: ";
    public static final String INPUT_COORDINATES_X_Y = "Input coordinates [row,column] for %c\n";
    public static final String WRONG_INPUT_PARAMETERS =
            "Wrong coordinates parameters";

    private final char[][] field = new char[SIZE][SIZE];

    public GamePlay () {
        initializeWithEmpties();
    }

    private void initializeWithEmpties() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = EMPTY.getSign1();
            }
        }
    }

    public char[][] getField() {
        return field;
    }


    private GameStates getWinner(Action sign) {
        return switch (sign) {
            case CROSS -> GameStates.WINNERX;
            case ZERO -> GameStates.WINNER0;
            default -> GameStates.TIE;
        };
    }

    public GameStates checkGameState(char sign){
        if (isHorizontalCompleted(sign)
                || isMainDiagonalCompleted(sign)
                || isSideDiagonalCompleted(sign)
                || isVerticalCompleted(sign)) {
            return getWinner(Action.getSign(sign));
        } else if (isTie()) {
            return GameStates.TIE;
        }

        return GameStates.GAME_IS_CONTINUE;
    }

    private boolean isRowEquals(char[] temp, char sign) {
        for (char c : temp) {
            if(c != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean isHorizontalCompleted(char sign) {
        char[] temp = new char[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == sign) {
                    temp[j] = field[i][j];
                }
            }
            if (isRowEquals(temp, sign)) {
                return true;
            }
            Arrays.fill(temp, EMPTY.getSign1());
        }
        return false;
    }

    private boolean isVerticalCompleted(char sign) {
        char[] temp = new char[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[j][i] == sign) {
                    temp[j] = field[j][i];
                }
            }
            if(isRowEquals(temp, sign)) {
                return true;
            }
            Arrays.fill(temp, EMPTY.getSign1());
        }
        return false;
    }

    private boolean isMainDiagonalCompleted(char sign) {
        for (int i = 0; i < SIZE; i++) {
            if (field[i][i] != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean isSideDiagonalCompleted(char sign) {
        for (int i = 0; i < SIZE; i++) {
            if (field[i][SIZE - i - 1] != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean isTie() {
        for (char[] rows : field) {
            for (char element : rows) {
                if (element == EMPTY.getSign1()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean inputTurnsCoordinates(int x, int y, char sign) {
        if (x >= SIZE || x < 0 || y >= SIZE || y < 0) {
            return false;
        }

        if (sign != Action.ZERO.getSign1()
                && sign != Action.CROSS.getSign1()
                && sign != Action.ZERO.getSign2()
                && sign != Action.CROSS.getSign2()) {
            return false;
        }

        if(field[x][y] == EMPTY.getSign1()) {
            field[x][y] = sign;
        } else {
            return false;
        }
        return true;
    }

    private int[] inputValues(char sign) {
        Scanner scanner = new Scanner(System.in);
        int[] inputArray = new int[2];
        try {
            System.out.printf(INPUT_COORDINATES_X_Y, sign);
            scanner.useDelimiter("\\s|,|\\n");
            inputArray[0] = scanner.nextInt();
            inputArray[1] = scanner.nextInt();
        } catch (InputMismatchException exception) {
            Arrays.fill(inputArray, -1);
        }
        return inputArray;
    }

    public GameStates game () {
        GameStates currentGameState = GameStates.GAME_IS_CONTINUE;
        char sign = getSignFromInput().charAt(0);

        do {
            int[] coordinates = inputValues(sign);
            int x = coordinates[0];
            int y = coordinates[1];
            if (!inputTurnsCoordinates(x, y, sign)) {
                printErrorCoordinatesMessage();
                continue;
            }
            drawField(getField());
            currentGameState = checkGameState(sign);
            sign = Action.getOppositeSign(sign);
        } while(Objects.equals(currentGameState, GameStates.GAME_IS_CONTINUE));

        return currentGameState;
    }

    private void printErrorCoordinatesMessage() {
        System.out.println(WRONG_INPUT_PARAMETERS);
    }

    private String getSignFromInput() {
        Scanner scanner = new Scanner(System.in);
        String input;
        System.out.print(INPUT_SIGN_WELCOME);
        input = scanner.next();
        return input;
    }
}

