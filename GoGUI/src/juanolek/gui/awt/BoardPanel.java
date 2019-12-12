package juanolek.gui.awt;

import juanolek.gui.GuiManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static juanolek.gui.awt.AwtBoard.*;

public class BoardPanel extends JPanel {


    AwtBoard board;

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

                board.onTileSelected(col, row);
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
