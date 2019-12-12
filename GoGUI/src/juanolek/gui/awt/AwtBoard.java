package juanolek.gui.awt;
import juanolek.client.Message;
import juanolek.gui.GuiManager;
import javax.swing.*;
import java.awt.*;


public class AwtBoard extends ReceiverFrame{

    public final static int SIZE = 19;
    public static final int N_OF_TILES = SIZE - 1;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;
    public static final int CONSTANT =15;
    GuiManager guiManager = null;


    public AwtBoard(GuiManager guiManager){
        this.guiManager = guiManager;

        setSize(820,860);
        setLayout(new BorderLayout());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel boardPanel = new BoardPanel(this);
        JPanel buttonPanel = new JPanel();



        add(boardPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);


        JButton button = new JButton();
        buttonPanel.add(button);
        button.setText("Pass");
        pack();


    }



    public void setBlackPawn(int x, int y){

        Graphics g = getGraphics();
        g.setColor(Color.BLACK);
        g.fillOval(x*TILE_SIZE+BORDER_SIZE-CONSTANT,y*TILE_SIZE+BORDER_SIZE-CONSTANT,30,30);


    }

    public void setWhitePawn(int x, int y){

        Graphics g = getGraphics();
        g.setColor(Color.WHITE);
        g.fillOval(x*TILE_SIZE+BORDER_SIZE-CONSTANT,y*TILE_SIZE+BORDER_SIZE-CONSTANT,30,30);


    }

    public void onTileSelected(int x, int y){
        System.out.println("Tile " + x + ", " + y + "selected");
    }


    @Override
    public void receive(Message message) {
        System.out.println("AwtBoard rec: " + message);
    }




}