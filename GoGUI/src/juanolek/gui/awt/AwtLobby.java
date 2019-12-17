package juanolek.gui.awt;

import juanolek.client.Message;
import juanolek.gui.GuiManager;

import javax.swing.*;
import java.awt.*;

public class AwtLobby extends ReceiverFrame {

    private final List players = new List();
        private final List games = new List();

        public AwtLobby(GuiManager guiManager){

            this.setSize(800,400);
            this.setVisible(true);
            this.setResizable(false);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);

            this.setLayout(new BorderLayout());
            JButton joinButton = new JButton();
            JButton refreshButton = new JButton();
            JButton createNewGameButton = new JButton("Create new game");
            JButton createNewGameWithBotButton = new JButton("Create game with bot");
            createNewGameButton.addActionListener(actionEvent -> {
                System.out.println("CreateNewGame clicked...");
                System.out.println(guiManager);
                guiManager.sendMessage(new Message("createGame", ""));
            });
            createNewGameWithBotButton.addActionListener(actionEvent -> {
                System.out.println("CreateNewGameWithBot clicked...");
                System.out.println(guiManager);
                guiManager.sendMessage(new Message("createGameWithBot", ""));
            });
            joinButton.setText("Join selected game");
            joinButton.addActionListener(actionEvent -> {
                System.out.println("Join Game clicked...");
                if(games.getSelectedItem() != null)
                    guiManager.sendMessage(new Message("JoinGame", games.getSelectedItem()));
            });
            refreshButton.setText("Refresh");
            refreshButton.addActionListener(actionEvent -> {
                System.out.println("Refresh clicked...");
                guiManager.sendMessage(new Message("getLobbyPlayers", ""));
                guiManager.sendMessage(new Message("getGames", ""));
            });

            JPanel buttonPanel = new JPanel();
            this.add(buttonPanel,BorderLayout.SOUTH);
            JPanel listPanel = new JPanel();
            this.add(listPanel,BorderLayout.CENTER);
            listPanel.setLayout(new GridLayout(1,2));
            listPanel.add(players);
            listPanel.add(games);
            buttonPanel.add(joinButton);
            buttonPanel.add(refreshButton);
            buttonPanel.add(createNewGameButton);
            buttonPanel.add(createNewGameWithBotButton);

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
