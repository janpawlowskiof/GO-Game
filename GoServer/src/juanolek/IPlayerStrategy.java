package juanolek;

public interface IPlayerStrategy {
    void handleMessage(Message message, Player sender);
    void forceQuit(Player sender);
}
