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
        private Stage primaryStage = new Stage();
        @Override
        public void start(Stage primaryStage) throws Exception{

            this.primaryStage=primaryStage;

            //here we have to add conditions to 'manage' windows which one should pop up now
            showBoard();
            showLoginWindow();
            showLobby();
        }

        //function that shows the board
        private void showBoard(){

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

        //function that shows Log in Window
        private void showLoginWindow() throws Exception {

            Parent root1 = FXMLLoader.load(getClass().getResource("/LoginWindow/LoginWindow.fxml"));
            primaryStage.setScene(new Scene(root1, 400, 300));
            primaryStage.show();
        }


        private void showLobby() throws Exception {

            Parent root1 = FXMLLoader.load(getClass().getResource("/Lobby/Lobby.fxml"));
            primaryStage.setScene(new Scene(root1, 600, 400));
            primaryStage.show();
        }




        public static void main(String[] args) {
            launch(args);
        }
    }
