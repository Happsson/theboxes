package nu.geeks.boxes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hannespa on 15-12-23.
 */
public class WinScreen {

    TheBoxes game;
    BitmapFont fnt;
    SpriteBatch batch;

    public WinScreen(TheBoxes game){
        this.game = game;
        fnt = new BitmapFont();
        batch = new SpriteBatch();
    }

    public void dispose() {
        batch.dispose();
        fnt.dispose();
    }

    public void mKeyPressed(int key) {
    }

    public void mDraw() {

    }
}
