package ru.smackmbu.mynotepad;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class NotepadController implements Initializable {
    @FXML
    private MenuItem aboutButton;

    File file = null;

    @FXML
    private MenuItem closeButton;

    @FXML
    private MenuItem newButton;

    @FXML
    private MenuItem openButton;

    @FXML
    private MenuItem quitButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private MenuItem saveAsButton;


    @FXML
    private TextArea textArea;

    final KeyCombination cs = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    final KeyCombination cn = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    final KeyCombination co = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
    final KeyCombination css = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        openButton.setOnAction(actionEvent -> openButton());

        saveButton.setOnAction(actionEvent -> saveButton());

        saveAsButton.setOnAction(actionEvent -> saveAsButton());

        closeButton.setOnAction(actionEvent -> closeButton());

        newButton.setOnAction(actionEvent -> newButton());

        quitButton.setOnAction(actionEvent -> quitButton());

        textArea.addEventFilter(KeyEvent.KEY_PRESSED, handler -> {
            if(cs.match(handler)){
                saveButton();
            }else if(cn.match(handler)){
                newButton();
            }else if(co.match(handler)){
                openButton();
            }else if(css.match(handler)){
                saveAsButton();
            }
        });

        aboutButton.setOnAction(actionEvent -> about());
    }

    public void newButton(){
        file = saveAsFile();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void quitButton(){
        if(!readFile(file).equals(textArea.getText())){
            quit();
        }else{ NotepadApplication.myStage.close(); }
    }
    public void openButton(){
        file = openFile();
        if(file != null){textArea.setText(readFile(file));}
    }

    public void saveButton(){
        if(file == null){ file = saveAsFile(); }
        if(file != null) { writeFile(file, textArea.getText()); }
    }

    public void saveAsButton(){
        File f = saveAsFile();
        if(file == null){
            file = f;
        }
        if(f != null) {
            writeFile(f, textArea.getText());
            NotepadApplication.myStage.setTitle(file.getPath());
        }
    }

    public void closeButton(){
        file = null;
        textArea.setText("");
        NotepadApplication.myStage.setTitle("MyNotepad");
    }

    public File openFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File f = fileChooser.showOpenDialog(NotepadApplication.myStage);
        if(f != null){
            NotepadApplication.myStage.setTitle(f.getPath());
        }
        return f;
    }

    public File saveAsFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Saving");
        File f = fileChooser.showSaveDialog(NotepadApplication.myStage);
        if(f != null){
            NotepadApplication.myStage.setTitle(f.getPath());
        }
        return f;
    }


    public void writeFile(File file, String text){
        if(file != null) {
            try (FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buffer = text.getBytes();
                fos.write(buffer, 0, buffer.length);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String readFile(File file){
        StringBuilder text = new StringBuilder();
        if(file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                int i;
                while ((i = fis.read()) != -1) {
                    text.append((char) i);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return text.toString();
    }
    public void quit(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("File not saved");
        alert.setHeaderText("File not saved");
        ButtonType buttonTypeOne = new ButtonType("Save");
        ButtonType buttonTypeTwo = new ButtonType("Don't Save");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()) {
            if (result.get() == buttonTypeOne) {
                if (file == null) {
                    file = saveAsFile();
                }
                writeFile(file, textArea.getText());
                NotepadApplication.myStage.close();
            } else if (result.get() == buttonTypeTwo) {
                NotepadApplication.myStage.close();
            }
        }
    }
    public void about(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About MyNotepad");
        alert.setHeaderText("Simple notepad by SmackMBU");
        alert.show();
    }
}