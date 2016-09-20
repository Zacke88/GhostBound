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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zacke on 2016-09-15.
 */
public class GamePanel extends SurfaceView implements Runnable {

    Thread thread = null;
    SurfaceHolder holder;
    boolean gameRunning = false;
    boolean firstRun = true;
    boolean floorActive = false;
    boolean decoyFloorActive = false;
    int fireX;
    int value;
    int randMin = 0;
    int randMax = 2;
    private Random rand = new Random();

    private int runCount = 0;
    private int gameFrame = 0;
    int newFire = 200;



    long timeMillis;
    long waitTime;
    long startTime;
    //long totalTime = 0;
    //long frameCount = 0;

    int FPS = 30;
    long targetTime = 1000/FPS;
    double avgFPS;

    private Player player;
    private List<Fire> fires = new ArrayList<>();
    private List<Floor> floors = new ArrayList<>();


    public GamePanel(Context context) {
        super(context);
        holder = getHolder();
    }

    /**
     * The run method which is a loop that keeps running while the player is
     * active and pauses while the application is not active.
     */
    @Override
    public void run() {

        while(gameRunning) {

            startTime = System.nanoTime();

            //perform canvas drawing
            if(!holder.getSurface().isValid()) {
                continue;
            }

            Canvas c = holder.lockCanvas();

            if(firstRun) {
                initiateGame(c);
                firstRun = false;
            }
            if(runCount == newFire) {
                createFire(c);
                runCount = 0;
            }
            if(gameFrame == 500) {
                floorActive = false;
                decoyFloorActive = true;
                gameFrame = 0;
            }

            if(gameFrame == 100 && decoyFloorActive) {
                decoyFloorActive = false;
                floorActive = true;
                gameFrame = 0;
            }

            if(gameFrame == 50 && floorActive) {
                decoyFloorActive = false;
                floorActive = false;
                gameFrame = 0;
            }

            if(gameFrame % 100 == 0) {
                //TODO ADD THIS
                // deleteFloor(c);
            }

            update(c, floorActive, decoyFloorActive);

            holder.unlockCanvasAndPost(c);

            adjustFPS();
            runCount++;
            gameFrame++;
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

    public void createPlayer(Canvas c) {
        Bitmap ghostImage = BitmapFactory.decodeResource(getResources(), R
                .drawable.ghost64);
        player = new Player(ghostImage, c.getWidth()/2, c.getHeight()/2);

    }

    public void createFire(Canvas c) {
        Bitmap fireImage = BitmapFactory.decodeResource(getResources(), R
                .drawable.fire64);
        fires.add(new Fire(fireImage, player.getScore(), c.getWidth()));

    }

    public void createFloor(Canvas c) {
        Bitmap squareImage = BitmapFactory.decodeResource(getResources(), R
                .drawable.square64);
        floors.clear();

        for(int i = 0; i < 10; i++) {
            floors.add(new Floor(squareImage, c.getWidth()));
        }
        generateFloorPattern(c);

    }

    public void generateFloorPattern(Canvas c) {
        for (Floor floor : floors) {
            int xyMin = 0;
            int xMax = c.getWidth()-floor.image.getWidth();
            int yMax = c.getHeight()-floor.image.getHeight();
            int x = rand.nextInt(xMax - xyMin + 1) + xyMin;
            int y = rand.nextInt(yMax - xyMin + 1) + xyMin;
            floor.setX(x);
            floor.setY(y);
        }
    }

    public void initiateGame(Canvas c) {
        drawBackground(c);
        createPlayer(c);
        createFire(c);
        createFloor(c);
    }

    public void drawBackground(Canvas c) {
        c.drawColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public void update(Canvas c, boolean FloorActive, boolean
            decoyFloorActive) {
        drawBackground(c);
        player.update(c);
        player.draw(c);

        if(decoyFloorActive) {
            for (Floor floor : floors) {
                floor.drawDecoy(c);
            }
        }

        if(FloorActive) {
            for (Floor floor : floors) {
                floor.draw(c);
            }
        }

        for (Fire fire : fires) {
            fire.update(c);
            fire.draw(c);
        }
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
