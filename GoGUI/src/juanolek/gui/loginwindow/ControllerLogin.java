package juanolek.gui.loginwindow;

import javafx.fxml.FXML;
import juanolek.client.IMessageReceiver;
import juanolek.client.Message;
import juanolek.gui.GuiManager;

public class ControllerLogin implements IMessageReceiver {

    GuiManager guiManager = null;

    @FXML
    private void buttonPressed(){
        System.out.println("dziala");
        //TODO: port i ip z okienka
        guiManager.connect("localhost", 6666);
    }

    public void setGuiManager(GuiManager guiManager){
        this.guiManager = guiManager;
    }


    @Override
    public void receive(Message message) {
        System.out.println("Rec message " + message.toString());
    }
}