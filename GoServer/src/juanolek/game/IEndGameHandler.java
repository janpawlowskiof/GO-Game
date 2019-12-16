package juanolek.game;

import java.util.List;

public interface IEndGameHandler {
    void handleEndGame(List<GameBoardChange> boardChanges);
}
