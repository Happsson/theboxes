package nu.geeks.boxes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hannespa on 15-12-23.
 */
public class HowToScreen {

    BitmapFont fnt;
    SpriteBatch batch;

    TheBoxes game;
    public HowToScreen(TheBoxes game){
        this.game = game;
        fnt = new BitmapFont();
        batch = new SpriteBatch();

    }

    public void dispose() {
        fnt.dispose();
        batch.dispose();
    }
}
