import juanolek.Lobby;
import juanolek.Message;
import juanolek.Player;

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
   public void testAddPlayer() {
        Player testPlayer = new PlayerMock();
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
        Lobby.getInstance().addPlayer(testPlayer);
        assertTrue(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
    }

    @org.junit.Test
   public void testCreateGame() {
        Player testPlayer = new PlayerMock();
        int gamesCount = Lobby.getInstance().getGames().size();
        Lobby.getInstance().createGame(testPlayer);
        assertTrue(Lobby.getInstance().getGames().size() > gamesCount);
        assertFalse(Lobby.getInstance().getLobbyPlayers().containsValue(testPlayer));
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
}