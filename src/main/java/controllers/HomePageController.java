package main.java.controllers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;


import static javafx.geometry.NodeOrientation.LEFT_TO_RIGHT;
import static javafx.geometry.NodeOrientation.RIGHT_TO_LEFT;


public class HomePageController {
    private File file;

    @FXML
    private MenuItem newfilemenu;

    @FXML
    private MenuItem openfilemenu;

    @FXML
    private MenuItem savemenu;

    @FXML
    private MenuItem closemenu;

    ///very important note about text area
    //in order to make it responsive make 3 things happen
    //1- make the original pane (border pane) or whatever for min set manually
    //2- set the min of the text area manually as well
    //3-set the min of primary stage as well
    //**you could wrap the text or not but other things word well this way
    @FXML
    private TextArea textarea;


    @FXML
    private CheckMenuItem txtwrapmenu;

    public void initialize(){
        //adding shortcuts
        //I could do this in fxml as well but at the time of creating this i wanted to
        //learn how to do it from here
        newfilemenu.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        savemenu.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        closemenu.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));
        incfontsizemenu.setAccelerator(new KeyCodeCombination(KeyCode.EQUALS , KeyCombination.CONTROL_DOWN));
        decfontsizemenu.setAccelerator(new KeyCodeCombination(KeyCode.MINUS , KeyCombination.CONTROL_DOWN));
        openfilemenu.setAccelerator(new KeyCodeCombination(KeyCode.O , KeyCombination.CONTROL_DOWN));

    }

    @FXML
    private MenuItem incfontsizemenu;


    @FXML
    private MenuItem decfontsizemenu;

    @FXML
    void decreasefontsize(ActionEvent event) {
        double currentsize = textarea.getFont().getSize();

        textarea.setFont(Font.font(currentsize-2));
    }

    @FXML
    void increasefontsize(ActionEvent event) {
        double currentsize = textarea.getFont().getSize();
        textarea.setFont(Font.font(currentsize+2));
    }

    @FXML
    void setfonttoarial(ActionEvent event) {
        //change the font to arial
        textarea.setFont(Font.font("Arial",textarea.getFont().getSize()));
    }

    @FXML
    void setfonttocouriernew(ActionEvent event) {
        textarea.setFont(Font.font("Courier New",textarea.getFont().getSize()));
    }

    @FXML
    void setfonttodefault(ActionEvent event) {

        textarea.setFont(Font.font("System",textarea.getFont().getSize()));
    }

    @FXML
    void setfonttotimesnewroman(ActionEvent event) {
        textarea.setFont(Font.font("Times New Roman",textarea.getFont().getSize()));
    }
    @FXML
    void setdirectionlefttoright(ActionEvent event) {
        //set text direction to left to right
        textarea.setNodeOrientation(LEFT_TO_RIGHT);
    }

    @FXML
    void setdirectionrighttoleft(ActionEvent event) {

        textarea.setNodeOrientation(RIGHT_TO_LEFT);
    }
    @FXML
    void toggletextwrap(ActionEvent event) {
        textarea.setWrapText(txtwrapmenu.isSelected());
    }

    @FXML
    void closethewindow(ActionEvent event) {
        // get the stage so we can close the window
        Stage stage = (Stage) textarea.getScene().getWindow();
        // close the stage
        stage.close();
    }

    @FXML
    void showaboutusinfo(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About us");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        //to clear all the buttons and the functionality of close buton you should do this
        //but for now we don't need that.
        //alert.getButtonTypes().clear();
        alert.setContentText("write about yourself here!");
        alert.showAndWait();
    }

    @FXML
    void savefiletotext(ActionEvent event) {

        //create a new file chooser
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for our file chooser
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        //Show save file dialog
        //first get the scene so we could show a file chooser dialogue on the stage
        Stage stage = (Stage) textarea.getScene().getWindow();
        if(file == null){
            file = fileChooser.showSaveDialog(stage);
            //if there is a file selected continue and process the saving
            if(file != null){
                //since \n in text file is not considered as new line here replace it with
                //system's seperator which i'm not sure but i guess is \r\n
                String textfile = textarea.getText().replaceAll("\n", System.getProperty("line.separator"));
                SaveFile(textfile, file);
            }

        }else {
            String textfile = textarea.getText().replaceAll("\n", System.getProperty("line.separator"));
            SaveFile(textfile, file);
        }

    }

    @FXML
    void openfile(ActionEvent event) {
        //create a new file chooser
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for our file chooser
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        //Show save file dialog
        //first get the scene so we could show a file chooser dialogue on the stage
        Stage stage = (Stage) textarea.getScene().getWindow();
        file = null;
        file = fileChooser.showOpenDialog(stage);
        //if there is a file selected continue and process the saving
        if(file != null){
            //since \n in text file is not considered as new line here replace it with
            //system's seperator which i'm not sure but i guess is \r\n
            try{
                //first clean the textarea
                textarea.setText("");
                //without lambda expression it would look like this
                //Files.lines(file.toPath()).forEach(System.out::println );
                //with lambda expression it's clean and looks like this
                Files.lines(file.toPath()).forEach(text -> textarea.appendText(text + "\n"));
            }catch (IOException ex){

            }
        }

    }


    //Save file method
    private void SaveFile(String content, File file){
        try {
            //use file writer to write Content to file
            //content is a text (String) and file is a new file with file chooser
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
        }

    }

    @FXML
    void startnewfile(ActionEvent event) {
        //here we only clean the textarea
        textarea.setText("");
        file = null;
    }

}
