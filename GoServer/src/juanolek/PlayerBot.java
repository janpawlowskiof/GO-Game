package juanolek;

import juanolek.game.Game;
import juanolek.game.GamePawnType;

import java.util.Random;

public class PlayerBot extends Player {

    private final GamePawnType[][] tiles;
    private final Game game;
    private final int size;
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

    private final Random generator = new Random();

    private void makeMove(int i){
        System.out.println("Bot moves now");
        int x, y;
        if(i < 1){
            int searchX = 0;
            int searchY = 0;
            x = generator.nextInt(size);
            y = generator.nextInt(size);
        }
        else{
            game.pass(this);
            return;
        }
        game.setPawn(x, y, this);
    }

    private int i = 0;

    @Override
    public void sendMessage(Message message) {
        System.out.println("Bot received message " + message.toString());

        switch (message.getHeader()) {
            case "setwhitepawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                tiles[x][y] = GamePawnType.White;
                break;
            }
            case "setblackpawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                tiles[x][y] = GamePawnType.Black;
                break;
            }
            case "deletepawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                tiles[x][y] = GamePawnType.Empty;
                break;
            }
            case "yourturn":
                System.out.println("its bots turn");
                i = 0;
                makeMove(i);
                break;
            case "invalidmoveinfo":
                i++;
                makeMove(i);
                break;
        }
    }

    public static class LazyConnectionManager implements IConnectionManager{
        @Override
        public void disconnect() { }
        @Override
        public void sendMessage(Message message) { }
        @Override
        public void startListening(IMessageReceiver receiver) {
        }
    }
}
