package nu.geeks.boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    BitmapFont fnt;

    boolean newGame = false, howto = false, highscore = false;
    Color selCol;
    ColorBox[] rain = new ColorBox[10];
    Random r = new Random();


    public MenuScreen(TheBoxes game){
        this.game = game;
        selCol = UTILS.white;
        batch = new SpriteBatch();
        newGame = true;
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
    }

    public void mDraw(){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        batch.draw(UTILS.bgmenu, 0, 0);
        batch.draw(UTILS.title, Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 120);
        if(game.firstGame) {
            if (newGame) {
                batch.draw(UTILS.newblack, Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 220);
            } else {
                batch.draw(UTILS.newwhite, Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 220);
            }
        }else{
            if (newGame) {
                batch.draw(UTILS.conblack, Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 220);
            } else {
                batch.draw(UTILS.conwhite, Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 220);
            }
            }

        if(howto) {
            batch.draw(UTILS.howblack, Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 320);
        }else{
            batch.draw(UTILS.howwhite, Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 320);
        }
        if(highscore){
            batch.draw(UTILS.highblack, Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 420);
        }
        else{
            batch.draw(UTILS.highwhite, Gdx.graphics.getWidth() / 2 - 110, Gdx.graphics.getHeight() - 420);
        }
        fnt.getData().setScale(0.5f);
        fnt.draw(batch, "By hannes.paulsson@gmail.com", Gdx.graphics.getWidth()- 310, 25);
        batch.end();
    }

    public void mKeyReleased(int key){

        switch(key){
            case Input.Keys.UP:
                if(howto){
                    howto = false;
                    newGame = true;
                }else if(highscore){
                    highscore = false;
                    howto = true;
                }
                break;
            case Input.Keys.DOWN:
                if(newGame){
                    newGame = false;
                    howto = true;
                }else if(howto){
                    howto = false;
                    highscore = true;
                }
                break;
            case Input.Keys.SPACE:
                if(newGame) game.state = "PLAY";
                if(howto) game.state = "HOWTO";
                if(highscore) game.state = "HIGHSCORES";
                break;
        }

    }

    public void dispose() {

        batch.dispose();
        fnt.dispose();
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
