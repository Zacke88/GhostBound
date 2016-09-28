package zacke.ghostbound;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class which represents the game and acts as the game view. It has a thread
 * that keeps running while the application is active or until the player
 * looses.
 *
 *
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    boolean firstRun = true;
    boolean floorActive = false;
    boolean decoyFloorActive = false;
    private int gameFrame = 0;
    private int startNumOfFloors = 1;
    private int maxNumOfFloors = 10;
    private int maxNumOfFires = 20;
    private Player player;
    private List<Fire> fires = new ArrayList<>();
    private List<Floor> floors = new ArrayList<>();
    private Random rand = new Random();
    private int canvasWidth;
    private int canvasHeight;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public void initiateGame() {
        createPlayer();
        createFire();
        createFloor();
    }

    /**
     * Creates a new Player for the game
     *
     */
    public void createPlayer() {
        player = new Player(canvasWidth, canvasHeight, getContext());

    }

    /**
     * Creates a new Fire for the game
     *
     */
    public void createFire() {
        if(fires.size() < maxNumOfFires) {
            fires.add(new Fire(canvasWidth, canvasHeight, getContext()));
        }
    }

    /**
     * Creates a new Floor for the game
     *
     */
    public void createFloor() {

        floors.clear();

        for(int i = 0; i < startNumOfFloors; i++) {
            floors.add(new Floor(canvasWidth, getContext()));
        }
        generateFloorPattern();

    }

    /**
     * Generates random positions for each floor to be drawn to
     *
     */
    public void generateFloorPattern() {
        for (Floor floor : floors) {
            int xyMin = 0;
            int xMax = canvasWidth-floor.image.getWidth();
            int yMax = canvasHeight-floor.image.getHeight();
            int x = rand.nextInt(xMax - xyMin + 1) + xyMin;
            int y = rand.nextInt(yMax - xyMin + 1) + xyMin;
            floor.setX(x);
            floor.setY(y);
        }
    }

    public void update() {
        if(firstRun) {
            initiateGame();
            firstRun = false;
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
            if(startNumOfFloors < maxNumOfFloors) {
                startNumOfFloors++;
            }
            createFire();
            createFloor();
            gameFrame = 0;

        }
        //Spawns decoy floors after after 500 frames
        if(gameFrame == 100) {
            floorActive = false;
            decoyFloorActive = true;
            gameFrame = 0;
        }

        // Draws the floors if they are active and checks for collision with
        // player if floors are active
        for (Floor floor : floors) {
            if(floorActive && (checkCollision(floor, player))) {
                endGame();
            }
        }

        //Draws the player
        player.update();

        //Draws the fires and checks for collision with player
        for (Fire fire : fires) {
            fire.update();
            if(checkCollision(fire, player)) {
                endGame();
            }
        }

        player.setScore(player.getScore()+1);
        gameFrame++;
    }

    public void drawBackground(Canvas canvas) {
            canvas.drawColor(getContext().getResources().getColor(R.color
                    .colorPrimaryDarker));
    }

    /**
     * Creates a new layout which holds a text view for the player score
     * which is drawn onto the canvas
     */
    public void drawScore(int score, Canvas canvas) {
        LinearLayout layout = new LinearLayout(this.getContext());
        TextView scoreText = new TextView(this.getContext());
        scoreText.setVisibility(View.VISIBLE);
        scoreText.setText("Score: "+ score);
        scoreText.setTextColor(getResources().getColor(R.color.colorWhite));
        scoreText.setTextSize(30);
        layout.addView(scoreText);
        layout.measure(canvasWidth, canvasHeight);
        layout.layout(0, 0, canvasWidth, canvasHeight);
        layout.draw(canvas);
    }

    /**
     * Method for drawing an object onto the canvas
     *
     * @param canvas Canvas to draw onto
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draws the canvas background
        drawBackground(canvas);

        // Draws floors or decoy floors if they are active
        Paint paint = new Paint();
        if (decoyFloorActive) {
            paint.setAlpha(35);
        }
        for (Floor floor : floors) {
            if (decoyFloorActive || floorActive) {
                drawObject(floor, paint, canvas);
            }
        }

        //Draws the player
        drawObject(player, null, canvas);

        //Draws the fires
        for (Fire fire : fires) {
            drawObject(fire, null, canvas);
        }

        //Draws the player score
        drawScore(player.getScore(), canvas);



    }

    public void drawObject(GameObject object, Paint paint, Canvas canvas) {
        canvas.drawBitmap(object.getImage(), object.getX(), object.getY(),
                paint);
    }

    /**
     * Checks two game objects to see if they intersects
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
        gameThread.setRunning(false);
        Intent intent = new Intent(getContext(), GameOverActivity.class);
        intent.putExtra("score", String.valueOf(player.getScore()));
        getContext().startActivity(intent);
    }


    public void setCanvasSize(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        gameThread.setRunning(false);

        while(true) {
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
