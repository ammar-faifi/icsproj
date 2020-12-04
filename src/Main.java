import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Pos;


// This code is for manage the scenes and running them

public class Main extends Application 
{
    private final int WIDTH = 555;
    private final int HEIGHT = 333;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        VBox vbox = new VBox();
        Scene mainScene = new Scene(vbox, WIDTH, HEIGHT);
        Scene homeScene = Home.scene(primaryStage, mainScene);
        Scene timerScene = Game.scene(primaryStage, mainScene);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Main");
        primaryStage.show();


        Button mainButton = new Button("change to Home");
        Button timerButton = new Button("change to Timer pane");
        
        mainButton.setOnAction(event -> 
        {
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Home");
            
        });

        timerButton.setOnAction(event -> 
        {
            primaryStage.setScene(timerScene);
            primaryStage.setTitle("Timer");
            Game.timeline.play();
            
        });
   
    
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(mainButton, timerButton);
    }

    public static void main(String[] args) 
    {launch(args);}
    
}