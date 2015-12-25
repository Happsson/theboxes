package nu.geeks.boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hannespa on 15-12-23.
 */
public class LevelDoneScreen {

    BitmapFont fnt;
    int level;
    TheBoxes game;
    SpriteBatch batch;
    public LevelDoneScreen(TheBoxes game){
        this.game = game;
        batch = new SpriteBatch();
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
    }

    public void mDraw(){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        batch.draw(UTILS.bg3, 0, 0);
        fnt.draw(batch, "Level " + (level-1) + " done!   Play level " + level + "!", Gdx.graphics.getWidth()/2-200, Gdx.graphics.getHeight()/2+50);
        batch.end();

    }

    public void mKeyReleased(int key){
        game.state = "PLAY";
    }

    public void disopse(){
        fnt.dispose();
        batch.dispose();
    }
}


