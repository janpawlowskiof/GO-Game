package juanolek;

import juanolek.game.Game;
import juanolek.game.GamePawnType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PlayerBot extends Player {

    private GamePawnType[][] tiles;
    Game game;
    int size;
//    private IMessageReceiver messageReceiver;

    public PlayerBot(Game game, int size) {
        super(null);
        this.game = game;
        this.size = size;
        System.out.println("Creating bot");

        connectionManager = new LazyConnectionManager();
        tiles = new GamePawnType[size][size];

        System.out.println("Bot created");

        for(int x = 0; x < size; x++)
            for(int y = 0; y < size; y++){
                tiles[x][y] = GamePawnType.Empty;
            }
    }

    Random generator = new Random();
    int searchX = 0;
    int searchY = 0;
    public void makeMove(int i){
        System.out.println("Bot moves now");
        int x = 0, y = 0;
        if(i < 1){
            searchX = 0;
            searchY = 0;
            x = generator.nextInt(size);
            y = generator.nextInt(size);
        }
        else{
            game.pass(this);
            return;
        }
        game.setPawn(x, y, this);
    }

    int i = 0;

    @Override
    public void sendMessage(Message message) {
        System.out.println("Bot received message " + message.toString());

        if(message.getHeader().equals("setwhitepawn")){
            int x = Integer.parseInt(message.getValue().split(",")[0]);
            int y = Integer.parseInt(message.getValue().split(",")[1]);
            tiles[x][y] = GamePawnType.White;
        }
        else if(message.getHeader().equals("setblackpawn")){
            int x = Integer.parseInt(message.getValue().split(",")[0]);
            int y = Integer.parseInt(message.getValue().split(",")[1]);
            tiles[x][y] = GamePawnType.Black;
        }
        else if(message.getHeader().equals("deletepawn")){
            int x = Integer.parseInt(message.getValue().split(",")[0]);
            int y = Integer.parseInt(message.getValue().split(",")[1]);
            tiles[x][y] = GamePawnType.Empty;
        }
        else if(message.getHeader().equals("yourturn")){
            System.out.println("its bots turn");
            i = 0;
            makeMove(i);
        }
        else if(message.getHeader().equals("invalidmoveinfo")){
            i++;
            makeMove(i);
        }
    }

    public class LazyConnectionManager implements IConnectionManager{
        @Override
        public void disconnect() throws IOException { }
        @Override
        public void sendMessage(Message message) { }
        @Override
        public void startListening(IMessageReceiver receiver) {
        }
    }
}
