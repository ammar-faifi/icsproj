import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.geometry.Pos;


// This code is for manage the scenes and running them

public abstract class Home extends Application 
{
    protected static final int WIDTH = 666;
    protected static final int HEIGHT = 677;
    private static VBox vbox = new VBox(50);
    private static StackPane pane = new StackPane();


    protected static Scene scene(Stage primaryStage, Scene scene) throws Exception 
    {
        // Layout objects
        ImageView img = new ImageView(new Image("contents/proj.png"));
        img.setFitWidth(WIDTH/2);
        img.setFitHeight(HEIGHT/2);

        ImageView background = new ImageView(new Image("contents/background.png"));
        background.setId("home-background-img");
        background.setFitWidth(WIDTH);
        background.setFitHeight(HEIGHT);
        
        Button startButton = new Button("Start the Game Now!");
        startButton.setId("start-button");
        
        startButton.setOnAction(event -> 
        {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gamming");
            Game.timeline.play();
        });
        
        vbox.getChildren().addAll(img, startButton);
        vbox.setId("home-vbox");
        pane.getChildren().addAll(background, vbox);

            
        return new Scene(pane, WIDTH, HEIGHT);
    }

    public static void main(String[] args) 
    {launch(args);}
    
}