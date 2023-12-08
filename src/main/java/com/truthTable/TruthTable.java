package com.truthTable;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TruthTable extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //create a scene
        Scene scene = new Scene(new Group());
        // set the program's title
        stage.setTitle("Truth Table generator");
        //create a root VBox to contain all the components
        VBox root = new VBox();
        //add space between components
        root.setSpacing(10);
        //create a text field for user to write the equation
        TextField ftxt = new TextField();;
        //create a button
        Button Tablebtn=new Button("Show table");
        //set empty space around the root
        root.setPadding(new Insets(20));
        //set the min size for the program window
        root.setMinSize(700,711);
        //Hbox contains the table and the K-Map
        HBox result=new HBox();
        //add all components to the root
        root.getChildren().addAll(new Label("enter the equation in W X Y Z"),ftxt,Tablebtn,result);

        //set an action to happen when the button is clicked
        Tablebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //trying to solve some common input problems
                //first convert al characters to capital as expected to be
                String equation = ftxt.getText().toUpperCase();
                //if there is any double invert remove them both as A'' is A
                equation=equation.replaceAll("''","");
                //remove spaces from the equation
                equation=equation.replaceAll(" ","");
                //remove any extra plus signs
                while (equation.indexOf("\\+\\+")!=-1)
                    equation=equation.replaceAll("\\+\\+","\\+");
                // check if the equation is valid or not
                //remove all inputs and valid signs from the equation from the equation
                String test = equation.replaceAll("W","");
                test = test.replaceAll("X","");
                test = test.replaceAll("Y","");
                test = test.replaceAll("Z","");
                test = test.replaceAll("\\+","");
                test = test.replaceAll("'","");
                //if the test is empty then it has to invalid chars
                if(test.length()==0){
                    //remove any old results
                    result.getChildren().clear();
                    //set the equation in the correct form
                    ftxt.setText(equation);
                    //go create the table and K-Map
                    result.getChildren().add(fillTable(equation));
                }else {
                    //if the test still contains any chars then the input is invalid
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error invalid input");
                    alert.setHeaderText("Your input contains the following invalid characters "+ test);
                    alert.setContentText("please note that the only valid characters are W X Y Z + '");
                    alert.showAndWait();
                }
            }
        });
        //add all the components to the scene
        ((Group) scene.getRoot()).getChildren().add(root);
        //set the scene and show the program
        stage.setScene(scene);
        stage.show();
    }

    public static HBox fillTable(String equation){
        //first create the table and set its dimensions
        TableView<Eq> table = new TableView();
        table.setMaxWidth(180);
        table.setMinHeight(500);
        //creat columns for the inputs and the output
        TableColumn wColumn = new TableColumn("W");
        TableColumn xColumn = new TableColumn("X");
        TableColumn yColumn = new TableColumn("Y");
        TableColumn zColumn = new TableColumn("Z");
        TableColumn fColumn = new TableColumn("F");
        //make the output column slightly bigger than the rest
        fColumn.setMinWidth(60);
        //match each column with the variable representing it in Eq class
        wColumn.setCellValueFactory(new PropertyValueFactory<>("W"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("X"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("Y"));
        zColumn.setCellValueFactory(new PropertyValueFactory<>("Z"));
        fColumn.setCellValueFactory(new PropertyValueFactory<>("F"));
		//create an ArrayList  of Eq objects
		ArrayList<Eq> results = new ArrayList();

		//add objects to the ArrayList with every possible input to calculate the output for them
        results.add(new Eq('0','0','0','0'));
        results.add(new Eq('0','0','0','1'));
        results.add(new Eq('0','0','1','0'));
        results.add(new Eq('0','0','1','1'));
        results.add(new Eq('0','1','0','0'));
        results.add(new Eq('0','1','0','1'));
        results.add(new Eq('0','1','1','0'));
        results.add(new Eq('0','1','1','1'));
        results.add(new Eq('1','0','0','0'));
        results.add(new Eq('1','0','0','1'));
        results.add(new Eq('1','0','1','0'));
        results.add(new Eq('1','0','1','1'));
        results.add(new Eq('1','1','0','0'));
        results.add(new Eq('1','1','0','1'));
        results.add(new Eq('1','1','1','0'));
        results.add(new Eq('1','1','1','1'));

        //split the equation by + sign to get ann array ot the product terms
        String[] terms= equation.split("\\+");
        //loop on all possible inputs to calculate the output
        for (Eq eq:results){
            //create a boolean to be the output
            boolean fr=false;
            for(String f:terms){
                if(f.length()>0){
                    //get the inputs from the object and create a result presents the result of the and to all the variables in this term
                    boolean w=eq.getBooleanW() , x=eq.getBooleanX(), y=eq.getBooleanY(), z=eq.getBooleanZ(), result = true;
                    //loop in all chars of the term
                    for (int i = 0; i < f.length(); i++) {
                        //check if it'd the last char or not to avoid out of boundary exception when checking if inverted
                        if (i + 1 < f.length()) {
                            //check id current input is W
                            if (f.charAt(i) == 'W') {
                                //check if it's inverted
                                if (f.charAt(i + 1) == '\'') {
                                    //and result with the inverted input
                                    result = result && !w;
                                } else {
                                    //and result with the input
                                    result = result && w;
                                }
                            //check id current input is X
                            } else if (f.charAt(i) == 'X') {
                                //check if it's inverted
                                if (f.charAt(i + 1) == '\'') {
                                    //and result with the inverted input
                                    result = result && !x;
                                } else {
                                    //and result with the input
                                    result = result && x;
                                }
                            //check id current input is Y
                            } else if (f.charAt(i) == 'Y') {
                                //check if it's inverted
                                if (f.charAt(i + 1) == '\'') {
                                    //and result with the inverted input
                                    result = result && !y;
                                } else {
                                    //and result with the input
                                    result = result && y;
                                }
                            //check id current input is Z
                            } else if (f.charAt(i) == 'Z') {
                                //check if it's inverted
                                if (f.charAt(i + 1) == '\'') {
                                    //and result with the input
                                    result = result && !z;
                                } else {
                                    //and result with the input
                                    result = result && z;
                                }
                            }
                        } else {
                            //if it's the last char then check for the char and and it directly without checking for invertion
                            if (f.charAt(i) == 'W') {
                                result = result && w;
                            } else if (f.charAt(i) == 'X') {
                                result = result && x;
                            } else if (f.charAt(i) == 'Y') {
                                result = result && y;
                            } else if (f.charAt(i) == 'Z') {
                                result = result && z;
                            }
                        }
                    }
                    //OR result with the fr to find the output
                    fr=fr||result;
                }
            }
            //set fr as the output of the current input
            eq.setBooleanF(fr);
        }
        //fill the table with the inputs and their corresponding
        table.setItems(FXCollections.observableList(results));
        //add all the columons to the table
        table.getColumns().addAll(wColumn,xColumn,yColumn,zColumn,fColumn);
        //show the table and the K-Map
        return new HBox(table,kmap(results));
    }

    public static GridPane kmap(ArrayList<Eq> results){
        //create 16 labels one for each output
        Label f0 = new Label(results.get(0).toString());
        Label f1 = new Label(results.get(1).toString());
        Label f2 = new Label(results.get(2).toString());
        Label f3 = new Label(results.get(3).toString());
        Label f4 = new Label(results.get(4).toString());
        Label f5 = new Label(results.get(5).toString());
        Label f6 = new Label(results.get(6).toString());
        Label f7 = new Label(results.get(7).toString());
        Label f8 = new Label(results.get(8).toString());
        Label f9 = new Label(results.get(9).toString());
        Label f10 = new Label(results.get(10).toString());
        Label f11 = new Label(results.get(11).toString());
        Label f12 = new Label(results.get(12).toString());
        Label f13 = new Label(results.get(13).toString());
        Label f14 = new Label(results.get(14).toString());
        Label f15 = new Label(results.get(15).toString());
        //set the style for all the labels solid balck borders , 1.5em font size and 10 padding
        f0.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f1.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f2.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f3.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f4.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f5.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f6.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f7.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f8.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f9.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f10.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f11.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f12.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f13.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f14.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");
        f15.setStyle("-fx-border-style:solid;-fx-border-width: 4;-fx-border-color: black;-fx-font-size: 1.5em;-fx-padding:10;");

        //create 2 labels for the W and W' on the top of the map
        Label w = new Label("W");
        Label winv = new Label("W'");
        //set the style for the solid black border between both only bold 1.5em font
        w.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:hidden hidden hidden solid;-fx-border-width: 4;-fx-border-color: black;");
        winv.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:hidden solid hidden hidden;-fx-border-width: 4;-fx-border-color: black;");


        //create 3 labels for the X and X' on both sides on the bot of the map
        Label x = new Label("X");
        Label xinv1 = new Label("X' ");
        Label xinv2 = new Label("X'");
        //set the style for the solid black border between each 2 only and bold 1.5em font
        x.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:hidden solid hidden solid;-fx-border-width: 4;-fx-border-color: black;");
        xinv1.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:hidden solid hidden hidden;-fx-border-width: 4;-fx-border-color: black;");
        xinv2.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:hidden hidden hidden solid;-fx-border-width: 4;-fx-border-color: black;");



        //create 2 labels for the Y and Y' on the top of the map
        Label y = new Label("Y ");
        Label yinv = new Label("Y'");
        //set the style for the solid black border between both only bold 1.5em font
        y.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:solid hidden hidden hidden;-fx-border-width: 4;-fx-border-color: black;");
        yinv.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:hidden hidden solid hidden;-fx-border-width: 4;-fx-border-color: black;");


        //create 3 labels for the Z and Z' on both sides on the bot of the map
        Label z = new Label("Z ");
        Label zinv1 = new Label("Z'");
        Label zinv2 = new Label("Z'");
        //set the style for the solid black border between each 2 only and bold 1.5em font
        z.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:solid hidden solid hidden;-fx-border-width: 4;-fx-border-color: black;");
        zinv1.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:hidden hidden solid hidden;-fx-border-width: 4;-fx-border-color: black;");
        zinv2.setStyle("-fx-font-size: 1.5em;-fx-font-weight: bold;-fx-border-style:solid hidden hidden hidden;-fx-border-width: 4;-fx-border-color: black;");


        //Create gridPane to contain the K-map
        GridPane gridPane = new GridPane();

        //Set position of all the tytle labels to center
        w.setAlignment(Pos.CENTER);
        winv.setAlignment(Pos.CENTER);
        x.setAlignment(Pos.CENTER);
        xinv1.setAlignment(Pos.CENTER);
        xinv2.setAlignment(Pos.CENTER);
        y.setAlignment(Pos.CENTER);
        yinv.setAlignment(Pos.CENTER);
        z.setAlignment(Pos.CENTER);
        zinv1.setAlignment(Pos.CENTER);
        zinv2.setAlignment(Pos.CENTER);

        //set the max size to a very larger number so it can expands as much as needed
        w.setMaxWidth(Double.MAX_VALUE);
        winv.setMaxWidth(Double.MAX_VALUE);
        x.setMaxWidth(Double.MAX_VALUE);
        xinv1.setMaxWidth(Double.MAX_VALUE);
        xinv2.setMaxWidth(Double.MAX_VALUE);
        y.setMaxHeight(Double.MAX_VALUE);
        yinv.setMaxHeight(Double.MAX_VALUE);
        z.setMaxHeight(Double.MAX_VALUE);
        zinv1.setMaxHeight(Double.MAX_VALUE);
        zinv2.setMaxHeight(Double.MAX_VALUE);

        //set all title labels to fill the available space for them
        GridPane.setFillWidth(w, true);
        GridPane.setFillWidth(winv, true);
        GridPane.setFillWidth(x, true);
        GridPane.setFillWidth(xinv1, true);
        GridPane.setFillWidth(xinv2, true);
        GridPane.setFillWidth(y, true);
        GridPane.setFillWidth(yinv, true);
        GridPane.setFillWidth(z, true);
        GridPane.setFillWidth(zinv1, true);
        GridPane.setFillWidth(zinv2, true);

        //add all title labels to the grid
        gridPane.add(winv, 1, 0, 2, 1);
        gridPane.add(w, 3, 0, 2, 1);
        gridPane.add(yinv, 0, 1, 1, 2);
        gridPane.add(y, 0, 3, 1, 2);
        gridPane.add(xinv1, 1, 5, 1, 1);
        gridPane.add(x, 2, 5, 2, 1);
        gridPane.add(xinv2, 4, 5, 1, 1);
        gridPane.add(zinv1, 5, 1, 1, 1);
        gridPane.add(z, 5, 2, 1, 2);
        gridPane.add(zinv2, 5, 4, 1, 1);

        //first column
        gridPane.add(f0, 1, 1, 1, 1);
        gridPane.add(f1, 1, 2, 1, 1);
        gridPane.add(f3, 1, 3, 1, 1);
        gridPane.add(f2, 1, 4, 1, 1);

        //second column
        gridPane.add(f4, 2, 1, 1, 1);
        gridPane.add(f5, 2, 2, 1, 1);
        gridPane.add(f7, 2, 3, 1, 1);
        gridPane.add(f6, 2, 4, 1, 1);

        //third column
        gridPane.add(f12, 3, 1, 1, 1);
        gridPane.add(f13, 3, 2, 1, 1);
        gridPane.add(f15, 3, 3, 1, 1);
        gridPane.add(f14, 3, 4, 1, 1);

        //fourth column
        gridPane.add(f8, 4, 1, 1, 1);
        gridPane.add(f9, 4, 2, 1, 1);
        gridPane.add(f11, 4, 3, 1, 1);
        gridPane.add(f10, 4, 4, 1, 1);

        //return the grid to be shown
        return gridPane;
    }
}

