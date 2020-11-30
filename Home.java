import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

//Here the all layouts of the home page
public abstract class  Home extends Application
{
    protected static VBox vbox = new VBox();
    
    public static VBox homelayout(Stage primaryStage, Scene scene)
    {
        Button testButton = new Button("change to Main");
        testButton.setOnAction(event -> 
        {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main");
        });
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(testButton);
        
        return vbox;
    }
    
}
