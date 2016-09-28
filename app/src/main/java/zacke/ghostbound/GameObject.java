package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Abstract class for any game object. It is used to store data such as x
 * and y position for the object, also holds the bitmap image and the size of
 * the image. Has method to return the image as a rectangle.
 *
 * @author Zacke
 * @version 2016-09-28
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * @return The object as a rectangle with adjusted collision size to make
     * collision better reflect bitmaps.
     */
    public Rect getRectangle() {
        int adjustCollision = (int) (imageSize / 20);
        return new Rect((x + adjustCollision), (y + adjustCollision), ((x +
                imageSize) - adjustCollision), ((y +
                imageSize) - adjustCollision));
    }
}
