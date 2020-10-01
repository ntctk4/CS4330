/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntctk4moviereviews;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author ntctk4
 */


//used Dale M's controller as a template.
public class NYTMovieViewerController implements Initializable {

    private Stage stage;
    
    private String searchString = "Avengers";
    private WebEngine webEngine;
    ObservableList<String> newsListItems;
    
    private NYTMovieReviewManager movieReviewManager;
    ArrayList<NYTReview> reviews;
    
    @FXML
    private TextField searchTextField;
    
    @FXML
    private ListView newsListView;
    
    @FXML
    private WebView webView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void ready(Stage stage) {
        this.stage = stage;
        webEngine = webView.getEngine();
        movieReviewManager = new NYTMovieReviewManager();
        
        newsListItems = FXCollections.observableArrayList();
        newsListView.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
 
            
            if ((int)new_val < 0 || (int)new_val > (reviews.size() - 1)) {
                return;
            }
            NYTReview review = reviews.get((int)new_val);
            webEngine.load(review.webUrl);
        });
        
        // put initial search string in searchTextField and load news based
        // on that search
        searchTextField.setText(searchString);
        loadReview();
    }
     
    @FXML
    private void handleSearch(ActionEvent event) {
        //makes sure the search isn't blank
        if (searchTextField.getText().equals("")) {
            displayErrorAlert("Search is empty, please try again");
            return;
        }
        //is string is valid put it in searchString and
        //begin to load themovie reviews
        searchString = searchTextField.getText();
        loadReview();
    }
    
    //calls the movieReviewManager to send the query to 
    //the NYT so it can recieve the message and parse the json
    //message to display in the UI
    private void loadReview() {
        try {
        movieReviewManager.load(searchString);
        } catch(Exception ex) {
        displayExceptionAlert(ex);
        return;
    }
        
        reviews = movieReviewManager.getNewsStories();
        newsListItems.clear();
        
        for (NYTReview review : reviews) {
            newsListItems.add(review.display_title);
        }

        newsListView.setItems(newsListItems);
        if (reviews.size() > 0) {
            newsListView.getSelectionModel().select(0);
            newsListView.getFocusModel().focus(0);
            newsListView.scrollTo(0);
        }
    }
        
    //currently only being used if the search box is empty when
    //the search button is pushed, creates an alert window to display error
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    //used example exception alert from class so I can 
    //learn how to display nice aleart window 
    //and show what the exceptions are.
    private void displayExceptionAlert(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText("An Exception Occurred!");
        alert.setContentText("There was an exception! Click below for details");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        //put the excepion in the printWriter
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("exception stacktrace:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.showAndWait();
    }
}
