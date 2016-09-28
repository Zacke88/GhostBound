package zacke.ghostbound;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Class which represents the floor object. Creates a scaled bitmap of the
 * image to make it adjust to different screen sizes.
 *
 * @author Zacke
 * @version 2016-09-28
 */
public class Floor extends GameObject {

    public Floor(int canvasWidth, Context context) {
        imageSize = (canvasWidth / 10);
        Bitmap bitmapImage = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.square64);
        super.image = Bitmap.createScaledBitmap(bitmapImage, imageSize,
                imageSize, false);
    }
}
