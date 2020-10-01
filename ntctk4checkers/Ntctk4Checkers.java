/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntctk4checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ntctk4
 */
public class Ntctk4Checkers extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

        //this is the same as the example in class so I can manipulate the stage.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Board.fxml"));
        Parent root = loader.load();
        BoardController controller = loader.getController();

        
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();
        
        controller.ready(stage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
