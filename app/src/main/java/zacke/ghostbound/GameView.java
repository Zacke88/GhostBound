package zacke.ghostbound;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

/**
 * Created by Zacke on 2016-09-15.
 */
public class GameView extends SurfaceView implements Runnable {

    Thread thread = null;
    SurfaceHolder holder;
    boolean gameRunning = false;
    Bitmap fire = BitmapFactory.decodeResource(getResources(), R.drawable
            .flame64);
    int x = 0;
    int y = 0;
    int fireX;

    Paint p = new Paint();

    boolean firstRun = true;




    long timeMillis;
    long waitTime;
    long startTime;
    //long totalTime = 0;
    //long frameCount = 0;

    int FPS = 30;
    long targetTime = 1000/FPS;
    double avgFPS;

    public GameView(Context context) {
        super(context);
        holder = getHolder();
    }

    @Override
    public void run() {

        while(gameRunning) {

            startTime = System.nanoTime();

            //perform canvas drawing
            if(!holder.getSurface().isValid()) {
                continue;
            }

            Canvas c = holder.lockCanvas();

            drawGame(c);

            updateCoords(c);

            holder.unlockCanvasAndPost(c);

            adjustFPS();
        }

    }

    public void updateCoords(Canvas c) {
        if(y < c.getHeight()) {
            y += 5;
        } else {
            y = 0;
        }
    }

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



            /* //TODO TO PRINT FPS
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

    public int generateFirePosX(Canvas c) {
        int randMin = 0;
        int randMax = 4;
        int value;
        Random r = new Random();
        value = r.nextInt(randMax - randMin + 1) + randMin;
        return Math.round((value*(c.getWidth()-fire.getWidth())/randMax));
    }

    public void drawGame(Canvas c) {
        if(firstRun) {
            fireX = generateFirePosX(c);
            firstRun = false;
        }
        c.drawColor(getResources().getColor(R.color.colorPrimaryDark));
        c.drawBitmap(fire, fireX, y, p);
    }


    public void pause() {
        gameRunning = false;
        while(true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        thread = null;
    }

    public void resume() {
        gameRunning = true;
        thread = new Thread(this);
        thread.start();
    }

}
