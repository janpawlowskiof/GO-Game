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

    @Test
    public void testMoves() throws NoSlotsAvailableException {

        TestPlayer testPlayer1 = new TestPlayer();
        TestPlayer testPlayer2 = new TestPlayer();
        Game testGame = new Game(19,testPlayer1);

        testGame.setPawn(1,1,testPlayer1);

        testGame.addPlayer(testPlayer2);

        //this part tests if pawns delete properly
        testGame.setPawn(2,2,testPlayer1);
        testGame.setPawn(1,2,testPlayer2);
        testGame.setPawn(10,5,testPlayer1);
        testGame.setPawn(3,2,testPlayer2);
        testGame.setPawn(10,6,testPlayer1);
        testGame.setPawn(2,1,testPlayer2);
        testGame.setPawn(10,7,testPlayer1);
        testGame.setPawn(2,3,testPlayer2);
        testGame.setPawn(10,8,testPlayer1);

        testGame.pass(testPlayer2);
        testGame.pass(testPlayer1);


    }

    @Test
    public void testPass() throws NoSlotsAvailableException {

        TestPlayer testPlayer1 = new TestPlayer();
        TestPlayer testPlayer2 = new TestPlayer();
        Game testGame = new Game(19,testPlayer1);
        testGame.addPlayer(testPlayer2);

        //Pass with no moves
        testGame.pass(testPlayer1);
        testGame.pass(testPlayer2);

        Game testGame2 = new Game(19,testPlayer1);
        testGame2.addPlayer(testPlayer2);

        //move after pass
        testGame2.setPawn(1,1,testPlayer1);
        testGame2.pass(testPlayer2);
        testGame2.setPawn(1,3,testPlayer1);
        testGame2.pass(testPlayer1);

        Game testGame3 = new Game(19,testPlayer1);
        testGame3.addPlayer(testPlayer2);

        //kill pawn after pass
        testGame3.setPawn(2,2,testPlayer1);
        testGame3.setPawn(1,2,testPlayer2);
        testGame3.setPawn(10,5,testPlayer1);
        testGame3.setPawn(3,2,testPlayer2);
        testGame3.setPawn(10,6,testPlayer1);
        testGame3.setPawn(2,1,testPlayer2);

        testGame3.pass(testPlayer1);

        testGame.setPawn(2,3,testPlayer2);
        testGame3.pass(testPlayer2);
    }

    @Test
    public void testKickPlayer() throws NoSlotsAvailableException {

        TestPlayer testPlayer1 = new TestPlayer();
        TestPlayer testPlayer2 = new TestPlayer();

        //kicking player when he is alone in the game
        Game testGame = new Game(19,testPlayer1);
        testGame.kickPlayer(testPlayer1);

        //kicking one of the players from game
        Game testGame2 = new Game(19,testPlayer1);
        testGame2.addPlayer(testPlayer2);
        testGame2.kickPlayer(testPlayer2);
    }

    @Test
    public void testGameWithBot() throws NoSlotsAvailableException {

        TestPlayer testPlayer = new TestPlayer();
        Game testGame = new Game(19,testPlayer);
        testGame.addPlayer(new PlayerBot(testGame,19));
        testGame.setPawn(1,1,testPlayer);

    }

}