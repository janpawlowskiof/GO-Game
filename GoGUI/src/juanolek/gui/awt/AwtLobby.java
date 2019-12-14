package juanolek.gui.awt;

import juanolek.client.Message;
import juanolek.gui.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO: (mało pilne) dorzucić drugą listę na listę gier dostępnych
// też mało pilnie przemianować zmienne pokroju buttonPanel na coś innego
public class AwtLobby extends ReceiverFrame {

        JPanel buttonPanel = new JPanel();
        JPanel listPanel = new JPanel();
        GuiManager guiManager;
        List players = new List();
        List games = new List();

        public AwtLobby(GuiManager guiManager){
            this.guiManager = guiManager;

            this.setSize(800,400);
            this.setVisible(true);
            this.setResizable(false);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);

            this.setLayout(new BorderLayout());
            JButton joinButton = new JButton();
            JButton refreshButton = new JButton();
            JButton createNewGameButton = new JButton();
            createNewGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("CreateNewGame clicked...");
                    System.out.println(guiManager);
                    guiManager.sendMessage(new Message("createGame", ""));
                }
            });
            joinButton.setText("Join");
            joinButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Join Game clicked...");
                    if(games.getSelectedItem() != null)
                        guiManager.sendMessage(new Message("JoinGame", games.getSelectedItem()));
                }
            });
            refreshButton.setText("Refresh");
            refreshButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.out.println("Refresh clicked...");
                    guiManager.sendMessage(new Message("getLobbyPlayers", ""));
                    guiManager.sendMessage(new Message("getGames", ""));
                }
            });
            createNewGameButton.setText("New Game");

            this.add(buttonPanel,BorderLayout.SOUTH);
            this.add(listPanel,BorderLayout.CENTER);
            //this.add(button,BorderLayout.SOUTH);
            listPanel.setLayout(new GridLayout(1,2));
            listPanel.add(players);
            listPanel.add(games);
            buttonPanel.add(joinButton);
            buttonPanel.add(refreshButton);
            buttonPanel.add(createNewGameButton);

            players.setFont(new Font("TimesRoman",Font.PLAIN,16));
            games.setFont(new Font("TimesRoman",Font.PLAIN,16));
        }

    @Override
    public void receive(Message message) {
            if(message.getHeader().equals("lobbyplayers")){
                players.removeAll();

                for(String player : message.getValue().split(","))
                players.add(player);
            } else if(message.getHeader().equals("games")){
                games.removeAll();

                for(String game : message.getValue().split(","))
                    games.add(game);
            }

        System.out.println("Lobby received message " + message.toString());
    }
}
