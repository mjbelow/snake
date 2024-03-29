import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.layout.Pane;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.paint.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * Pane that displays all assets for Snake game
 * @author Matthew Below
 */
public class GameField extends Pane
{    
    private Snake player;
    private Timeline frameTimer;
    private Food food;
    private boolean diagonal;
    private boolean reverse;
    private final double movement;
    private Stage stage;
    private int score;
    private MainMenu menu;
    private int record;
    private Game gameApp;

    public GameField(boolean diagonal, boolean reverse, Stage stage, MainMenu menu, Game gameApp)
    {
        this.stage = stage;
        this.diagonal = diagonal;
        this.reverse = reverse;
        this.menu = menu;
        score = 1;
        record = this.menu.getRecord(diagonal, reverse);
        this.gameApp = gameApp;
        
        // set movement of snake (based on diagonal and reverse)
        if(diagonal && reverse)
            movement = -.5;
        else if(diagonal)
            movement = .5;
        else if(reverse)
            movement = -1;
        else
            movement = 1;
        
        // increase fps for diagonal since the snake moves half the distance
        double fps;
        if(diagonal)
            fps = 10.0;
        else
            fps = 8.0;        
        
        // start player in center of field
        if(diagonal)
            player = new Snake(Game.WIDTH / 2, Game.HEIGHT / 2, Direction.NW, menu);
        else
            player = new Snake(Game.WIDTH / 2, Game.HEIGHT / 2, Direction.N, menu);

        
        
        // generate and add food to field
        food = new Food(menu);
        generateFood();
        getChildren().add(food);

        // add player to field
        getChildren().add(player.getFirst());
        
        // change background color
        setBackground(new Background(new BackgroundFill(menu.getSettings().bg, new CornerRadii(0), new Insets(0))));
        
        // change title based on high score and current score
        stage.setTitle("Snake (" + record + ") (" + score + ")");
        
        // create Timeline for field
        frameTimer = new Timeline(new KeyFrame(Duration.seconds(1.0/fps),
            e->updateFrame()));
        frameTimer.setCycleCount(Animation.INDEFINITE);
    }

    public void addPiece()
    {
        // create a copy of last piece
        Tail piece = player.getLast().copy(player.determineColor(), player.determineSize(), menu.getSettings().strColor, menu.getSettings().strWidth);
        Move move = piece.getCurrentMove();
        
        // set new piece position according to direction copied from last piece
        // (e.g., if last piece is going left, set new piece 1 space to the right)
        if(move.getDirection() == Direction.N)
            move.setY(move.getY() + movement);
        else if(move.getDirection() == Direction.S)
            move.setY(move.getY() - movement);
        else if(move.getDirection() == Direction.E)
            move.setX(move.getX() + movement);
        else if(move.getDirection() == Direction.W)
            move.setX(move.getX() - movement);
        else if(move.getDirection() == Direction.NE) {
            move.setY(move.getY() + movement);
            move.setX(move.getX() + movement);
        }
        else if(move.getDirection() == Direction.NW) {
            move.setY(move.getY() + movement);
            move.setX(move.getX() - movement);
        }
        else if(move.getDirection() == Direction.SE) {
            move.setY(move.getY() - movement);
            move.setX(move.getX() + movement);
        }
        else if(move.getDirection() == Direction.SW) {
            move.setY(move.getY() - movement);
            move.setX(move.getX() - movement);
        }
        
        
        // add piece to player
        player.add(piece);
        
        // add piece to game field
        getChildren().add(piece);
    }

    public void generateFood()
    {
        boolean found = true;
        
        // coordinates for food (upper limit = 24, 18) (n * 20 + 10) (500x380)
        int x = Game.WIDTH;
        int y = Game.HEIGHT;
        
        // food generation restrictions based off of snake pieces coordinates
        ArrayList<Tail> tail = player.getTail();

            
        while(found)
        {
            
            // generate random coordinates for food
            x = (int)(Math.random() * Game.WIDTH);
            y = (int)(Math.random() * Game.HEIGHT);
            
            // don't think it's possible, but just in case
            if(x >= Game.WIDTH || y >= Game.HEIGHT) {
                // not continue because I want this to be noticeable if it ever happens
                break;
            }
            
            if(diagonal) {
                //prevent food from generating in corners if snake is moving diagonally
                if((x == 0 && y == 0) || (x == Game.WIDTH-1 && y == 0) || (x == 0 && y == Game.HEIGHT-1) || (x == Game.WIDTH-1 && y == Game.HEIGHT-1))
                    continue;
            }
            
            
            // prevent generating food in space occupied by snake
            for (Tail piece : tail) {
                
                if(x == piece.getCurrentMove().getX() && y == piece.getCurrentMove().getY()) {
                    found = true;
                    break;
                } else {
                    found = false;
                }
                
            }
        }
        
        food.setX(x);
        food.setY(y);
        
        food.setCenterX(x * 20 + 10);
        food.setCenterY(y * 20 + 10);
    }

    public void updateFrame()
    {
        // get first piece from snake
        Tail head = player.getFirst();
        
        // pause game if game over
        if(isGameOver()) {
            pause();
            return;
        }
        
        // perfom actions when snake runs into food
        if(head.getCurrentMove().getX() == food.getX() && head.getCurrentMove().getY() == food.getY()) {
            
            // add piece to player then update player
            addPiece();
            score++;
            stage.setTitle("Snake (" + record + ") (" + score + ")");
            player.updateFrame(movement);
            
            // pause game if game over, else generate food
            if(isGameOver()) {
                pause();
            } else {
                generateFood();
            }
            
        } else {
            player.updateFrame(movement);
            food.updateFrame();
        }
    }

    public boolean isGameOver()
    {
        // get first piece from snake
        Tail head = player.getFirst();
        
        // get tail from snake
        ArrayList<Tail> tail = player.getTail();
        
        // return true when running into edges
        if(head.getCurrentMove().getX() < 0 || head.getCurrentMove().getX() > Game.WIDTH-1 || head.getCurrentMove().getY() < 0 || head.getCurrentMove().getY() > Game.HEIGHT-1) {
            return true;
        }
        
        // return true when running into self
        for(int i = 1; i < tail.size(); i++) {
            
            if(head.getCurrentMove().getX() == tail.get(i).getCurrentMove().getX() && head.getCurrentMove().getY() == tail.get(i).getCurrentMove().getY()) {
                return true;
            }
            
        }
        
        return false;
    }
    
    // change player's direction on key input
    public void handleKey(KeyEvent ke)
    {
        if(diagonal) {
            if(ke.getCode() == KeyCode.NUMPAD4)
                player.changeDirection(Direction.NE);
            else if(ke.getCode() == KeyCode.NUMPAD5)
                player.changeDirection(Direction.NW);
            else if(ke.getCode() == KeyCode.NUMPAD1)
                player.changeDirection(Direction.SE);
            else if(ke.getCode() == KeyCode.NUMPAD2)
                player.changeDirection(Direction.SW);
        }
        else {
            if(ke.getCode() == KeyCode.UP)
                player.changeDirection(Direction.N);
            else if(ke.getCode() == KeyCode.DOWN)
                player.changeDirection(Direction.S);
            else if(ke.getCode() == KeyCode.LEFT)
                player.changeDirection(Direction.E);
            else if(ke.getCode() == KeyCode.RIGHT)
                player.changeDirection(Direction.W);
        }
    }

    public void play()
    {
        frameTimer.play();
    }
    
    // end game and go back to main menu
    public void pause()
    {
        frameTimer.pause();
        
        // update high score if it is beaten and write to file
        if(score > record) {
            // get user name for new high score
            ScoreDialog newScore = new ScoreDialog();
            gameApp.getScene().setRoot(newScore);
            stage.setScene(gameApp.getScene());
        }
        else {
            gameApp.getScene().setRoot(menu);
            stage.setScene(gameApp.getScene());
        }
        
    }
    
    public class ScoreDialog extends StackPane
    {
        
        Rectangle box;
        TextField name;
        Label confirm;
        Label highScore;
        Label instruct;
        
        public ScoreDialog()
        {
            
            box = new Rectangle(360, 180);
            box.setStyle("-fx-fill: rgba(0, 0, 0, .6); -fx-arc-height: 26; -fx-arc-width: 26;");
            
            
            VBox form = new VBox();
            
            // high score label
            highScore = new Label("NEW HIGH SCORE!");
            highScore.setStyle("-fx-text-fill: white; -fx-font: bold 24 inherit;");
            
            instruct = new Label("Enter your initials.");
            instruct.setStyle("-fx-text-fill: white; -fx-font-size: 18;");
            
            // text field for name
            name = new TextField();
            name.setMaxWidth(50);
            name.setStyle("-fx-font: 14 monospace; -fx-alignment: center;");
            
            name.textProperty().addListener(l -> {
                // trim last character if it's not a letter or there are more than 3
                if(!name.getText().matches("[A-Za-z]{1,3}") && name.getText().matches(".+")) {
                    name.setText(name.getText().substring(0, name.getText().length() - 1));
                    instruct.setText("[Enter only 1-3 letters.]");
                }
            });
            
            // confirm label 
            confirm = new Label("PRESS ENTER BUTTON");
            confirm.setStyle("-fx-underline: true; -fx-text-fill: white; -fx-font-size: 14;");
            
            form.getChildren().addAll(highScore, instruct, name, confirm);
            form.setAlignment(Pos.CENTER);
            form.setSpacing(12);
            
            
            getChildren().addAll(box, form);
            
            setOnKeyPressed(ke->setName(ke));
        }
        
        public void setName(KeyEvent ke)
        {
            
            if(ke.getCode() == KeyCode.ENTER) {
                
                // allow only 3 letters for user's initials
                if(!name.getText().matches("[A-Za-z]{1,3}")) {
                    instruct.setText("[Enter only 1-3 letters.]");
                }
                else {
                    // update record and file
                    menu.setRecord(diagonal, reverse, score, name.getText().toUpperCase());
                    menu.writeHighScores();
                    
                    // update high score label
                    menu.showRecord(diagonal, reverse);

                    gameApp.getScene().setRoot(menu);
                    stage.setScene(gameApp.getScene());
                }
            }
     
        }
        
    }
    
}
