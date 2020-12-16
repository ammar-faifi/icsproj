
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
import java.io.*;
import java.util.*;


public class Game extends Application 
{
    private final int num_of_pairs = 8;
    private final int num_per_row = 4;
    private final int CELL_SIZE = 140;
    private int clickCount = 2;
    private Tile selected = null;
    private HBox topPane = new HBox(40);
    private StackPane allPanes = new StackPane();
    private BorderPane mainPane = new BorderPane();
    private Scene ss = new Scene(allPanes, Home.WIDTH, Home.HEIGHT);
    private int startSecond;
    private int startMinute;
    private int currentScore;
    private int correct;
    private int highScore;
    private Label score ;
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

        // Check the last high score
        File highscoreinput = new File("highscore.txt");
        if (highscoreinput.exists())
        {
            Scanner in = new Scanner(highscoreinput);
            highScore = in.nextInt();
            in.close();
        }
    	else highScore = 0;

        //Create images
        for (int i = 0; i < puzz.length; i++)
        puzz[i] = new Image("contents/" + i + ".jpeg");   
        refresh(true);

        //play again button
        Button play = new Button("Play again");
        play.setId("play-button");
        play.setOnMouseClicked(n -> 
        {
            correct = 0;
            stop = true;
            timeline.play();

            for (int i = 0; i < tiles.size(); i++) 
            root.getChildren().remove(tile[i]);
            
            refresh(false);
        });

        // Header: time scores buttons
        score = new Label("Your Score:" + currentScore + "  High Score: "+ highScore);
        Label time = new Label("Time:" + startMinute + ":" + startSecond);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> 
        {
            if (stop) {
                startSecond = 0;
                startMinute = 0;
                hideAll();
                stop = false;
                
            } 
            //increment time
            else {
                if (startSecond == 59) 
                {
                    startSecond = 0;
                    startMinute++;
                } 
                else startSecond++;
            }
            // update time
            time.setText("Time:" + startMinute + ":" + startSecond);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        //Pause button
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
        homeScene.getStylesheets().add("contents/style.css");
        ss.getStylesheets().add(new File("contents/style.css").toURI().toString());

        primaryStage.setScene(homeScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args)
    {launch(args);}

    //set images and locate them randomly
    public void refresh(boolean first_time) 
    {   
        if (first_time)
        {
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
        } 
        Collections.shuffle(tiles);
        for (int i = 0; i < tiles.size(); i++) {
            tile[i] = tiles.get(i);
            tile[i].setTranslateX(CELL_SIZE * (i % num_per_row) + 50);
            tile[i].setTranslateY(CELL_SIZE * (i / num_per_row) + 25);
            tile[i].open(() ->{});
            root.getChildren().add(tile[i]);
        }
        
    }

    //to hide all tiles
    public void hideAll()
    {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < tiles.size(); i++)    
        {         
            tiles.get(i).close();
        }
    }

    public void updateScores()
    {
        score.setText("Your Score:" + currentScore + "   High Score: "+ highScore);
    }

    //Tile Class
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
                        currentScore -= 2;
                        updateScores();
                    }

                    else
                    {
                        MediaPlayer mp = new MediaPlayer(new Media(new File("contents/win.wav").toURI().toString()));
                        mp.play();
                        mp.setVolume(0.8);
                        correct++;
                        currentScore += 10;
                        updateScores();
                        
                        //if all cards are open
                        if (correct == 8) {
                            if(currentScore > highScore) {
                                highScore = currentScore;
                                
                                //write the high score
                                try (PrintWriter pr = new PrintWriter(new File("highscore.txt")))
                                {
                                    pr.print(highScore);
                                    currentScore = 0;
                                } catch (FileNotFoundException e) 
                                {
                                    System.out.println(e);
								}
                            }
                            updateScores();
                            timeline.stop();
                        }
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