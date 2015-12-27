package nu.geeks.boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        batch = new SpriteBatch();
    }

    public void dispose() {
        batch.dispose();
        fnt.dispose();
    }

    public void mKeyReleased(int key) {
        game.state = "MENU";
        game.restart();
    }

    public void mDraw() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        batch.draw(UTILS.bg3, 0, 0);
        fnt.draw(batch, "You did it! It took you " + game.winMoves + " moves!", Gdx.graphics.getWidth()/2-250, Gdx.graphics.getHeight()/2+50);
        batch.end();


    }
}
