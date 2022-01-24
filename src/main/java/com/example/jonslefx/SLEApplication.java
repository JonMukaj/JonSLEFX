package com.example.jonslefx;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SLEApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SLEApplication.class.getResource("jonSLE.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), 810.0D, 630.0D);

        primaryStage.getIcons().add(new Image(SLEApplication.class.getResourceAsStream("img/sigma.png")));
        primaryStage.setResizable(false);
        primaryStage.setTitle("SLE GENERATOR");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(new String[0]);
    }
    final Hyperlink link = new Hyperlink("https://github.com/JonMukaj/JonSLEFX");

    public void openLink() {
        getHostServices().showDocument(link.getText());
    }

    public SLEApplication() {
    }
}
