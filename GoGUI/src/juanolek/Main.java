package juanolek;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main extends Application {

        private Group root = new Group();
        private Stage primaryStage = new Stage();
        @Override
        public void start(Stage primaryStage) throws Exception{

            this.primaryStage=primaryStage;

            //here we have to add conditions to 'manage' windows which one should pop up now
            showBoard();
            //showLoginWindow();
            //showLobby();
        }

        //function that shows the board
        private void showBoard() throws Exception {
            Parent root1 = FXMLLoader.load(getClass().getResource("/juanolek/Board.fxml"));
            primaryStage.setScene(new Scene(root1, 700, 500));
            primaryStage.setResizable(false);
            primaryStage.show();
        }

        //function that shows Log in Window
        private void showLoginWindow() throws Exception {
            Parent root1 = FXMLLoader.load(getClass().getResource("/juanolek/loginwindow/LoginWindow.fxml"));
            primaryStage.setScene(new Scene(root1, 400, 300));
            primaryStage.setResizable(false);
            primaryStage.show();
        }


        private void showLobby() throws Exception {

            Parent root1 = FXMLLoader.load(getClass().getResource("/juanolek/lobby/Lobby.fxml"));
            primaryStage.setScene(new Scene(root1, 600, 400));
            primaryStage.setResizable(false);
            primaryStage.show();
        }




        public static void main(String[] args) {
            launch(args);
        }
    }
