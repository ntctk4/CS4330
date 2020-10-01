/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntctk4checkers;

import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author ntctk4
 */
public class BoardController implements Initializable {
    
    @FXML
    private AnchorPane anchor;
    
    @FXML
    private MenuBar menuBar;
    
    @FXML
    private VBox vBox;
    
    //fields to hold the number of rows and cols
    //initialized to -1
    private int numRows = -1;
    private int numCols = -1;
    
    //fields to calculate the deminsions of the board/rects
    private double boardWidth;
    private double boardHeight;
    private double top;
    
    //field to determine the color to be
    //used when the board is being rebuild
    private String color;
    
    private Stage stage;
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    //called after everything is set up in the start method
    //to initialize the board
    public void ready(Stage stage) {
        
        
        this.stage = stage;
        
        //anonymous inner class to listen when the stage changes in size so that the 
        //board can grow/shrink accordingly
        ChangeListener<Number> listener = new ChangeListener<Number>() {
         @Override 
         public void changed(ObservableValue<? extends Number> observable, Number oldValue, final Number newValue) {
              render();
         } 
         };
   
        //set the colors to the default red/black
        color = "default";
        //put listeners on the stage for height and width so the
        //board's size can dynamically grow.
        stage.widthProperty().addListener(listener);
        stage.heightProperty().addListener(listener);
        //used in calculations later so that the bottom rectangles don't
        //get cut off. For some resson calculating everytime render is called 
        //doesn't work well.
        top = (stage.getHeight()- vBox.getHeight()) + menuBar.getHeight();

        render();
    }
        
    protected void render()
    {
        boardWidth = stage.getWidth();// - menuBar.getWidth();
        boardHeight = stage.getHeight() - this.top;

        //the default board is an 8x8
        if(numRows == -1 || numCols == -1)
        {
            numRows = 8;
            numCols = 8;
        }
        
        //if blue is the color create a board with sky blue and dark blue, other wise create a red and black board and put it in the vBox.
        vBox.getChildren().remove(anchor);
        if(color.equals("blue"))
        {
            CheckerBoard checkerBoard = new CheckerBoard(boardWidth, boardHeight, numRows, numCols, Color.SKYBLUE, Color.DARKBLUE);
            checkerBoard.build();
            anchor = checkerBoard.getBoard();
            vBox.getChildren().add(anchor);
            VBox.setVgrow(checkerBoard.getBoard(), Priority.ALWAYS);
        }
        else
        {
            CheckerBoard checkerBoard = new CheckerBoard(boardWidth, boardHeight, numCols, numRows);
            checkerBoard.build();
            anchor = checkerBoard.getBoard();
             vBox.getChildren().add(anchor);
            VBox.setVgrow(checkerBoard.getBoard(), Priority.ALWAYS);
        }
    }
    //grabs the id from the demension button pushed (3x3, 8x8, 10,10, 16x16) and creates a new board with those demesions of rectangles
    //with the same color pattern as the last board.
    @FXML
    private void hadleDimensions(ActionEvent event){
        
        String sourceId = event.getSource().toString();
        sourceId = sourceId.substring(sourceId.indexOf('=')+1, sourceId.indexOf(','));
        
        boardWidth = vBox.getWidth();
        boardHeight = vBox.getHeight() - menuBar.getHeight();
        
        numRows = Integer.parseInt(sourceId);
        numCols = Integer.parseInt(sourceId);
        
        vBox.getChildren().remove(anchor);
        
        if(color.equals("blue"))
        {
            CheckerBoard checkerBoard = new CheckerBoard(boardWidth, boardHeight, numRows, numCols, Color.SKYBLUE, Color.DARKBLUE);
            checkerBoard.build();
            anchor = checkerBoard.getBoard();
            vBox.getChildren().add(anchor);
            VBox.setVgrow(checkerBoard.getBoard(), Priority.ALWAYS);
        }
        else
        {
            CheckerBoard checkerBoard = new CheckerBoard(boardWidth, boardHeight, numCols, numRows);
            checkerBoard.build();
            anchor = checkerBoard.getBoard();
             vBox.getChildren().add(anchor);
            VBox.setVgrow(checkerBoard.getBoard(), Priority.ALWAYS);
        }
    }
    
    //when the default button is hit remove the board and build one with the same dimensions with the colors red and black.
    @FXML
    private void handleDefaultColors(ActionEvent event)
    {
        boardWidth = vBox.getWidth();
        boardHeight = vBox.getHeight() - menuBar.getHeight();
        
        color = "default";
        
        vBox.getChildren().remove(anchor);
        CheckerBoard checkerBoard = new CheckerBoard(boardWidth, boardHeight, numRows, numCols, Color.RED, Color.BLACK);
        
        checkerBoard.build();
        anchor = checkerBoard.getBoard();
        vBox.getChildren().add(anchor);
        VBox.setVgrow(checkerBoard.getBoard(), Priority.ALWAYS);
    }
    
    //when the default button is hit remove the board and build one with the same dimensions with the colors skyblue and darkblue.
    @FXML
    private void handleBlueColors(ActionEvent event)
    {
        boardWidth = vBox.getWidth();
        boardHeight = vBox.getHeight() - menuBar.getHeight();
        
        color = "blue";
        
        vBox.getChildren().remove(anchor);
        CheckerBoard checkerBoard = new CheckerBoard(boardWidth, boardHeight, numRows, numCols, Color.SKYBLUE, Color.DARKBLUE);
        
        checkerBoard.build();
        anchor = checkerBoard.getBoard();
        vBox.getChildren().add(anchor);
        VBox.setVgrow(checkerBoard.getBoard(), Priority.ALWAYS);
    }
}
