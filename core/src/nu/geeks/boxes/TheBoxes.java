package nu.geeks.boxes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Arrays;

public class TheBoxes extends ApplicationAdapter implements InputProcessor{


	int width = 960;
	int height = 540;

	//PFont fnt3;
	String state;

	boolean firstGame;

	String winTime = "";
	int winMoves = 0;

	int currentLevel = 0;

	String[] highScores;

	GameScreen gameScreen;
	MenuScreen menuScreen;
	LevelDoneScreen levelDoneScreen;
	HowToScreen howToScreen;
	WinScreen winScreen;
	HighScoreScreen highScoreScreen;


	SpriteBatch batch;
	Texture bg2, bg3;
	BitmapFont fnt;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		loadBgs();
		fnt = new BitmapFont();
		drawLoading();
		state = "MENU";
		loadColors();

		firstGame = true;
		initializeScreens();

		Gdx.input.setInputProcessor(this);



	}

	private void initializeScreens(){
		//Initialize screens.
		gameScreen = new GameScreen(this);
		menuScreen = new MenuScreen(this);
		levelDoneScreen = new LevelDoneScreen(this);
		howToScreen = new HowToScreen(this);
		winScreen = new WinScreen(this);
		highScoreScreen = new HighScoreScreen(this);
	}

	private void loadColors(){
		UTILS.white = new Color(255/255f, 255/255f, 255/255f,1);
		UTILS.black = new Color(0, 0, 0, 1);
		UTILS.blue = new Color(106/255f, 100/255f, 218/255f,1);
		UTILS.green = new Color(88/255f, 213/255f, 138/255f,1);
		UTILS.pink = new Color(224/255f, 91/255f, 206/255f,1);
		UTILS.red = new Color(255/255f, 118/255f, 107/255f,1);

	}

	private void loadBgs(){
		bg2 = new Texture("bg2.png");
		bg3 = new Texture("bg3.png");

	}

	private void drawLoading(){
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(bg2, 0, 0);
		fnt.draw(batch, "Loading...", width/2, height/2);
		batch.end();
	}

	@Override
	public void render () {

		if (state.equals("MENU")) {
			menuScreen.mDraw();
		} else if (state.equals("PLAY")) {
			gameScreen.mDraw();
		} /*else if (state.equals("LEVELDONE")) {
			levelDoneScreen.level = currentLevel;
			levelDoneScreen.mDraw();
		} else if (state.equals("HOWTO")) {
			howToScreen.mDraw();
		} else if (state.equals("WINSCREEN")) {
			winScreen.mDraw();
		} else if (state.equals("HIGHSCORES")) {
			highScoreScreen.mDraw();
		}

	*/

	}

	//Increment level. This is done by restarting the gamescreen, which increments the level and boardsize, but does not reset the counter for time and moves.
	public void nextLevel() {
		gameScreen.startGame();
	}

	public void restart() {
		initializeScreens();
	}

	@Override
	public boolean keyDown(int keycode) {
		//gameScreen.mKeyPressed(keycode);
		//Gdx.app.log("Recieved " + keycode, "keydown");

		if (keycode == Input.Keys.ESCAPE) {
			state = "MENU";
		} else {
			if (state.equals("MENU")) {
				menuScreen.mKeyPressed(keycode);
			} else if (state.equals("PLAY")) {
				gameScreen.mKeyPressed(keycode);
			} /*else if (state.equals("WINSCREEN")) {
				winScreen.mKeyPressed(keycode);
			} else if (state.equals("HIGHSCORES")) {
				highScoreScreen.mKeyPressed(keycode);
			}
		}

*/
		}
			return false;

	}

	@Override
	public boolean keyUp(int keycode) {

		/*
		if (state.equals("PLAY")) {
			gameScreen.mKeyReleased(keycode);
		} else if (state.equals("LEVELDONE")) {
			levelDoneScreen.mKeyReleased(keycode);
		}
		*/
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		/*
		if (state.equals("MENU")) {
			menuScreen.mMouseClicked(screenX, screenY);
		}

		*/
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void dispose() {
		super.dispose();
		gameScreen.dispose();

	}
}
