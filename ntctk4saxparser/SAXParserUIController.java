/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntctk4saxparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ntctk4
 */
public class SAXParserUIController implements Initializable {

    
    private Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    //opens the open file dialog so the user can choose an xml file to parse
    @FXML
    public void handleOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try
            {
                
                //uncomment this out to print out and check the xml file to see if the xml is parsed correctly
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile())); 
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                
                br.close();

                //make sure an xml file is being read in
                //if not send out an alert and 
                //have the user pick a different file
               String extension = "";
               int i = file.getPath().lastIndexOf('.');
               if(i > 0)
               {
                   extension = file.getPath().substring(i+1);
               }
               if(!extension.equals("xml"))
               {
                   String message = "Incorrect file extension Please open an xml file";
                   displayAlert(message);
               }
               else
               {
                   //get the parsed xml from the static parseXML method
                   XMLNode root = XmlSAXParser.parseXML(file);

               }
            //if there is an exception display an alert and print out the error
            }catch(Exception ex)
            {
                System.out.println(ex);
               String message = "Exception occurred while opening the file";
               displayAlert(message);
            }
        }
    }
    
    //display an error message if something went wrong
    //while opening the file
    private void displayAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
