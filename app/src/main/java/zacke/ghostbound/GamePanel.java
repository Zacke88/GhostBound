package zacke.ghostbound;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zacke on 2016-09-15.
 */
public class GamePanel extends SurfaceView implements Runnable {

    private Thread thread = null;
    private SurfaceHolder holder;
    boolean gameRunning = false;
    boolean firstRun = true;
    boolean floorActive = false;
    boolean decoyFloorActive = false;
    int fireX;
    int value;
    int randMin = 0;
    int randMax = 2;
    private Random rand = new Random();
    //private TextView scoreText = new TextView();

    private int gameFrame = 0;
    private int numOfFloors = 1;
    private int maxNumOfFires = 20;
    private int maxNumOfFloors = 10;



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
     * The run method which is the game loop that keeps running while the
     * player is active. Also handles the canvas to draw objects onto it
     */
    @Override
    public void run() {

        while(gameRunning) {

            startTime = System.nanoTime();

            //Checks if the surface is valid else it will restart the loop
            if(!holder.getSurface().isValid()) {
                continue;
            }
            //Locks the canvas to allow canvas drawing
            Canvas c = holder.lockCanvas();

            if(firstRun) {
                initiateGame(c);
                firstRun = false;
            }
            //Spawns decoy floors after after 500 frames
            if(gameFrame == 500) {
                floorActive = false;
                decoyFloorActive = true;
                gameFrame = 0;
            }

            // Spawns floors over the decoy floors that was placed 100 frames
            // ago
            if(gameFrame == 100 && decoyFloorActive) {
                decoyFloorActive = false;
                floorActive = true;
                gameFrame = 0;
            }

            // Removes all floors and creates a new floor pattern 50 frames
            // after the floors activated. Also creates another fire.
            if(gameFrame == 50 && floorActive) {
                decoyFloorActive = false;
                floorActive = false;
                gameFrame = 0;
                if(numOfFloors < maxNumOfFloors) {
                    numOfFloors++;
                }
                createFire(c);
                createFloor(c);
            }

            //Updates the game panel
            update(c, floorActive, decoyFloorActive);
            drawScore(c);

            //scoreText = player.getScore();

            holder.unlockCanvasAndPost(c);
            adjustFPS();
            gameFrame++;
            player.setScore(player.getScore()+1);
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

    /**
     * Creates a new Player for the game
     *
     * @param c The canvas which is drawn into
     */
    public void createPlayer(Canvas c) {
        Bitmap ghostImage = BitmapFactory.decodeResource(getResources(), R
                .drawable.ghost64);
        player = new Player(ghostImage, c);

    }

    /**
     * Creates a new Fire for the game
     *
     * @param c The canvas which is drawn into
     */
    public void createFire(Canvas c) {
        if(fires.size() < maxNumOfFires) {
            Bitmap fireImage = BitmapFactory.decodeResource(getResources(), R
                    .drawable.fire64);
            fires.add(new Fire(fireImage, c.getWidth(), c));
        }

    }

    /**
     * Creates a new Floor for the game
     *
     * @param c The canvas which is drawn into
     */
    public void createFloor(Canvas c) {
        Bitmap squareImage = BitmapFactory.decodeResource(getResources(), R
                .drawable.square64);
        floors.clear();

        for(int i = 0; i < numOfFloors; i++) {
            floors.add(new Floor(squareImage, c.getWidth(), c));
        }
        generateFloorPattern(c);

    }

    /**
     * Generates random positions for each floor to be drawn to
     *
     * @param c The canvas which is drawn into
     */
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

    /**
     * Initiates the game which creates the background, player, a fire and a
     * floor to start with
     *
     * @param c The canvas which is drawn into
     */
    public void initiateGame(Canvas c) {
        drawBackground(c);
        createPlayer(c);
        createFire(c);
        createFloor(c);
    }

    public void drawBackground(Canvas c) {
        c.drawColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    /**
     * Creates a new layout which holds a text view for the player score
     * which is drawn onto the canvas
     *
     * @param c Canvas to draw into
     */
    public void drawScore(Canvas c) {
        LinearLayout layout = new LinearLayout(this.getContext());
        TextView scoreText = new TextView(this.getContext());
        scoreText.setVisibility(View.VISIBLE);
        scoreText.setText("Score: "+ player.getScore());
        scoreText.setTextColor(getResources().getColor(R.color.colorWhite));
        scoreText.setTextSize(30);
        layout.addView(scoreText);
        layout.measure(c.getWidth(), c.getHeight());
        layout.layout(0, 0, c.getWidth(), c.getHeight());
        layout.draw(c);
    }

    /**
     * Updates the game panel and draws it
     *
     * @param c The canvas which is drawn into
     * @param floorActive if floors are active or not
     * @param decoyFloorActive if decoy floors are active or not
     */
    public void update(Canvas c, boolean floorActive, boolean
            decoyFloorActive) {
        drawBackground(c);
        player.update(c);
        player.draw(c);

        if(decoyFloorActive) {
            for (Floor floor : floors) {
                floor.drawDecoy(c);
            }
        }

        if(floorActive) {
            for (Floor floor : floors) {
                floor.draw(c);
                if(checkCollision(floor, player)) {
                    endGame();
                }
            }
        }

        for (Fire fire : fires) {
            fire.update(c);
            fire.draw(c);
            if(checkCollision(fire, player)) {
                endGame();
            }
        }

    }

    /**
     * Checks two gameobjects to see if they intersects
     *
     * @param object an object
     * @param player the player object
     * @return true if objects intersect else false
     */
    public boolean checkCollision(GameObject object, GameObject player) {
        if (Rect.intersects(object.getRectangle(), player.getRectangle())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Stops the game thread and starts the game over activity
     */
    public void endGame() {
        Intent intent = new Intent(getContext(), GameOverActivity.class);
        intent.putExtra("Score", player.getScore());
        ((GameActivity)getContext()).startActivity(intent);
        /*
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }

    /**
     * Pauses the game which stops the game loop from running
     */
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

    /**
     * Resumes the game which resumes the game loop which was paused
     */
    public void resume() {
        gameRunning = true;
        thread = new Thread(this);
        thread.start();
    }

}
