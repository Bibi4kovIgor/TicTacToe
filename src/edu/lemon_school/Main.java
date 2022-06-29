package edu.lemon_school;

import static edu.lemon_school.Draw.drawField;

public class Main {

    public static void main(String[] args)  {
        GamePlay gamePlay = new GamePlay();
        drawField(gamePlay.getField());
        System.out.println(gamePlay.game().getGameStateMessage());
    }
}
