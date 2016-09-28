package zacke.ghostbound;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class which represents the game and acts as the game view. It has a thread
 * that keeps running while the application is active or until the player
 * looses.
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class GameThread extends Thread {

    boolean gameRunning = false;
    boolean firstRun = true;

    private GamePanel view;
    private SurfaceHolder surface;
    int FPS = 30;
    long timeMillis;
    long waitTime;
    long startTime;
    long targetTime = 1000/FPS;
    /* Use to print avarage FPS
    long totalTime = 0;
    long frameCount = 0; */

    public GameThread(GamePanel view) {
        this.view = view;
        surface = view.getHolder();
    }

    /**
     * The run method which is the game loop that keeps running while the
     * game is active. Also handles the canvas to draw objects onto it
     */
    @Override
    public void run() {
        Log.e("msg", "1");

        //TODO while game running active
        while(gameRunning) {

            startTime = System.nanoTime();

            //Checks if the surface is valid else it will restart the loop
            if(!surface.getSurface().isValid()) {
                continue;
            }

            //Locks the canvas to allow canvas drawing
            Canvas canvas = surface.lockCanvas();
            if(firstRun) {
                view.setCanvasSize(canvas);
                firstRun = false;
            }
            view.update();
            if(canvas != null) {
                view.draw(canvas);
                surface.unlockCanvasAndPost(canvas);
            }

            adjustFPS();
        }

    }

    /**
     * Sleeps the game thread for a certain amount of time to make it adjust
     * to the set game FPS
     */
    public void adjustFPS() {

        timeMillis = ((System.nanoTime() - startTime) / 1000000);
        waitTime = (targetTime - timeMillis);
        if(waitTime < 0) {
            waitTime = 0;
        }
        try{
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //  Use to print avarage FPS to console
        /*
            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS) {
                avgFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                Log.e("FPS", String.valueOf(avgFPS));

            }
            */
    }

    /**
     * Stes the game running boolean to true or false
     *
     * @param running Boolean for if the game loop should run
     */
    public void setRunning(Boolean running) {
        gameRunning = running;
    }

}
