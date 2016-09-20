package zacke.ghostbound;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getRectangle() {
        return new Rect(x, y, x+imageSize, y+imageSize);
    }

    public int getImageSize() {
        return imageSize;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
    }
}
