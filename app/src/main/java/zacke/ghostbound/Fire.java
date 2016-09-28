package zacke.ghostbound;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Class which represents the fire object. Creates a scaled bitmap of the
 * image to make it adjust to different screen sizes. A new fire object
 * generates a random speed and start position and is able to update the
 * position of the object.
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class Fire extends GameObject {

    private int speed;
    private int canvasWidth;
    private int canvasHeight;
    private Random r = new Random();

    public Fire(int canvasWidth, int canvasHeight, Context context) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        imageSize = (canvasWidth / 10);
        Bitmap bitmapImage = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.fire64);
        super.image = Bitmap.createScaledBitmap(bitmapImage, imageSize,
                imageSize, false);

        initiateFire();
    }

    /**
     * Generates a position and speed for a new fire object.
     */
    public void initiateFire() {
        x = generateFirePosX(image);
        y = 0;
        speed = generateSpeed();
    }

    /**
     * Generates a random position for a fire to spawn within the canvas region.
     *
     * @param image Image to generate random x position for.
     * @return generated x position.
     */
    public int generateFirePosX(Bitmap image) {
        int randMin = 0;
        int randMax = (canvasWidth - image.getWidth());
        return r.nextInt(randMax - randMin + 1) + randMin;
    }

    /**
     * Generates a random speed for a new fire.
     *
     * @return the speed value generated.
     */
    public int generateSpeed() {
        int baseSpeed = 4;
        int maxSpeed = 20;
        int speed = r.nextInt(maxSpeed - baseSpeed + 1) + baseSpeed;
        return speed;
    }

    /**
     * Updates the position of a fire object based on its speed.
     */
    public void update() {
        if (y < canvasHeight) {
            y += speed;
        } else {
            initiateFire();
        }
    }
}
