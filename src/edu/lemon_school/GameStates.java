package edu.lemon_school;

public enum GameStates {
    WINNERX("X won's!"),
    WINNER0("0 won's!"),
    TIE("Tie!"),
    GAME_IS_CONTINUE("Waiting for your move:");

    private final String gameStateMessage;

    GameStates(String gameState) {
        this.gameStateMessage = gameState;
    }

    public String getGameStateMessage() {
        return gameStateMessage;
    }

}
