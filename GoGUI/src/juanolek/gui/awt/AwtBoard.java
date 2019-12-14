package juanolek.gui.awt;
import juanolek.client.Message;
import juanolek.gui.GuiManager;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;


public class AwtBoard extends ReceiverFrame{

    public final static int SIZE = 19;
    public static final int N_OF_TILES = SIZE - 1;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;
    public static final int CONSTANT =15;
    GuiManager guiManager = null;
    BoardPanel boardPanel = new BoardPanel(this);


    public AwtBoard(GuiManager guiManager){
        this.guiManager = guiManager;

        setSize(820,860);
        setLayout(new BorderLayout());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4,1));
        buttonPanel.setPreferredSize(new Dimension(200,800));

        add(boardPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.EAST);

        JButton passButton = new JButton();
        JButton leaveButton = new JButton();
        JLabel score = new JLabel("Your Score:",SwingConstants.CENTER);
        JLabel scoreValue = new JLabel("",SwingConstants.CENTER);

        buttonPanel.add(score);
        score.setFont(new Font("Arial",Font.PLAIN,20));
        buttonPanel.add(scoreValue);
        scoreValue.setFont(new Font("Arial",Font.PLAIN,20));
        buttonPanel.add(passButton);
        passButton.setText("Pass");
        buttonPanel.add(leaveButton);
        leaveButton.setText("Leave");
        pack();
    }



    public synchronized void setBlackPawn(int x, int y){

        Pawn newBlackPawn = new Pawn(x,y,Color.BLACK);
        boardPanel.getPawns().add(newBlackPawn);
        repaint();
    }

    public synchronized void setWhitePawn(int x, int y){

        Pawn newWhitePawn = new Pawn(x,y,Color.WHITE);
        boardPanel.getPawns().add(newWhitePawn);
        repaint();
    }

    public synchronized void clearPawn(int x, int y){

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
        if(message.getHeader().equals("setwhitepawn")){
            int x = Integer.parseInt(message.getValue().split(",")[0]);
            int y = Integer.parseInt(message.getValue().split(",")[1]);
            setWhitePawn(x, y);
        }
        else if(message.getHeader().equals("setblackpawn")){
            int x = Integer.parseInt(message.getValue().split(",")[0]);
            int y = Integer.parseInt(message.getValue().split(",")[1]);
            setBlackPawn(x, y);
        }
        else if(message.getHeader().equals("deletepawn")){
            int x = Integer.parseInt(message.getValue().split(",")[0]);
            int y = Integer.parseInt(message.getValue().split(",")[1]);
            clearPawn(x, y);
        }
    }




}