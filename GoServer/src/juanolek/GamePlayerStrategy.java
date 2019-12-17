package juanolek;

import juanolek.game.Game;

public class GamePlayerStrategy implements IPlayerStrategy {

    private final Game game;

    public GamePlayerStrategy(Game game){
        this.game = game;
    }

    @Override
    public void handleMessage(Message message, Player sender) {
        switch (message.getHeader()) {
            case "abortgame":
                game.endSession(true);
                break;
            case "pass":
                game.pass(sender);
                break;
            case "tileselected":
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                game.setPawn(x, y, sender);
                break;
            default:
                System.out.println("Header " + message.getHeader() + " received in game but not implemented");
                break;
        }
    }

    @Override
    public void forceQuit(Player sender) {
        game.kickPlayer(sender);
    }
}
