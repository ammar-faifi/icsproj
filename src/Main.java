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

public class Main extends Application 
{
    protected static final int WIDTH = 666;
    protected static final int HEIGHT = 777;
    private VBox vbox = new VBox(50);


    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Scene homeScene = new Scene(vbox, WIDTH, HEIGHT);
        Scene gameScene = Game.scene(primaryStage, homeScene);
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Home | V0.1-beta");
        primaryStage.show();

        // Layout objects
        ImageView img = new ImageView(new Image("proj.png"));
        img.setFitWidth(WIDTH/2);
        img.setFitHeight(WIDTH/2);

        Button testButton = new Button("Start the Game Now!");

        testButton.setOnAction(event -> 
        {
            primaryStage.setScene(gameScene);
            primaryStage.setTitle("Gamming");
            Game.timeline.play();
        });
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(img, testButton);

    }

    public static void main(String[] args) 
    {launch(args);}
    
}