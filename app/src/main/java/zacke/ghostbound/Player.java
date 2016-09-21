package zacke.ghostbound;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Zacke on 2016-09-15.
 */
public class Player extends GameObject {
    private int score;
    private AccelerometerSensor accSensor;


    public Player(Bitmap image, Canvas c, Context context) {
        accSensor = new AccelerometerSensor(context);
        imageSize = (c.getWidth() / 10);
        x = ((c.getWidth() - imageSize) / 2);
        y = ((c.getHeight() - imageSize) / 2);
        score = 0;
        super.image = Bitmap.createScaledBitmap(
                image, imageSize, imageSize, false);
    }

    public void update(Canvas c) {
        x += accSensor.getdX();
        if(x > (c.getWidth() - imageSize)) {
            x = (c.getWidth() - imageSize);
        }
        if(x < 0) {
            x = 0;
        }
        y += accSensor.getdY();
        if(y > (c.getHeight() - imageSize)) {
            y = (c.getHeight() - imageSize);
        }
        if(y < 0) {
            y = 0;
        }

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
