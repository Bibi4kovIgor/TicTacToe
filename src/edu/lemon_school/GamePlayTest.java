package edu.lemon_school;


import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static edu.lemon_school.Draw.drawField;
import static edu.lemon_school.GameStates.TIE;
import static edu.lemon_school.Sign.CROSS;
import static edu.lemon_school.Sign.ZERO;
import static edu.lemon_school.Utils.SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GamePlayTest {

    @Test
    void checkGameState_InputFieldWithSigns_ReturnTie() {
        // arrange - act - assert
        Sign[][] field = new Sign[SIZE][SIZE];

        field[0][0] = CROSS;
        field[0][1] = ZERO;
        field[0][2] = CROSS;

        field[1][0] = ZERO;
        field[1][1] = ZERO;
        field[1][2] = CROSS;

        field[2][0] = CROSS;
        field[2][1] = CROSS;
        field[2][2] = ZERO;

        String expectedMessage = TIE.getGameStateMessage();
        GamePlay gamePlay = new GamePlay(field);

        drawField(field);

        // Act
        GameStates actual = gamePlay.checkGameState(CROSS);
        String actualMessage = gamePlay.checkGameState(CROSS).getGameStateMessage();

        assertEquals(TIE, actual, "Wrong game state");
        assertEquals(expectedMessage, actualMessage, "Wrong game result message");

    }
}