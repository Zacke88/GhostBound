package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Abstract class for any game object. It is used to store data succh as x
 * and y position for the object, also holds the bitmap image and the size of
 * the image.
 *
 * Created by Zacke on 2016-09-15.
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected Bitmap image;
    protected int imageSize;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getRectangle() {
        return new Rect(x, y, x+imageSize, y+imageSize);
    }
}
