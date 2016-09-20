package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.animation.Animation;

import java.util.Random;

/**
 * Created by Zacke on 2016-09-15.
 */
public class Fire extends GameObject {

    private int speed;
    private int gameWidth;
    private Random r = new Random();



    public Fire(Bitmap image, int gameWidth, Canvas c) {
        this.gameWidth = gameWidth;
        imageSize = c.getWidth()/10;
        super.image = Bitmap.createScaledBitmap(
                image, imageSize, imageSize, false);

        initiateFire();


    }

    public void initiateFire() {
        x = generateFirePosX(image, gameWidth);
        y = 0;
        speed = generateSpeed();
    }

    public int generateFirePosX(Bitmap image, int gameWidth) {
        int randMin = 0;
        int randMax = (gameWidth-image.getWidth());
        return r.nextInt(randMax - randMin + 1) + randMin;
    }

    public int generateSpeed() {
        int baseSpeed = 4;
        int maxSpeed = 16;
        int speed = r.nextInt(maxSpeed - baseSpeed + 1) + baseSpeed;
        return speed;
    }

    public void update(Canvas c) {
        if(y < c.getHeight()) {
            y += speed;
        } else {
            initiateFire();
        }
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

}
