package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Zacke on 2016-09-17.
 */
public class DecoyFloor extends GameObject {

    private int gameWidth;
    private Bitmap image;


    public DecoyFloor(Bitmap image, int gameWidth) {
        this.gameWidth = gameWidth;
        this.image = image;

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);

    }
}
