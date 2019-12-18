package juanolek;

import juanolek.player.Player;

public interface IPlayerStrategy {
    void handleMessage(Message message, Player sender);
    void forceQuit(Player sender);
}
