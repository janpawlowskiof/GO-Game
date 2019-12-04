package GoBoard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

    public class Main extends Application {

        private Group root = new Group();
        @Override
        public void start(Stage primaryStage) throws Exception{
            //Parent root1 = FXMLLoader.load(getClass().getResource("sample.fxml"));

            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root, 500, 500));

            for(int i=0 ; i < 18 ; i++){
                for(int j=0 ; j < 18 ; j++){

                    Rectangle rect = new Rectangle(25,25);
                    rect.relocate(i*25,j*25);
                    if((i+j)%2==0){rect.setFill(Color.BLACK);}
                    else if((i+j)%2==1) {rect.setFill(Color.WHITE);}
                    root.getChildren().add(rect);

                }


            }

            primaryStage.show();
        }


        public static void main(String[] args) {
            launch(args);
        }
    }
