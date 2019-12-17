package juanolek.gui.awt;

import juanolek.client.Message;
import juanolek.gui.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AwtLobby extends ReceiverFrame {

    private final List players = new List();
    private final List games = new List();
    private final ArrayList<String> gamesUUID = new ArrayList();

    public AwtLobby(GuiManager guiManager){

        this.setSize(800,800);
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
                guiManager.sendMessage(new Message("JoinGame", gamesUUID.get(games.getSelectedIndex())));
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
        listPanel.setLayout(new GridLayout(2,2));
        listPanel.add(new JLabel("Players in lobby", SwingConstants.CENTER));
        listPanel.add(new JLabel("List of games", SwingConstants.CENTER));
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
            gamesUUID.clear();

            for(String game : message.getValue().split(",")){
                String[] gameInfo = game.split(";");
                if(gameInfo.length < 2)
                    continue;
                gamesUUID.add(gameInfo[0]);
                games.add(gameInfo[1]);
            }
        }
        System.out.println("Lobby received message " + message.toString());
    }
}
