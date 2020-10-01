package ntctk4checkers;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ntctk4
 */
public class CheckerBoard {
    
    //fields used in the checker board class
    private AnchorPane board;
    private double width;
    private double height;
    private int rows;
    private int cols;
    private Color light;
    private Color dark;
    
     //constructor for creating a checker board object. 
    //this one is used if the colors specified for the checker board.
    public CheckerBoard(double width, double height, int rows, int cols, Color light, Color dark)
    {
        this(width, height, rows, cols);
        this.dark = dark;
        this.light = light;
        
    }
    //constructor for creating a checker board object. 
    //this one is used if the colors are not specified.
    //red and black will be used for the colors.
    public CheckerBoard(double width, double height, int rows, int cols)
    {
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.cols = cols;
        
    }
    
    //this method creates the checker board and puts it in an anchor pane
    public void build()
    {
        //find the height and width for each rectangle
       double recWidth = width/cols;
       double recHeight = height/rows;
       
       //as a default the checker board starts off with its light color as red
       //and its dark color as black.
       if(this.getDarkColor() == null || this.getLightColor() == null)
       {
           this.dark = Color.BLACK;
           this.light = Color.RED;
       }
       
       //create a new anchor pane to be used as the checkerboard
       board = new AnchorPane();

       //this for loop creates the boards checkered pattern.
       //the number of rectangles created will be rows*cols
       for(int i = 0; i < rows; i++)
       {
           for(int j = 0; j < cols; j++)
           {
               Rectangle rect = new Rectangle(recWidth, recHeight);
               if((j % 2 != 0 && i % 2 == 0)
                       ||(i % 2 != 0 && j %2 == 0))
               {
                    rect.setFill(this.getLightColor());
               }
               else
               {
                   rect.setFill(this.getDarkColor());
               }
               
               //once each rect is created with the correct color
               //it is added to the anchorpane.
               board.getChildren().add(rect);
               AnchorPane.setLeftAnchor(rect, ((recWidth*j)));
               AnchorPane.setBottomAnchor(rect, ((recHeight*i)));

           }
       }
    }
    
    //this lets the controller get the created board
    public AnchorPane getBoard()
    {
        return board;
    }
    
    //getters for the controller to get info about the board
    //I ended up not needing theses for my implimentation.
    public int getNumRows()
    {
        return rows;
    }
    
    public int getNumCols()
    {
        return cols;
    }
    
    public double getWidth()
    {
        return width;
    }
    
    public double getHeight()
    {
        return height;
    }
    
    public Color getDarkColor()
    {
        return this.dark;
    }
    
    public Color getLightColor()
    {
        return this.light;
    }
    public double getRectangleWidth()
    {
        return width;
    }
    
    public double getRetangleHeight()
    {
        return height;
    }
    
}
