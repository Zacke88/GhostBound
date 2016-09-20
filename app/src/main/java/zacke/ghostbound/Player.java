package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.animation.Animation;

/**
 * Created by Zacke on 2016-09-15.
 */
public class Player extends GameObject {
    private int score;
    private boolean playing;
    private boolean isAlive = true;
    private int speed;

    public Player(Bitmap image, Canvas c) {
        imageSize = c.getWidth()/10;
        x = ((c.getWidth()-imageSize)/2);
        y = ((c.getHeight()-imageSize)/2) ;
        score = 0;
        super.image = Bitmap.createScaledBitmap(
                image, imageSize, imageSize, false);
    }

    public void update(Canvas c) {

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
    public boolean getPlaying() {
        return playing;
    }
    public void setPlaying(boolean b) {
        playing = b;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean Alive) {
        isAlive = Alive;
    }
}
