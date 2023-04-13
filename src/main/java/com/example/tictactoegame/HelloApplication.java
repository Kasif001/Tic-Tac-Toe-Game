package com.example.tictactoegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Callable;

public class HelloApplication extends Application {
    private Label playerXScoreLable, playerYScoreLable;

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;

    private int playerXScore = 0, playerYScore = 0;

    private BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setBackground(new Background(new BackgroundFill(Color.DEEPPINK,CornerRadii.EMPTY, Insets.EMPTY)));

        //Title
        Label titleLable = new Label("Tic Tac Toe");
        titleLable.setTextFill(Color.BLACK);
        titleLable.setStyle("-fx-font-size: 35pt; -fx-font-weight: bold;");
        root.setTop(titleLable);
        BorderPane.setAlignment(titleLable, Pos.CENTER);

        //Game Board
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);


        gridPane.setHgap(10);
        gridPane.setVgap(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setStyle("-fx-font-size: 25pt; -fx-font-weight: bold;");
                button.setPrefSize(100,100);
                button.setOnAction(event->buttonClick(button));


                buttons[i][j] = button;
                gridPane.add(button,j,i);
            }
        }
        root.setCenter(gridPane);

        //Score
        HBox scoreboard = new HBox();
        scoreboard.setAlignment(Pos.CENTER);
        playerXScoreLable = new Label("Player X:0 ");
        playerXScoreLable.setTextFill(Color.BLACK);
        playerXScoreLable.setStyle("-fx-font-size: 21pt; -fx-font-weight: bold;");

        playerYScoreLable = new Label(" Player O:0");
        playerYScoreLable.setTextFill(Color.BLACK);
        playerYScoreLable.setStyle("-fx-font-size: 21pt; -fx-font-weight: bold;");
        scoreboard.getChildren().addAll(playerXScoreLable,playerYScoreLable);

        root.setBottom(scoreboard);



        return root;
    }

    private void buttonClick(Button button){
        if(button.getText().equals("")){
            if(playerXTurn){
                button.setTextFill(Color.DARKBLUE);
                button.setText("X");

            }else{
                button.setTextFill(Color.DARKRED);
                button.setText("O");
            }

            playerXTurn = !playerXTurn;
            checkWinner();
        }
        return;
    }

    //Check winner
    private void checkWinner() {
        //row
        for (int row = 0; row < 3; row++) {
            if (buttons[row][0].getText().equals(buttons[row][1].getText()) && buttons[row][1].getText().equals(buttons[row][2].getText()) && !buttons[row][0].getText().isEmpty()) {
                //we have a winner
                String winner = buttons[row][0].getText();
                showWinner(winner);
                updateScores(winner);
                resetBoard();
            }
        }

        //col
        for (int col = 0; col < 3; col++) {
            if (buttons[0][col].getText().equals(buttons[1][col].getText()) &&
                    buttons[1][col].getText().equals(buttons[2][col].getText()) &&
                    !buttons[0][col].getText().isEmpty()) {
                //we have a winner
                String winner = buttons[0][col].getText();
                showWinner(winner);
                updateScores(winner);
                resetBoard();
                return;
            }
        }

        // 1 diagonal
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().isEmpty()) {
            //we have a winner
            String winner = buttons[0][0].getText();
            showWinner(winner);
            updateScores(winner);
            resetBoard();
            return;
        }
        //2nd diagonal
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                !buttons[0][2].getText().isEmpty()) {
            //we have a winner
            String winner = buttons[0][2].getText();
            showWinner(winner);
            updateScores(winner);
            resetBoard();
            return;
        }

        //tie
        boolean tie = true;
        for(Button row[]: buttons){
            for(Button button: row){
                if(button.getText().isEmpty()){
                    tie = false;
                }
            }
        }
        if(tie == true){
            if(playerXScore > playerYScore){
                showTieWinner("X");
            }else if(playerXScore < playerYScore){
                showTieWinner("O");
            }else{
                showTieWinner("No-One");
            }
            playerYScore = 0;
            playerXScore = 0;
            playerXScoreLable.setText("Player X:"+playerXScore);
            playerYScoreLable.setText(" Player O:"+playerYScore);
            resetBoard();

        }

    }
    //funtion to raise a message if someone won the game
    private void showWinner(String Winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner");
        alert.setContentText("Congratulates "+Winner+"! You Won The Game Dud!");
        alert.setHeaderText("");
        alert.showAndWait();
    }


    //Game Over if tie
    private void showTieWinner(String winner){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tie");
        alert.setContentText("Game Over It's Tie and Winner is "+winner);
        alert.setHeaderText("");
        alert.showAndWait();
    }


    //Update Scores
    private void updateScores(String player){
        if(player.equals("X")){
            playerXScore++;
            playerXScoreLable.setText("Player X:"+playerXScore);
        }else{
            playerYScore++;
            playerYScoreLable.setText(" Player O:"+playerYScore);
        }
    }

    //Reset the Board/Game
    private void resetBoard(){
        for(Button row[]: buttons){
            for(Button button: row){
                button.setText("");
            }
        }
    }
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}