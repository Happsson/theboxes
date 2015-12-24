package nu.geeks.boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by hannespa on 15-12-23.
 */
public class MenuScreen {


    TheBoxes game;
    SpriteBatch batch;

    boolean newGame = false, howto = false, highscore = false;
    Color selCol;
    ColorBox[] rain = new ColorBox[10];
    Random r = new Random();
    BitmapFont fnt;


    public MenuScreen(TheBoxes game){
        this.game = game;
        selCol = UTILS.white;
        fnt = new BitmapFont();
        batch = new SpriteBatch();
    }

    public void mDraw(){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        batch.draw(UTILS.bg3, 0, 0);
        fnt.getData().setScale(4, 4);

        fnt.draw(batch, "The Boxes", Gdx.graphics.getWidth() / 2 - 120, Gdx.graphics.getHeight() - 100);
        fnt.getData().setScale(2, 2);
        fnt.draw(batch, "New Game", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 200);
        fnt.draw(batch, "How To Play", Gdx.graphics.getWidth()/2 - 60,Gdx.graphics.getHeight() - 300 );
        fnt.draw(batch, "High Scores", Gdx.graphics.getWidth()/2 - 65, Gdx.graphics.getHeight() - 400);
        batch.end();
    }

    public void mKeyPressed(int key){
        game.state = "PLAY";
    }

    public void dispose() {
        fnt.dispose();
        batch.dispose();
    }

    /*
    public MenuScreen(){
        selCol = white;
        for(int i = 0; i < 10; i++){
            rain[i] = new ColorBox(r.nextInt(width), r.nextInt(2000)-2020, white);
            rain[i].z = -300;
            rain[i].rotation = r.nextInt(90);
        }
    }

*/

}
