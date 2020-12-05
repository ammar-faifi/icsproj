
import java.util.Calendar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

//This class is responsible for the info data:  timer, scores, and top score  
public abstract class Game 
{
    protected static Timeline timeline;
    protected static int startSecond;
    protected static int startMinute;
    protected static boolean stop = true;
    private static int currentScore;
    
    
    protected static Scene scene(Stage primaryStage, Scene scene) throws Exception 
    {
        HBox topPane = new HBox(50);
        topPane.setId("game-header");
        BorderPane mainPane = new BorderPane();
        
        
        Label timeLabel = new Label("Time:");
        Label scoreLabel = new Label("Your Score:");
        Label score = new Label(Integer.toString(currentScore));
        Label time = new Label();
        
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Calendar cal = Calendar.getInstance();
            if (stop)
            {
                startSecond = cal.get(Calendar.SECOND);
                startMinute = cal.get(Calendar.MINUTE);
                stop = false;
            }
            time.setText((cal.get(Calendar.MINUTE) - startMinute) + ":" 
            + (cal.get(Calendar.SECOND) - startSecond));
            System.out.println("Running...");
            
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        
        
        
        Button testButton = new Button("END the Game !!");
        testButton.setOnAction(event -> 
        {
            timeline.pause();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Home");
            stop = true;
        });
        
        
        topPane.getChildren().addAll(timeLabel, time, scoreLabel, score, testButton);
        mainPane.setTop(topPane);
        topPane.setAlignment(Pos.CENTER);
        topPane.setStyle("−fx−border−color: green;");
        
        return new Scene(mainPane, Main.WIDTH, Main.HEIGHT);
    }

}