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
    VBox vbox = new VBox();
    Scene mainScene = new Scene(vbox, WIDTH, HEIGHT);

    public Main(){}

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Scene homeScene = new Scene(Home.homelayout(primaryStage, mainScene), WIDTH, HEIGHT);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Main");
        primaryStage.show();


        Button mainButton = new Button("change to Home");
        mainButton.setOnAction(event -> 
        {
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Home");
            
        });

        // Button homeButton = new Button("change to Home");
        // homeButton.setOnAction(event -> 
        // {
        //     primaryStage.setScene(mainScene);
        //     primaryStage.setTitle("Main");
            
        // });
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(mainButton);
    }

    public Scene getScene() {return mainScene;}



    public static void main(String[] args) 
    {launch(args);}
    
}