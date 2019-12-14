package juanolek.gui.awt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import static juanolek.gui.awt.AwtBoard.*;


public class BoardPanel extends JPanel {


    private AwtBoard board;
    private List<Pawn> pawns = new ArrayList<>();

    public BoardPanel(AwtBoard board) {
        this.board = board;

        setPreferredSize(new Dimension(800,800));
        setBackground(Color.ORANGE);
        setLayout( new GridLayout(N_OF_TILES,N_OF_TILES));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                int row = Math.round((float) (e.getY() - BORDER_SIZE)
                        / TILE_SIZE);
                int col = Math.round((float) (e.getX() - BORDER_SIZE)
                        / TILE_SIZE);

                if(checkPoint(col,row)) {
                    board.onTileSelected(col, row);
                }
                else System.out.println("nie wolno");
            }
        });
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

        for(Pawn p : pawns){
            if(p.getColor() == Color.BLACK){
                g2.setColor(Color.BLACK);
                g2.fillOval(p.getX()*TILE_SIZE+BORDER_SIZE-CONSTANT,p.getY()*TILE_SIZE+BORDER_SIZE-CONSTANT,30,30);
            }
            else if(p.getColor() == Color.WHITE){
                g2.setColor(Color.WHITE);
                g2.fillOval(p.getX()*TILE_SIZE+BORDER_SIZE-CONSTANT,p.getY()*TILE_SIZE+BORDER_SIZE-CONSTANT,30,30);
            }
        }
    }

    private boolean checkPoint(int x, int y){

        boolean position = true;
        if(x>SIZE-1 || y>SIZE-1) position = false;
        if(x<0 || y<0) position = false;


        return position;
    }

    public List<Pawn> getPawns() {
        return pawns;
    }
}
