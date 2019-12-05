package juanolek;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ControllerBoard  {

    @FXML  private GridPane Board = new GridPane();
    private final int RectWidth = 26;
    private final int RectHeight = 26;
    private final int Radius = 10;
    int counter = 0;

    private void drawCircle(int x,int y) {


        if(counter%2 ==0) {
            Circle circle = new Circle(Radius);
            circle.setFill(Color.BLACK);

            Board.add(circle, x, y);
        }
        else{

            Circle circle = new Circle(Radius);
            circle.setFill(Color.RED);

            Board.add(circle, x, y);

        }
        counter++;
    }

    @FXML
    private void Pressed(MouseEvent event){
         double currX;
         double currY;

         currX = (event.getX()/RectWidth);
         currY = (event.getY()/RectHeight);

         drawCircle((int)currX,(int)currY);

    }


}
