package juanolek;

import juanolek.exceptions.GameNotExistingException;
import juanolek.exceptions.NoSlotsAvailableException;
import juanolek.game.Game;
import org.junit.Test;

import static org.junit.Assert.*;

public class LobbyTest {


    private class PlayerMock extends Player {
        public PlayerMock() {
            super(null);
        }
        @Override
        public void startReceivingMessages(){}
        public void sendMessage(Message message){}
    }

    @org.junit.Test
   public void testAddPlayer() throws NoSlotsAvailableException, GameNotExistingException {
        Player testPlayer1 = new PlayerMock();
        Player testPlayer2 = new PlayerMock();

        //adding a player to lobby
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer1));
        Lobby.getInstance().addPlayer(testPlayer1);
        assertTrue(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer1));

        //adding a player to game
        Game testGame = new Game(19,testPlayer1);
        String uuid = testGame.getUuid().toString();
        Lobby.getInstance().getGames().put(testGame.getUuid(),testGame);
        Lobby.getInstance().addPlayerToGame(testPlayer2,uuid );


    }

    @org.junit.Test
   public void testCreateGame() {
        Player testPlayer = new PlayerMock();
        int gamesCount = Lobby.getInstance().getGames().size();
        Lobby.getInstance().createGame(testPlayer);
        assertTrue(Lobby.getInstance().getGames().size() > gamesCount);
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
    }

    @Test
    public void testRemoveGame(){

        Player testPlayer = new PlayerMock();
        Lobby.getInstance().removeGame(new Game(19,testPlayer));

    }

    @org.junit.Test
   public void testRemovePlayer() {
        Player testPlayer = new PlayerMock();
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
        Lobby.getInstance().addPlayer(testPlayer);
        assertTrue(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
        Lobby.getInstance().removePlayer(testPlayer);
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
    }

    @Test
    public void testGameWithBot(){

        Player testPlayer = new PlayerMock();
        Lobby.getInstance().createGameWithBot(testPlayer);

    }
}