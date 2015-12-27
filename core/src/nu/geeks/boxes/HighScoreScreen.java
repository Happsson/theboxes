package nu.geeks.boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hannespa on 15-12-23.
 */
public class HighScoreScreen {

    TheBoxes game;
    SpriteBatch batch;
    BitmapFont fnt;
    BitmapFont fntbig;

    public HighScoreScreen(TheBoxes game){
        this.game = game;
        batch = new SpriteBatch();
        fntbig = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        fnt = new BitmapFont(Gdx.files.internal("fntsmall.fnt"), Gdx.files.internal("fntsmall.png"), false);

    }

    public void dispose(){
        batch.dispose();
        fnt.dispose();
    }

    public void mDraw(){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        batch.draw(UTILS.bg3, 0, 0);
        fntbig.draw(batch, "TOP 10!", Gdx.graphics.getWidth()/2 - 40, Gdx.graphics.getHeight() - 40);
        for(int i = 0; i < 10; i++){
            fnt.draw(batch, game.currentHighScores[i], Gdx.graphics.getWidth()/2 - 100, Gdx.graphics.getHeight() - 100 - (i*35));
        }
        fnt.draw(batch, "Reset highscores with 'c'.", 100,100);

        batch.end();

    }

    public void mKeyReleased(int keycode) {
        switch(keycode){
            case Input.Keys.C:
                game.clearHighscores();
                game.readHighScores();
                break;
            default:
                game.state = "MENU";
                break;
        }

    }
}
