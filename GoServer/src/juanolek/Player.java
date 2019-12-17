package juanolek;

import java.util.ArrayList;
import java.util.UUID;

public class Player implements IMessageReceiver{

    IConnectionManager connectionManager;
    private IPlayerStrategy playerStrategy;
    private String nick;
    private UUID uuid;
    private static final ArrayList<String> usedNicks;
    static{
        usedNicks = new ArrayList<>();
    }

    public Player(IConnectionManager connectionManager){
        this.connectionManager = connectionManager;
        this.uuid = UUID.randomUUID();
        setNick();
    }

    public UUID getUuid(){
        return uuid;
    }
    public String getNick(){
        return nick;
    }
    private void setNick() {
        usedNicks.remove(this.nick);
        if(!usedNicks.contains("GenericNick")){
            this.nick = "GenericNick";
        }
        else{
            int i = 0;
            while(usedNicks.contains("GenericNick" +i)){
                i++;
            }
            this.nick = "GenericNick" +i;
        }
        usedNicks.add(this.nick);
        System.out.println("Your nick has been set to " + "GenericNick");
    }

    public void startReceivingMessages(){
        this.connectionManager.startListening(this);
    }
    public void sendMessage(Message message){
        connectionManager.sendMessage(message);
    }

    public void setPlayerStrategy(IPlayerStrategy playerStrategy){
        this.playerStrategy = playerStrategy;
    }

    @Override
    public void receive(Message message) {
        if(playerStrategy == null){
            System.out.println("Brak strategii dla gracza");
            return;
        }
        if(message == null){
            playerStrategy.forceQuit(this);
            try{
                connectionManager.disconnect();
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
        else{
            playerStrategy.handleMessage(message, this);
        }
    }
}
