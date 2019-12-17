package juanolek;

interface IPlayerStrategy {
    void handleMessage(Message message, Player sender);
    void forceQuit(Player sender);
}
