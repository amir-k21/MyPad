package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/HomePage.fxml"));
        primaryStage.setTitle("MyPad");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(220);
        primaryStage.setMinWidth(200);
        //set the icon
        Image icon = new Image(getClass().getResourceAsStream("/main/resources/images/Icon.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
