import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

//Here the all layouts of the home page
public abstract class  Home extends Application
{
    private static final int WIDTH = 666;
    private static final int HEIGHT = 666;
    protected static VBox vbox = new VBox(50);
    protected static StackPane pane = new StackPane();
    
    public static Scene scene(Stage primaryStage, Scene scene)
    {

        ImageView img = new ImageView(new Image("proj.png"));
        img.setFitWidth(WIDTH/2);
        img.setFitHeight(WIDTH/2);

        Button testButton = new Button("Start the Game Now!");

        testButton.setOnAction(event -> 
        {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gamming");
            Game.timeline.play();
        });
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(img, testButton);
        
        return new Scene(vbox, WIDTH, HEIGHT);
    }
    
}
