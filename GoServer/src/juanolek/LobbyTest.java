package juanolek;

import static org.junit.jupiter.api.Assertions.*;

class LobbyTest {

    private class PlayerMock extends Player{
        public PlayerMock() {
            super(null);
        }
        @Override
        public void startReceivingMessages(){}
        public void sendMessage(Message message){}
    }

    @org.junit.jupiter.api.Test
    void testAddPlayer() {
        Player testPlayer = new PlayerMock();
        assertFalse(Lobby.getInstance().getLobbyPlayers().contains(testPlayer));
        Lobby.getInstance().addPlayer(testPlayer);
        assertTrue(Lobby.getInstance().getLobbyPlayers().contains(testPlayer));
    }

    @org.junit.jupiter.api.Test
    void testCreateGame() {
        Player testPlayer = new PlayerMock();
        int gamesCount = Lobby.getInstance().getGames().size();
        Lobby.getInstance().createGame(testPlayer);
        assertTrue(Lobby.getInstance().getGames().size() > gamesCount);
        assertFalse(Lobby.getInstance().getLobbyPlayers().contains(testPlayer));
    }

    @org.junit.jupiter.api.Test
    void testRemovePlayer() {
        Player testPlayer = new PlayerMock();
        assertFalse(Lobby.getInstance().getLobbyPlayers().contains(testPlayer));
        Lobby.getInstance().addPlayer(testPlayer);
        assertTrue(Lobby.getInstance().getLobbyPlayers().contains(testPlayer));
        Lobby.getInstance().removePlayer(testPlayer);
        assertFalse(Lobby.getInstance().getLobbyPlayers().contains(testPlayer));
    }
}