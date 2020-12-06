// package sample;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.*;


public class Game extends Application 
{
    private final int num_of_pairs = 8;
    private final int num_per_row = 4;
    private final int CELL_SIZE = 140;
    private int clickCount = 2;
    private Tile selected = null;
    private HBox topPane = new HBox(50);
    private StackPane allPanes = new StackPane();
    private BorderPane mainPane = new BorderPane();
    private Scene ss = new Scene(allPanes, Home.WIDTH, Home.HEIGHT);
    private int startSecond;
    private int startMinute;
    private int currentScore;
    private int correct;
    private Image[] puzz = new Image[num_of_pairs];
    private Tile[] tile = new Tile[16];
    private static Pane root = new Pane();
    protected static Timeline timeline;
    protected static boolean stop = true;
    private static ArrayList<Tile> tiles = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        Scene homeScene = Home.scene(primaryStage, ss);

        //Create images
        for (int i = 0; i < puzz.length; i++)
        puzz[i] = new Image("contents/" + i + ".jpeg");
       
        refresh();

        Button play = new Button("Play again");
        play.setId("play-button");
        play.setOnMouseClicked(n -> 
        {
            timeline.play();
            startMinute = startSecond = correct = 0;

            for (int i = 0; i < tiles.size(); i++)    
            {         
            tile[i].close();
            tiles.get(i).close();
            }
            
            for (int i = 0; i < tiles.size(); i++) 
            root.getChildren().remove(tile[i]);
            
            refresh();
        });

        // Header: time scores buttons
        Label score = new Label("Your Score:" + currentScore);
        Label time = new Label("Time:" + startMinute + ":" + startSecond);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> 
        {
            if (stop) {
                startSecond = 0;
                startMinute = 0;
                stop = false;
            } else {
                if (startSecond == 59) 
                {
                    startSecond = 0;
                    startMinute++;
                } 
                else startSecond++;
            }
            time.setText("Time:" + startMinute + ":" + startSecond);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        Button endButton = new Button("PAUSE the Game !!");
        endButton.setId("end-button");
        endButton.setOnAction(event -> {
            stop = false;
            timeline.pause();
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("Home | V0.1");
        });

        //background
        ImageView background = new ImageView(new Image("contents/background2.jpg"));
        background.setId("home-background-img2");
        background.setFitWidth(Home.WIDTH);
        background.setFitHeight(Home.HEIGHT);

        // Setting layout
        topPane.setMinHeight(50);
        topPane.setId("game-header");
        topPane.getChildren().addAll(time, score, endButton, play);
        topPane.setAlignment(Pos.CENTER);
        mainPane.setTop(topPane);
        mainPane.setCenter(root);
        allPanes.getChildren().addAll(background, mainPane);
        homeScene.getStylesheets().add("style.css");
        ss.getStylesheets().add("style.css");

        primaryStage.setScene(homeScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args)
    {launch(args);}

    //set images and locate them randomly
    public void refresh() 
    {   
        if (stop)
            for (int i = 0; i < num_of_pairs; i++) 
            {
                ImageView first = new ImageView(puzz[i]);
                first.setFitHeight(CELL_SIZE);
                first.setFitWidth(CELL_SIZE);
                ImageView second = new ImageView(puzz[i]);
                second.setFitHeight(CELL_SIZE);
                second.setFitWidth(CELL_SIZE);

                tiles.add(new Tile(first));
                tiles.add(new Tile(second));
            }
            
            Collections.shuffle(tiles);
            for (int i = 0; i < tiles.size(); i++) {
                tile[i] = tiles.get(i);
                tile[i].setTranslateX(CELL_SIZE * (i % num_per_row) + 50);
                tile[i].setTranslateY(CELL_SIZE * (i / num_per_row) + 25);
                root.getChildren().add(tile[i]);
            }
    }


    class Tile extends StackPane 
    {
        ImageView image;

        Tile(ImageView image) 
        {
            this.image = image;
            Rectangle border = new Rectangle(CELL_SIZE, CELL_SIZE);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            getChildren().addAll(this.image, border);
            setOnMouseClicked(this::handleMouseClick);

            close();
        }

        public void handleMouseClick(MouseEvent event) 
        {
            if (isOpen() || clickCount == 0)
                return;
            clickCount--;

            if (selected == null) 
            {
                selected = this;
                open(() -> {});
            } 
            
            else 
            {
                open(() -> 
                {
                    if (!hasSameValue(selected)) 
                    {
                        selected.close();
                        this.close();
                    }

                    else
                    {
                        File f = new File("contents/win.wav");
                        MediaPlayer mp = new MediaPlayer(new Media(f.toURI().toString()));
                        mp.play();
                        mp.setVolume(0.8);
                        correct++;
                        //if all cards open
                        if (correct == 8) {timeline.pause(); stop=true;}
                    }
                    selected = null;
                    clickCount = 2;
                });
            }
        }


        public boolean isOpen() 
        {
            return image.getOpacity() == 1;
        }

        public void close() 
        {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), image);
            ft.setToValue(0);
            ft.play();
        }

        public void open(Runnable action) 
        {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), image);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            ft.play();
        }

        public boolean hasSameValue(Tile other) 
        {
            return image.getImage().equals(other.image.getImage());
        }
    }
}