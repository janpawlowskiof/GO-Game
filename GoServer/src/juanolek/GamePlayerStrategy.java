package juanolek;

import juanolek.game.Game;

public class GamePlayerStrategy implements IPlayerStrategy {

    Game game;

    public GamePlayerStrategy(Game game){
        this.game = game;
    }

    @Override
    public void handleMessage(Message message, Player sender) {
        if(message.getHeader().equals("exit")){
            game.endSession();
        }
        else if(message.getHeader().equals("tileselected")){
            int x = Integer.parseInt(message.getValue().split(",")[0]);
            int y = Integer.parseInt(message.getValue().split(",")[1]);
            game.setPawn(x, y, sender);
        }
        else{
            System.out.println("Header " + message.getHeader() + "received in game but not implemented");
        }
    }
}
