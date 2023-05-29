package ru.smackmbu.mynotepad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NotepadApplication extends Application {
    public static Stage myStage;

    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(NotepadApplication.class.getResource("notepad-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);
        myStage.setTitle("MyNotepad");
        myStage.setScene(scene);
        myStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}