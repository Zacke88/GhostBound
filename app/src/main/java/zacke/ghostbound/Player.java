package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.animation.Animation;

/**
 * Created by Zacke on 2016-09-15.
 */
public class Player extends GameObject {
    private Bitmap image;
    private int score;
    private double dya;
    private double dxa;
    private boolean playing;
    private long startTime;

    public Player(Bitmap image, int w, int h) {
        x = 500;
        y = 500;
        dy = dx = 0;
        height = h;
        width = w;
        this.image = image;

        startTime = System.nanoTime();
    }

    public void update(Canvas c) {
        long elapsedTime = (System.nanoTime()-startTime)/1000000;
        if(elapsedTime>100)
        {
            score++;
            startTime = System.nanoTime();
        }
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
    public int getScore() {
        return score;
    }
    public boolean getPlaying() {
        return playing;
    }
    public void setPlaying(boolean b) {
        playing = b;
    }
}
