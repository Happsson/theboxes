package nu.geeks.boxes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;


public class TheBoxes extends ApplicationAdapter implements InputProcessor{

	boolean absoulteFirst = true;
	int width = 960;
	int height = 54;
	int[] levelBests;
	Sound plop;
	boolean musicPlaying;

	Music music;

	String state;

	boolean firstGame;

	int winMoves = 0;

	int currentLevel = 0;

	GameScreen gameScreen;
	MenuScreen menuScreen;
	LevelDoneScreen levelDoneScreen;
	HowToScreen howToScreen;
	WinScreen winScreen;
	HighScoreScreen highScoreScreen;

	String[] currentHighScores = new String[10];

	SpriteBatch batch;
	Texture bg2, bg3;
	BitmapFont fnt;

	//save
	Preferences highScores;
	Preferences levelBest;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		loadBgs();
		fnt = new BitmapFont();
		drawLoading();
		state = "MENU";
		loadColors();

		plop = Gdx.audio.newSound(Gdx.files.internal("plop.mp3"));

		firstGame = true;
		initializeScreens();
		levelBests = new int[19];

		Gdx.input.setInputProcessor(this);
		highScores = Gdx.app.getPreferences("highScores");
		levelBest = Gdx.app.getPreferences("levelBest");
		readHighScores();
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.play();
		music.setLooping(true);
		musicPlaying = true;
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
		} else if (state.equals("LEVELDONE")) {
			levelDoneScreen.level = currentLevel;
			levelDoneScreen.mDraw();
		} else if (state.equals("WINSCREEN")) {
			winScreen.mDraw();
		} else if (state.equals("HOWTO")) {
			howToScreen.mDraw();
		} else if (state.equals("HIGHSCORES")) {
			highScoreScreen.mDraw();
		}

	}

	//Increment level. This is done by restarting the gamescreen, which increments the level and boardsize, but does not reset the counter for time and moves.
	public void nextLevel() {
		gameScreen.startGame();
	}

	public void restart() {
		firstGame = true;
		initializeScreens();
	}

	public void saveData(){

	}

	public void playSound(){
		if(musicPlaying){
			plop.play();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		//gameScreen.mKeyPressed(keycode);
		//Gdx.app.log("Recieved " + keycode, "keydown");

		if (keycode == Input.Keys.ESCAPE) {

			state = "MENU";
		} else if(keycode == Input.Keys.M) {
			if (musicPlaying) {
				music.pause();
				musicPlaying = false;
			} else {
				musicPlaying = true;
				music.play();
			}
		}else{
			if(state.equals("PLAY")) {
				gameScreen.mKeyPressed(keycode);
			}
		}



			return false;

	}

	@Override
	public boolean keyUp(int keycode) {

		if (state.equals("WINSCREEN")) {
			winScreen.mKeyReleased(keycode);
		}else if(state.equals("HOWTO")){
			howToScreen.mKeyReleased();
		} else if (state.equals("HIGHSCORES")) {
			highScoreScreen.mKeyReleased(keycode);
		}else if (state.equals("PLAY")) {
			gameScreen.mKeyReleased(keycode);
		} else if (state.equals("LEVELDONE")) {
			levelDoneScreen.mKeyReleased(keycode);
		}else if (state.equals("MENU")) {
			menuScreen.mKeyReleased(keycode);
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(state.equals("MENU")){
			menuScreen.mMouseDown(screenX, screenY);
		}
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
		if(state.equals("MENU")){
			menuScreen.mMouseMoved(screenX, screenY);

		}
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
		menuScreen.dispose();
		levelDoneScreen.disopse();
		bg2.dispose();
		bg3.dispose();
		batch.dispose();
		howToScreen.dispose();
		winScreen.dispose();
		highScoreScreen.dispose();
		UTILS.bg2.dispose();
		UTILS.bg3.dispose();
		UTILS.bgmenu.dispose();
		UTILS.conblack.dispose();
		UTILS.conwhite.dispose();
		UTILS.highblack.dispose();
		UTILS.highwhite.dispose();
		UTILS.howblack.dispose();
		UTILS.howwhite.dispose();
		UTILS.newblack.dispose();
		UTILS.newwhite.dispose();
		UTILS.title.dispose();
		music.dispose();
	}

	public void addHighScore() {

		int[] previous = new int[11]; //One longer than the actual top 10 list
		for(int i = 1; i <= 10; i++)
		{
			previous[i-1] = highScores.getInteger(""+(i-1), 999999999);
		}
		previous[10] = winMoves;	//Add the current score.
		Arrays.sort(previous);		//Sort the list. (if current score wasn't top ten, it wont get saved.
		highScores.clear();
		highScores.flush();
		for(int i = 0; i < 10; i++){
			highScores.putInteger(""+i, previous[i]);
			//Gdx.app.log(""+ previous[i], "hs:"+i);
		}
		highScores.flush();

		if(previous[0] == winMoves){
			levelBest.clear();
			for(int i = 0; i < 19; i++){
				levelBest.putInteger(""+ (i+1),levelBests[i]);
			}
			levelBest.flush();
		}

		readHighScores();
	}

	public void readHighScores(){
		for(int i = 0; i < 10; i++){
			int tmpHigh = highScores.getInteger(""+i, 999999999);
			if(tmpHigh == 999999999){
				currentHighScores[i] = "" + (i+1) + " ------";
			}else{
				currentHighScores[i] = "" + (i+1) + ". " + tmpHigh;

			}
		}
	}

	public void clearHighscores(){
		highScores.clear();
		highScores.flush();
		levelBest.clear();
		levelBest.flush();
	}
}
