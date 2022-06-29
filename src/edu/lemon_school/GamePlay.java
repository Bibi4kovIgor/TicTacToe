package edu.lemon_school;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static edu.lemon_school.Draw.drawField;
import static edu.lemon_school.Sign.*;
import static edu.lemon_school.Utils.SIZE;

public class GamePlay {

    public static final String INPUT_SIGN_WELCOME = "Input sign: ";
    public static final String INPUT_COORDINATES_X_Y = "Input coordinates [row,column] for %c\n";
    public static final String WRONG_INPUT_PARAMETERS =
            "Wrong coordinates parameters";
    public static final String WRONG_INPUT_PARAMETERS_SET_X =
            "Wrong sign parameter set 'X' by default";

    private final Sign[][] field;

    public GamePlay () {
        field = new Sign[SIZE][SIZE];
        initializeWithEmpties();
    }

    public GamePlay(Sign[][] field) {
        this.field = field;
    }

    private void initializeWithEmpties() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = EMPTY;
            }
        }
    }

    public Sign[][] getField() {
        return field;
    }


    private GameStates getWinner(Sign sign) {
        return switch (sign) {
            case CROSS -> GameStates.WINNERX;
            case ZERO -> GameStates.WINNER0;
            default -> GameStates.TIE;
        };
    }

    public GameStates checkGameState(Sign sign){
        if (isHorizontalCompleted(sign)
                || isMainDiagonalCompleted(sign)
                || isSideDiagonalCompleted(sign)
                || isVerticalCompleted(sign)) {
            return getWinner(sign);
        } else if (isTie()) {
            return GameStates.TIE;
        }

        return GameStates.GAME_IS_CONTINUE;
    }

    private boolean isRowEquals(Sign[] temp, Sign sign) {
        for (Sign c : temp) {
            if(c != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean isHorizontalCompleted(Sign sign) {
        Sign[] temp = new Sign[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j] == sign) {
                    temp[j] = field[i][j];
                }
            }
            if (isRowEquals(temp, sign)) {
                return true;
            }
            Arrays.fill(temp, EMPTY);
        }
        return false;
    }

    private boolean isVerticalCompleted(Sign sign) {
        Sign[] temp = new Sign[SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[j][i] == sign) {
                    temp[j] = field[j][i];
                }
            }
            if(isRowEquals(temp, sign)) {
                return true;
            }
            Arrays.fill(temp, EMPTY);
        }
        return false;
    }

    private boolean isMainDiagonalCompleted(Sign sign) {
        for (int i = 0; i < SIZE; i++) {
            if (field[i][i] != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean isSideDiagonalCompleted(Sign sign) {
        for (int i = 0; i < SIZE; i++) {
            if (field[i][SIZE - i - 1] != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean isTie() {
        for (Sign[] rows : field) {
            for (Sign element : rows) {
                if (element == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean inputTurnsCoordinates(int x, int y, Sign sign) {
        if (x >= SIZE || x < 0 || y >= SIZE || y < 0) {
            return false;
        }

        if(field[x][y] == EMPTY) {
            field[x][y] = sign;
        } else {
            return false;
        }
        return true;
    }

    private int[] inputValues(Sign sign) {
        Scanner scanner = new Scanner(System.in);
        int[] inputArray = new int[2];
        try {
            System.out.printf(INPUT_COORDINATES_X_Y, sign.getSign1());
            scanner.useDelimiter("\\s|,|\\n");
            inputArray[0] = scanner.nextInt();
            inputArray[1] = scanner.nextInt();
        } catch (InputMismatchException exception) {
            Arrays.fill(inputArray, -1);
        }
        return inputArray;
    }

    public void game () {
        GameStates currentGameState = GameStates.GAME_IS_CONTINUE;

        drawField(field);
        String inputSign = getSignFromInput();

        Sign sign = inputSign.equals("") ? CROSS : getSign(inputSign.charAt(0));

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
            sign = Sign.getOppositeSign(sign);
        } while(Objects.equals(currentGameState, GameStates.GAME_IS_CONTINUE));
        System.out.println(currentGameState.getGameStateMessage());
    }

    private void printErrorCoordinatesMessage() {
        System.out.println(WRONG_INPUT_PARAMETERS);
    }

    private String getSignFromInput() {
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.print(INPUT_SIGN_WELCOME);
        input = scanner.next();
        if(!Objects.equals(getSign(input.charAt(0)), CROSS)
                && !Objects.equals(getSign(input.charAt(0)), ZERO)) {
            input = "";
        }

        return input;
    }
}

