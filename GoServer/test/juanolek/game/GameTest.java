package juanolek.game;

import juanolek.*;
import juanolek.exceptions.InvalidMoveException;
import juanolek.exceptions.NoSlotsAvailableException;
import juanolek.exceptions.TrashDataException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class GameTest {


    private  class TestPlayer extends Player {

        public TestPlayer() {
            super(null);
        }

        @Override
        public void startReceivingMessages(){}
        public void sendMessage(Message message){}
    }


    @Test
    public void testBadCoordinates() throws NoSlotsAvailableException {
        TestPlayer testPlayer1 = new TestPlayer();
        TestPlayer testPlayer2 = new TestPlayer();
        Game testGame = new Game(19, testPlayer1);
        testGame.addPlayer(testPlayer2);
        testGame.setPawn(21,14,testPlayer1);
    }


    @Test(expected = NoSlotsAvailableException.class)
    public void testAddTooManyPlayers() throws NoSlotsAvailableException {

        TestPlayer testPlayer1 = new TestPlayer();
        TestPlayer testPlayer2 = new TestPlayer();
        TestPlayer testPlayer3 = new TestPlayer();

        Game testGame = new Game(19, testPlayer1);

        testGame.addPlayer(testPlayer2);
        testGame.addPlayer(testPlayer3);
    }

}