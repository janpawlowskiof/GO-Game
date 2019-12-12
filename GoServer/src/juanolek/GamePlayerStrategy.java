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
        else{
            System.out.println("Header " + message.getHeader() + "received in game but not implemented");
        }
    }
}
