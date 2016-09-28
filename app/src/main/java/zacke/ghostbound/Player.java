package zacke.ghostbound;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Class which represents the player object. Creates a scaled bitmap of the
 * image to make it adjust to different screen sizes. Updates the position of
 * the player object with an accelerometer sensor. Also holds the player score.
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class Player extends GameObject {
    private int score;
    private int canvasWidth;
    private int canvasHeight;
    private AccelerometerSensor accSensor;

    public Player(int canvasWidth, int canvasHeight, Context context) {
        accSensor = new AccelerometerSensor(context);
        imageSize = (canvasWidth / 10);
        x = ((canvasWidth - imageSize) / 2);
        y = ((canvasHeight - imageSize) / 2);
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        score = 0;
        Bitmap bitmapImage = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.ghost64);
        super.image = Bitmap.createScaledBitmap(bitmapImage, imageSize, imageSize,
                false);
    }

    /**
     * Updates the player position which is updated by the accelerometer sensor.
     */
    public void update() {
        x += accSensor.getdX();
        if (x > (canvasWidth - imageSize)) {
            x = (canvasWidth - imageSize);
        }
        if (x < 0) {
            x = 0;
        }
        y += accSensor.getdY();
        if (y > (canvasHeight - imageSize)) {
            y = (canvasHeight - imageSize);
        }
        if (y < 0) {
            y = 0;
        }
    }

    /**
     * Getter for the score.
     *
     * @return Player score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for the score.
     *
     * @param score Player score.
     */
    public void setScore(int score) {
        this.score = score;
    }


}
