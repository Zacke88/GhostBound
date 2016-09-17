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
    int fireX;
    int value;
    int randMin = 0;
    int randMax = 2;
    private Random rand = new Random();

    private int runCount = 0;
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
    private Fire fire;
    private Floor floor;
    private List<Fire> fires = new ArrayList<Fire>();
    private List<Floor> floors = new ArrayList<Floor>();

    public GamePanel(Context context) {
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

            if(firstRun) {
                initiateGame(c);
                firstRun = false;
            }
            if(runCount == newFire) {
                createFire(c);
                runCount = 0;
            }

            createFloor(c);

            update(c);

            holder.unlockCanvasAndPost(c);

            adjustFPS();
            runCount++;
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
                .drawable.ghosticon64);
        player = new Player(ghostImage, c.getWidth()/2, c.getHeight()/2);

    }

    public void createFire(Canvas c) {
        Bitmap fireImage = BitmapFactory.decodeResource(getResources(), R
                .drawable.flame64);
        fires.add(new Fire(fireImage, player.getScore(), c.getWidth()));

    }

    public void createFloor(Canvas c) {
        Bitmap fireImage = BitmapFactory.decodeResource(getResources(), R
                .drawable.square64);

        for(int i = 0; i < 4; i++) {
            floors.add(new Floor(fireImage, c.getWidth()));
        }
        generateFloorPattern();

    }

    public void generateFloorPattern() {
        value = rand.nextInt(randMax - randMin + 1) + randMin;
        int i = 0;

        if(value == 0) {
            for (Floor floor : floors) {
                int x = floor.getWidth()/2;
                int y = floor.getHeight()/2;
                floor.setX(x+floor.getWidth()*i);
                floor.setY(y);
                i++;
            }
        }
        if(value == 1) {
            for (Floor floor : floors) {
                int x = floor.getWidth()/2;
                int y = floor.getHeight()/2;
                floor.setX(x+floor.getWidth()*i);
                floor.setY(y);
                i++;
            }
        }
        if(value == 2) {
            for (Floor floor : floors) {
                int x = floor.getWidth()/2;
                int y = floor.getHeight()/2;
                floor.setX(x+floor.getWidth()*i);
                floor.setY(y);
                i++;
            }
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

    public void update(Canvas c) {
        drawBackground(c);
        player.update(c);
        player.draw(c);

        for (Floor floor : floors) {
            floor.draw(c);
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
