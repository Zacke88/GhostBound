package zacke.ghostbound;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Zacke on 2016-09-15.
 */
public class GameThread extends Thread {

    private int FPS = 30;
    private double avgFPS;
    private SurfaceHolder surface;
    private GamePanel game;
    private boolean running;
    public static Canvas canvas;

    public GameThread(SurfaceHolder surface, GamePanel game) {
        super();
        this.game = game;
        this.surface = surface;
    }
    @Override
    public void run() {

    }

}
