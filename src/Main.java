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
    protected static final int HEIGHT = 677;
    private VBox vbox = new VBox(50);
    private StackPane pane = new StackPane();


    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Scene homeScene = new Scene(pane, WIDTH, HEIGHT);
        Scene gameScene = Game.scene(primaryStage, homeScene);

        // Layout objects
        ImageView img = new ImageView(new Image("proj.png"));
        img.setFitWidth(WIDTH/2);
        img.setFitHeight(HEIGHT/2);

        // ImageView background = new ImageView(new Image("background.png"));
        ImageView background = new ImageView(new Image("background.png"));
        background.setId("home-background-img");
        background.setFitWidth(WIDTH);
        background.setFitHeight(HEIGHT);

        Button testButton = new Button("Start the Game Now!");

        testButton.setOnAction(event -> 
        {
            primaryStage.setScene(gameScene);
            primaryStage.setTitle("Gamming");
            Game.timeline.play();
        });
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(img, testButton);
        pane.getChildren().addAll(background, vbox);
        
        homeScene.getStylesheets().add("style.css");
        gameScene.getStylesheets().add("style.css");
        primaryStage.setScene(homeScene);
        primaryStage.setTitle("Home | V0.1-beta");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) 
    {launch(args);}
    
}