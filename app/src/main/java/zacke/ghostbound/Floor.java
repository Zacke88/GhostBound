package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Zacke on 2016-09-17.
 */
public class Floor extends GameObject {

    private int gameWidth;

    public Floor(Bitmap image, int gameWidth, Canvas c) {
        this.gameWidth = gameWidth;
        imageSize = c.getWidth()/10;
        super.image = Bitmap.createScaledBitmap(
                image, imageSize, imageSize, false);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void drawDecoy(Canvas canvas) {
        Paint alphaPaint = new Paint();
        alphaPaint.setAlpha(35);
        canvas.drawBitmap(image, x, y, alphaPaint);
    }
}
