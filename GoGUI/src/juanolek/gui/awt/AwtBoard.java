package juanolek.gui.awt;
import juanolek.client.Message;
import juanolek.gui.GuiManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class AwtBoard extends ReceiverFrame{

    public final static int SIZE = 19;
    public static final int N_OF_TILES = SIZE - 1;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;
    public static final int CONSTANT =15;
    private GuiManager guiManager;
    private final BoardPanel boardPanel = new BoardPanel(this);
    private final JLabel opponentInfo;
    private final JLabel colorInfo;
    private final JLabel yourScore;
    private final JLabel opponentsScore;

    public AwtBoard(GuiManager guiManager){
        this.guiManager = guiManager;

        setSize(820,860);
        setLayout(new BorderLayout());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6,1));
        buttonPanel.setPreferredSize(new Dimension(200,800));

        add(boardPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.EAST);

        JButton passButton = new JButton();
        JButton leaveButton = new JButton();
        opponentInfo = new JLabel("<html>Waiting for<br> an opponent to join</html>",SwingConstants.CENTER);
        colorInfo = new JLabel("",SwingConstants.CENTER);

        buttonPanel.add(opponentInfo);
        opponentInfo.setFont(new Font("Arial",Font.PLAIN,14));
        buttonPanel.add(colorInfo);
        colorInfo.setFont(new Font("Arial",Font.PLAIN,14));

        buttonPanel.add(passButton);
        passButton.setText("Pass");
        passButton.addActionListener(actionEvent -> guiManager.sendMessage(new Message("pass", "")));
        buttonPanel.add(leaveButton);
        leaveButton.setText("Abort the game");
        leaveButton.addActionListener(actionEvent -> guiManager.sendMessage(new Message("abortgame", "")));
        yourScore = new JLabel("Your Score: 0");
        buttonPanel.add(yourScore);
        yourScore.setFont(new Font("Arial",Font.PLAIN,14));
        opponentsScore = new JLabel("Opponent's Score: 0");
        buttonPanel.add(opponentsScore);
        opponentsScore.setFont(new Font("Arial",Font.PLAIN,14));

        pack();
    }



    private synchronized void setBlackPawn(int x, int y){

        Pawn newBlackPawn = new Pawn(x,y,Color.BLACK);
        boardPanel.getPawns().add(newBlackPawn);
        repaint();
    }

    private synchronized void setWhitePawn(int x, int y){

        Pawn newWhitePawn = new Pawn(x,y,Color.WHITE);
        boardPanel.getPawns().add(newWhitePawn);
        repaint();
    }

    private synchronized void clearPawn(int x, int y){

        for (Iterator<Pawn> iterator = boardPanel.getPawns().iterator(); iterator.hasNext();) {
            Pawn center = iterator.next();
            if (center.getX()==x && center.getY()==y) {
                iterator.remove();
                break;
            }
        }
        repaint();
    }

    public void onTileSelected(int x, int y){
        guiManager.sendMessage(new Message("tileselected", x+","+y));
    }


    @Override
    public void receive(Message message) {
        switch (message.getHeader()) {
            case "setwhitepawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                setWhitePawn(x, y);
                break;
            }
            case "setblackpawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                setBlackPawn(x, y);
                break;
            }
            case "deletepawn": {
                int x = Integer.parseInt(message.getValue().split(",")[0]);
                int y = Integer.parseInt(message.getValue().split(",")[1]);
                clearPawn(x, y);
                break;
            }
            case "opponentinfo":
                opponentInfo.setText("<html>You are playing vs <br>" + message.getValue() + "</html>");
                break;
            case "colorinfo":
                colorInfo.setText("<html>You are playing as <br>" + message.getValue() + "</html>");
                break;
            case "yourscore":
                yourScore.setText("Your score: " + message.getValue());
                break;
            case "opponentsscore":
                opponentsScore.setText("Opponent's score: " + message.getValue());
                break;
        }
    }




}