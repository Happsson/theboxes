package nu.geeks.boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hannespa on 15-12-23.
 */
public class HowToScreen {

    BitmapFont fnt;
    BitmapFont fntBig;
    SpriteBatch batch;

    TheBoxes game;
    public HowToScreen(TheBoxes game){
        this.game = game;
        fntBig = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        fnt = new BitmapFont(Gdx.files.internal("fntsmall.fnt"), Gdx.files.internal("fntsmall.png"), false);
        batch = new SpriteBatch();
    }

    public void mDraw(){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        batch.draw(UTILS.bg3, 0, 0);
        fnt.getData().setScale(1);
        fntBig.draw(batch, "Welcome to boxes!", Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 50);
        fnt.draw(batch, "The goal of this game is to get all the boxes the same color.\nSelect a box using the arrow keys.\nChange color of the selected box, and all connected boxes \nof the same color, to the current color displayed\n in the top right corner by hitting SPACE.\nPress R to restart game.\n\nTry to get through all 19 levels in as few moves as possible.\nHit ESC at any time to go back to main menu.", Gdx.graphics.getWidth() / 2 - 300, Gdx.graphics.getHeight() - 120);
        fnt.getData().setScale(0.5f);
        fnt.draw(batch, "Music:\n\"Monkeys Spinning Monkeys\" Kevin MacLeod (incompetech.com)\n" +
                "Licensed under Creative Commons: By Attribution 3.0\n" +
                "http://creativecommons.org/licenses/by/3.0/", Gdx.graphics.getWidth()/2, 100);
        batch.end();
    }

    public void mKeyReleased(){
        game.state = "MENU";
    }


    public void dispose() {
        fnt.dispose();
        batch.dispose();
        fntBig.dispose();
    }
}
