package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.animation.Animation;

import java.util.Random;

/**
 * Created by Zacke on 2016-09-15.
 */
public class Fire extends GameObject {

    private int score;
    private int speed;
    private int speedCap = 30;
    private int baseSpeed = 5;
    private int gameWidth;
    private Random rand = new Random();
    private Bitmap image;


    public Fire(Bitmap image, int s, int gameWidth) {
        this.gameWidth = gameWidth;
        score = s;
        this.image = image;

        initiateFire();


    }

    public void initiateFire() {
        x = generateFirePosX(image, gameWidth);
        y = 0;
        speed = generateSpeed();
    }

    public int generateFirePosX(Bitmap image, int gameWidth) {
        int randMin = 0;
        int randMax = 4;
        int value;
        Random r = new Random();
        value = r.nextInt(randMax - randMin + 1) + randMin;
        return Math.round((value*(gameWidth-image.getWidth())/randMax));
    }

    public int generateSpeed() {
        int speed = (baseSpeed + (int) (rand.nextDouble()*score/100));
        if(speed >= speedCap) {
            speed = speedCap;
        }
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

    @Override
    public int getWidth() {
        return width-10;
    }
}
