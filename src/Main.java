import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Pos;


// This code is for manage the scenes and running them

public class Main extends Application 
{
    private final int WIDTH = 666;
    private final int HEIGHT = 666;

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        VBox vbox = new VBox();

        Scene gameScene = Game.scene(primaryStage, homeScene);
        Scene homeScene = Home.scene(primaryStage, gameScene);
        
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Home | V0.1-beta");
        primaryStage.show();

    
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll();
    }

    public static void main(String[] args) 
    {launch(args);}
    
}