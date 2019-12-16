package juanolek.game;

import juanolek.exceptions.InvalidMoveException;
import juanolek.exceptions.TrashDataException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class GameLogicTest {

    IEndGameHandler endGameHandler;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testCellNotEmpty() throws InvalidMoveException, TrashDataException {
        expectedEx.expect(InvalidMoveException.class);
        expectedEx.expectMessage("Can't place pawn on a non-empty tile");

        GameLogic testGameLogic = new GameLogic(endGameHandler,19);
        testGameLogic.setPawn(1, 1, GamePawnType.White);
        testGameLogic.setPawn(12, 1, GamePawnType.Black);
        testGameLogic.setPawn(1, 1, GamePawnType.White);
    }

    @Test
    public void testNoBreaths() throws InvalidMoveException, TrashDataException {
        expectedEx.expect(InvalidMoveException.class);
        expectedEx.expectMessage("Can't place pawn on a tile with no breaths");

        GameLogic testGameLogic = new GameLogic(endGameHandler,19);

        testGameLogic.setPawn(2, 2, GamePawnType.White);
        testGameLogic.setPawn(12, 11, GamePawnType.Black);
        testGameLogic.setPawn(3, 1, GamePawnType.White);
        testGameLogic.setPawn(12, 10, GamePawnType.Black);
        testGameLogic.setPawn(4, 2, GamePawnType.White);
        testGameLogic.setPawn(3, 2, GamePawnType.Black);
        testGameLogic.setPawn(3, 3, GamePawnType.White);
        testGameLogic.setPawn(3, 2, GamePawnType.Black);

    }

    @Test
    public void testKoRule() throws InvalidMoveException, TrashDataException {
        expectedEx.expect(InvalidMoveException.class);
        expectedEx.expectMessage("Can't place pawn here because of ko rule");

        GameLogic testGameLogic = new GameLogic(endGameHandler,19);

        testGameLogic.setPawn(4, 1, GamePawnType.White);
        testGameLogic.setPawn(5 ,1, GamePawnType.Black);
        testGameLogic.setPawn(6, 1, GamePawnType.White);
        testGameLogic.setPawn(10, 1, GamePawnType.Black);
        testGameLogic.setPawn(5, 0, GamePawnType.White);
        testGameLogic.setPawn(10, 2, GamePawnType.Black);
        testGameLogic.setPawn(4, 2, GamePawnType.White);
        testGameLogic.setPawn(10, 3, GamePawnType.Black);
        testGameLogic.setPawn(6, 2, GamePawnType.White);
        testGameLogic.setPawn(5, 2, GamePawnType.Black);
        testGameLogic.setPawn(5, 3, GamePawnType.White);
        testGameLogic.setPawn(5, 2, GamePawnType.Black);
    }

    @Test
    public void testWhiteStartsFirst() throws InvalidMoveException, TrashDataException {
        expectedEx.expect(InvalidMoveException.class);
        expectedEx.expectMessage("The opponent must move first");

        GameLogic testGameLogic = new GameLogic(endGameHandler,19);
        testGameLogic.setPawn(1, 1, GamePawnType.Black);
        testGameLogic.setPawn(12, 1, GamePawnType.White);
    }





}