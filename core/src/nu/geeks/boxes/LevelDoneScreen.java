package nu.geeks.boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by hannespa on 15-12-23.
 */
public class LevelDoneScreen {

    BitmapFont fnt;
    BitmapFont fntsmall;
    int level;
    TheBoxes game;
    SpriteBatch batch;
    int record;
    public LevelDoneScreen(TheBoxes game){
        this.game = game;
        batch = new SpriteBatch();
        fntsmall = new BitmapFont(Gdx.files.internal("fntsmall.fnt"), Gdx.files.internal("fntsmall.png"), false);
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
    }

    public void mDraw(){
        record = game.levelBest.getInteger(""+(level-1), -1);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        batch.draw(UTILS.bg3, 0, 0);
        fnt.draw(batch, "Level " + (level-1) + " done!\nPlay level " + level + "!", Gdx.graphics.getWidth()/2-100, Gdx.graphics.getHeight()-70);
        if(record == -1){
            fntsmall.draw(batch, "No record for this level yet! You should play more!\nYou did this level in " + game.levelBests[level-2] + " moves.", Gdx.graphics.getWidth()/2-200, Gdx.graphics.getHeight()/2+90);
        }else{
            if(record > game.levelBests[level-2]){
                fntsmall.draw(batch,"Awesome! You beat your record.\nYou did it in " + game.levelBests[level-2] + " moves!\nRecord on this level for your top game was " + record + " moves!", Gdx.graphics.getWidth()/2-200, Gdx.graphics.getHeight()/2+90);
            }else{
                fntsmall.draw(batch,"You did not beat your record.\nYou did it in " + game.levelBests[level-2] + " moves!\nRecord on this level for your top game is " + record + " moves!", Gdx.graphics.getWidth()/2-200, Gdx.graphics.getHeight()/2+90);

            }
        }
        batch.end();

    }

    public void mKeyReleased(int key){
        game.state = "PLAY";
    }

    public void disopse(){
        fnt.dispose();
        fntsmall.dispose();
        batch.dispose();
    }
}


