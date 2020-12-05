// package sample;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.*;

public class Main2 extends Application {
    private final int num_of_pairs = 8;
    private final int num_per_row = 4;
    private Tile selected = null;
    private int clickCount = 2;
    private Tile[] tile = new Tile[16];
    private Pane root = new Pane();
    private Scene ss=new Scene(root, 800, 550);

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        ArrayList<Tile> tiles = new ArrayList<>();


        Image[] puzz = new Image[num_of_pairs];

        for (int i = 0; i < puzz.length; i++) {
            puzz[i] = new Image(new File(i + ".jpeg").toURI().toString());
        }


        for (int i = 0; i < num_of_pairs; i++) 
        {
            ImageView first = new ImageView(puzz[i]);
            first.setFitHeight(120);
            first.setFitWidth(120);

            ImageView second = new ImageView(puzz[i]);
            second.setFitHeight(120);
            second.setFitWidth(120);

            tiles.add(new Tile(first));
            tiles.add(new Tile(second));
        }


        Collections.shuffle(tiles);

        for (int i = 0; i < tiles.size(); i++)
        {
            tile[i] = tiles.get(i);

            tile[i].setTranslateX(120 * (i % num_per_row));
            tile[i].setTranslateY(120 * (i / num_per_row));

            root.getChildren().add(tile[i]);
        }
        Button play = new Button("play");


        play.setOnMouseClicked(n -> 
        {

            for (int i = 0; i < tiles.size(); i++) 
            {
                tile[i].close();
                root.getChildren().remove(tile[i]);
            }

            Collections.shuffle(tiles);

            for (int i = 0; i < tiles.size(); i++) 
            {
                tile[i] = tiles.get(i);

                tile[i].setTranslateX(120 * (i % num_per_row));
                tile[i].setTranslateY(120 * (i / num_per_row));

                root.getChildren().remove(tile[i]);
                root.getChildren().add(tile[i]);
            }
        });

        root.getChildren().add(play);
        primaryStage.setScene(ss);
        primaryStage.show();
    }


    public static void main(String[] args)
        {launch(args);}


    public void refresh() 
    {
    }


    class Tile extends StackPane 
    {
        ImageView image;

        Tile(ImageView image) 
        {
            this.image = image;
            Rectangle border = new Rectangle(120, 120);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            getChildren().addAll(this.image, border);
            setOnMouseClicked(this::handleMouseClick);

            close();
        }

        public void handleMouseClick(MouseEvent event) 
        {
            File alertF = new File("select.wav");
            MediaPlayer alert = new MediaPlayer(new Media(alertF.toURI().toString()));
            alert.play();
            alert.setVolume(0.8);

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
                        File f = new File("win.wav");
                        MediaPlayer mp = new MediaPlayer(new Media(f.toURI().toString()));
                        mp.play();
                        mp.setVolume(0.8);
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