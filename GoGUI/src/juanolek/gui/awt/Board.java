package juanolek.gui.awt;

import juanolek.client.Message;
import juanolek.gui.GuiManager;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static juanolek.gui.awt.Board.*;


class MyPanel extends JPanel {


    //TODO: silnie nalegam żeby to nie była osobna klasa skoro nie musi
    public MyPanel(){

        setBackground(Color.ORANGE);
        setLayout( new GridLayout(N_OF_TILES,N_OF_TILES));

        //TODO: Niech rysowanie będzie przerzucone do osobnych metod (patrz todo: nieżej)
        // a llistener niech wywoła metodę pokroju "onTileSelected"
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                int row = Math.round((float) (e.getY() - BORDER_SIZE)
                        / TILE_SIZE);
                int col = Math.round((float) (e.getX() - BORDER_SIZE)
                        / TILE_SIZE);

                System.out.println(e.getX() +" "+e.getY());


                Graphics g = getGraphics();
                g.setColor(Color.BLACK);
                g.fillOval(col*TILE_SIZE+BORDER_SIZE-CONSTANT,row*TILE_SIZE+BORDER_SIZE-CONSTANT,30,30);


            }
        });
        repaint();
    }




    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        // Draw rows.
        for (int i = 0; i < SIZE; i++) {
            g2.drawLine(BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE, TILE_SIZE
                    * N_OF_TILES + BORDER_SIZE, i * TILE_SIZE + BORDER_SIZE);
        }
        // Draw columns.
        for (int i = 0; i < SIZE; i++) {
            g2.drawLine(i * TILE_SIZE + BORDER_SIZE, BORDER_SIZE, i * TILE_SIZE
                    + BORDER_SIZE, TILE_SIZE * N_OF_TILES + BORDER_SIZE);
        }



    }



}

//TODO: jak już nie będzie klasy u góry to refactor->rename->AwtBoard
//TODO: dorzuć metody:
// setWhitePawn(int x, int y)
// setBlackPawn(int x, int y)
// onTileSelected(int x, int y) patrz todo wyżej
public class Board extends ReceiverFrame{

    public final static int SIZE = 19;
    public static final int N_OF_TILES = SIZE - 1;
    public static final int TILE_SIZE = 40;
    public static final int BORDER_SIZE = TILE_SIZE;
    public static final int CONSTANT =15;
    GuiManager guiManager = null;


    public Board(GuiManager guiManager){
        this.guiManager = guiManager;

        setSize(820,860);
        setLayout(new BorderLayout());
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel boardPanel = new MyPanel();
        JPanel buttonPanel = new JPanel();

        add(boardPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);
        boardPanel.setPreferredSize(new Dimension(800,800));




        JButton button = new JButton();
        buttonPanel.add(button);
        button.setText("Pass");
        pack();


    }


    @Override
    public void receive(Message message) {
        System.out.println("Board rec: " + message);
    }
}