/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ecosystemfx;

import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author shanemendez
 */
public class EcosystemFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        int gen = 0;
        final Object LOCK = new Object();
        primaryStage.setWidth(800);
        primaryStage.setHeight(500);
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(primaryStage.getWidth(), primaryStage.getHeight());
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.RED);

        Environment e = new Environment(canvas, 1000);
        e.spawn(1000, new Vector((float) canvas.getWidth() / 2, (float) canvas.getHeight() / 2));

//        scene.setOnMouseMoved(me -> e.target.set((float) me.getX(), (float) me.getY()));
        new AnimationTimer() {

            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                synchronized(LOCK){
                    e.update();
                }
                gc.setFill(Color.BLACK);
                gc.fillRect(e.target.x + 10, e.target.y + 10, 10, 10);
            }

        }.start();

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                e.update(c -> c.getFitness());
            }
        }, 0, 200);
        
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                synchronized(LOCK){
                    
                e.repopulate(e.pickBest());
                }
            }
        },
        0, 2000);
        primaryStage.setTitle("Stage");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        launch(args);
        Vector v = new Vector(0,0);
        Vector v2 = new Vector(0,0);
        System.out.println(v.equals(v2));
        
    }

}
