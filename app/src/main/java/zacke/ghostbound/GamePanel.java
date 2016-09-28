package zacke.ghostbound;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Class which represents the game and acts as the game view. When the
 * surface is created it creates a new game thread which keeps running while
 * the game is active. When the game is paused or the player looses the
 * thread gets terminated.
 *
 * This class acts as a surface view which updates all the game objects and is
 * also drawing each object onto the canvas.
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
    private int floorFrames = 50;
    private int decoyFrames = 100;
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

    /**
     * Initiates the game first time the game panel is created.
     */
    public void initiateGame() {
        createPlayer();
        createFire();
        createFloor();
    }

    /**
     * Creates a new player object for the game.
     */
    public void createPlayer() {
        player = new Player(canvasWidth, canvasHeight, getContext());
    }

    /**
     * Creates a new fire object for the game.
     */
    public void createFire() {
        if (fires.size() < maxNumOfFires) {
            fires.add(new Fire(canvasWidth, canvasHeight, getContext()));
        }
    }

    /**
     * Creates a new Floor for the game. Also generates a new floor pattern
     * for all floors.
     */
    public void createFloor() {

        floors.clear();

        for (int i = 0; i < startNumOfFloors; i++) {
            floors.add(new Floor(canvasWidth, getContext()));
        }
        generateFloorPattern();
    }

    /**
     * Generates random positions for each floor to be drawn to.
     */
    public void generateFloorPattern() {
        for (Floor floor : floors) {
            int xyMin = 0;
            int xMax = canvasWidth - floor.image.getWidth();
            int yMax = canvasHeight - floor.image.getHeight();
            int x = rand.nextInt(xMax - xyMin + 1) + xyMin;
            int y = rand.nextInt(yMax - xyMin + 1) + xyMin;
            floor.setX(x);
            floor.setY(y);
        }
    }

    /**
     * Updates the game objects and checks if the player has collided with
     * fires or active floors. ALso updates the player score each iteration.
     */
    public void update() {
        if (firstRun) {
            initiateGame();
            firstRun = false;
        }

        spawnObject();

        // Check floor collision if the floors are active
        for (Floor floor : floors) {
            if (floorActive && (checkCollision(floor, player))) {
                endGame();
            }
        }
        //Update player position
        player.update();
        //Update fire positions and check for collision
        for (Fire fire : fires) {
            fire.update();
            if (checkCollision(fire, player)) {
                endGame();
            }
        }
        player.setScore(player.getScore() + 1);
    }

    /**
     * Spawns new fires and floors into the game based on how many frames has
     * passed.
     */
    public void spawnObject() {
        // Spawns floors over the decoy floors
        if (gameFrame == decoyFrames && decoyFloorActive) {
            decoyFloorActive = false;
            floorActive = true;
            gameFrame = 0;
        }
        // Removes all floors and creates a new floor pattern and spawns
        // another fire.
        if (gameFrame == floorFrames && floorActive) {
            decoyFloorActive = false;
            floorActive = false;
            if (startNumOfFloors < maxNumOfFloors) {
                startNumOfFloors++;
            }
            createFire();
            createFloor();
            gameFrame = 0;
        }
        //Spawns decoy floors
        if (gameFrame == decoyFrames) {
            floorActive = false;
            decoyFloorActive = true;
            gameFrame = 0;
        }
        gameFrame++;
    }

    /**
     * Draws the background color for the game.
     *
     * @param canvas Canvas to draw onto.
     */
    public void drawBackground(Canvas canvas) {
        canvas.drawColor(getContext().getResources().getColor(R.color
                .colorPrimaryDarker));
    }

    /**
     * Creates a new layout which holds a text view for the player score
     * which is drawn onto the canvas.
     *
     * @param score  The player score.
     * @param canvas Canvas to draw onto.
     */
    public void drawScore(int score, Canvas canvas) {
        LinearLayout layout = new LinearLayout(this.getContext());
        TextView scoreText = new TextView(this.getContext());
        scoreText.setVisibility(View.VISIBLE);
        scoreText.setText("Score: " + score);
        scoreText.setTextColor(getResources().getColor(R.color.colorWhite));
        scoreText.setTextSize(30);
        layout.addView(scoreText);
        layout.measure(canvasWidth, canvasHeight);
        layout.layout(0, 0, canvasWidth, canvasHeight);
        layout.draw(canvas);
    }

    /**
     * Method for drawing an object onto the canvas.
     *
     * @param canvas Canvas to draw onto.
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // Draws the canvas background
        drawBackground(canvas);
        // Draws floors or decoy floors if they are active
        Paint paint = new Paint();
        if (decoyFloorActive) {
            // Lower alpha for decoy floors
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

    /**
     * Method for drawing an object onto the canvas.
     *
     * @param object Object to be drawn.
     * @param paint  Paint for the object.
     * @param canvas Canvas to be drawn onto.
     */
    public void drawObject(GameObject object, Paint paint, Canvas canvas) {
        canvas.drawBitmap(object.getImage(), object.getX(), object.getY(),
                paint);
    }

    /**
     * Checks two game objects to see if they intersects each other.
     *
     * @param object an game object.
     * @param player the player object.
     * @return true if objects intersect else false.
     */
    public boolean checkCollision(GameObject object, GameObject player) {
        if (Rect.intersects(object.getRectangle(), player.getRectangle())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Terminates the game thread and starts the game over activity
     */
    public void endGame() {
        gameThread.setRunning(false);
        while (true) {
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Intent intent = new Intent(getContext(), GameOverActivity.class);
        intent.putExtra("score", String.valueOf(player.getScore()));
        getContext().startActivity(intent);
    }

    /**
     * Method used to update the canvas size with width and height.
     *
     * @param canvas canvas which is drawn onto.
     */
    public void setCanvasSize(Canvas canvas) {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
    }

    /**
     * When a new surface is created it starts a new game thread.
     *
     * @param holder the surface for the game.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * When the surface is destroyed it terminates the game thread.
     *
     * @param holder the surface for the game.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        gameThread.setRunning(false);

        while (true) {
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
