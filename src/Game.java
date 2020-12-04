
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

//This class is responsible for the info data:  timer, scores, and top score  

public abstract class Game 
{
    private static final int WIDTH = 555;
    private static final int HEIGHT = 333;
    private static Date date = new Date();
    private static Label timerLabel = new Label();
    protected static Timeline timeline;


    protected static Scene scene(Stage primaryStage, Scene scene) throws Exception 
    {
        HBox pane = new HBox(50);
               
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            
            Calendar cal = Calendar.getInstance();
            Date date = new Date();
            
            // timerLabel.setText( (new Date(date.getTime() - startTime)).toString() );

            timerLabel.setText(cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
            System.out.print("Running...");
            
        }));
        
        Button testButton = new Button("change to Main");
        testButton.setOnAction(event -> 
        {
            timeline.pause();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main");
        });
        
        timeline.setCycleCount(Animation.INDEFINITE);
        
        pane.getChildren().addAll(timerLabel, testButton);
        pane.setAlignment(Pos.CENTER);

        return new Scene(pane, WIDTH, HEIGHT);
    }

}