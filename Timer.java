
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Timer extends Application {
    private final int WIDTH = 555;
    private final int HEIGHT = 333;
    Date date = new Date();
    Label timerLabel = new Label();

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        Scene scene = new Scene(mainPane, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Timer");
        primaryStage.show();

        mainPane.setTop(timerLabel);

        final DateFormat format = DateFormat.getInstance();

        long startTime = date.getTime();
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
           
            Calendar cal = Calendar.getInstance();
            Date date = new Date();

            // timerLabel.setText( (new Date(date.getTime() - startTime)).toString() );
            timerLabel.setText(cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
            System.out.println(cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
           
		}));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

}