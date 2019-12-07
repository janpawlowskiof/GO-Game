package juanolek;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ControllerBoard  {

    @FXML  private GridPane Board = new GridPane();
    private final int RectWidth = 26;
    private final int RectHeight = 26;
    private final int Radius = 8;
    double currX;
    double currY;

    private void drawCircle(int x,int y, Color color ) {



            Circle circle = new Circle(Radius);
            circle.setFill(color);

            Board.add(circle, x, y);
            Board.setHalignment(circle, HPos.CENTER);
            Board.setValignment(circle, VPos.CENTER);


    }

    @FXML
    private void Pressed(MouseEvent event){

         currX = (event.getX()/RectWidth);
         currY = (event.getY()/RectHeight);
         drawCircle((int)currX,(int)currY,Color.BLACK);
         fieldClicked((int)currX,(int)currY);
    }


    private void fieldClicked(int x, int y){

        System.out.println("Kliknieto na: "+ x+" "+y);

    }


}
