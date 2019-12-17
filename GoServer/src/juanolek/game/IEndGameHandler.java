package juanolek.game;

import java.util.List;

interface IEndGameHandler {
    void handleEndGame(List<GameBoardChange> boardChanges);
}
