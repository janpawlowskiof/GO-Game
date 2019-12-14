package juanolek;

public interface IPlayerStrategy {
    public void handleMessage(Message message, Player sender);
    public void forceQuit(Player sender);
}
