import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

//Here the all layouts of the home page
public abstract class  Home extends Application
{
    private static final int WIDTH = 555;
    private static final int HEIGHT = 333;
    protected static VBox vbox = new VBox();
    
    public static Scene scene(Stage primaryStage, Scene scene)
    {
        Button testButton = new Button("change to Main");
        testButton.setOnAction(event -> 
        {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main");
        });
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(testButton);
        
        return new Scene(vbox, WIDTH, HEIGHT);
    }
    
}
