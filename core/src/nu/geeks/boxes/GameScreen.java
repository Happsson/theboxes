package nu.geeks.boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import java.util.ArrayList;
import java.util.Random;
/**
 * Created by hannespa on 15-12-23.
 */
public class GameScreen {
    boolean fastAnim = false;
    int levelMoves = 0;
    int moves = 0;
    int row = 1;
    int col = 1;
    int level = 0;
    int[] currentSelected = {0,0};
    boolean won = false;
    boolean animateCurrentColor = false;
    int colorBoxAnim = 0;
    Color currentColor;
    Color[] colors = {UTILS.blue,UTILS.green,UTILS.pink,UTILS.red};
    Random rand;
    BitmapFont fnt;
    BitmapFont fntSmall;

    boolean animateMoves = false;
    ArrayList<Box> animateQueue;
    int animateIndex;

    boolean firstGame = true;
    SpriteBatch batch;
    Environment environment;
    PerspectiveCamera cam;
    ModelBatch mBatch;
    Box boxes[][];
    ColorBox colorBox[];
    TheBoxes game;

    public GameScreen(TheBoxes game){
        this.game = game;
        rand = new Random();
        fnt = new BitmapFont(Gdx.files.internal("fnt2.fnt"), Gdx.files.internal("fnt2.png"), false);
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(3f, 1f, 17f);
        cam.lookAt(-19f, -19f, -19f);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(0.5f, 0.5f, 0.5f, -1f, -0.2f, -0.1f));
        mBatch = new ModelBatch();
        batch = new SpriteBatch();
        fntSmall = new BitmapFont(Gdx.files.internal("fntsmall.fnt"), Gdx.files.internal("fntsmall.png"), false);
        animateQueue = new ArrayList<Box>();
        animateIndex = 0;
        startGame();
    }

    public void startGame(){
        disposeOld();
        row++;
        col++;
        level++;
        currentSelected[0] = 0;
        currentSelected[1] = 0;
        createBoxes();
        createColorBoxes();
        game.currentLevel = level;
    }

    /**
     * Dispose old models before building new level.
     *
     */
    private void disposeOld(){
        if(!firstGame){
            for(int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    boxes[i][j].model.dispose();
                }
            }
            for(int i = 0; i < 10; i++){
                colorBox[i].model.dispose();
            }
        }
    }

    private void createColorBoxes(){
        colorBox = new ColorBox[10];
        for (int i = 0; i < 10; i++) {
            colorBox[i] = new ColorBox(4+(i*1.2f),-3,-(i*2), colors[rand.nextInt(4)]);
            colorBox[i].rotX = 1;
            colorBox[i].rotY = 1;
            colorBox[i].rotZ = 1;
            colorBox[i].rotAmount = 2;

        }
        colorBox[0].selected = true;
        colorBox[0].anim = true;
        currentColor = colorBox[0].c;
    }

    public void createBoxes() {
        boxes = new Box[row][col];

        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                Color col = colors[rand.nextInt(4)];
                boxes[x][y] = new Box(-x, -y,0, col);
            }
        }
        boxes[0][0].selected = true;
    }

    public void mDraw(){
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.begin();
        batch.draw(UTILS.bg2, 0, 0);
        fnt.setColor(UTILS.white);
        fnt.draw(batch, "Current level: " + level, 100, 50);
        fnt.draw(batch, "Moves used: " + moves, 600, 50);
        fntSmall.draw(batch, "Current color", 690, 300);
        if(game.absoulteFirst){
            fntSmall.draw(batch, "WASD to move, ENTER to change color.", 300, Gdx.graphics.getHeight()/2);
        }
        batch.end();

        if (animateCurrentColor) {
            animateColor();
        }

        if(animateMoves){
            if(animateIndex < animateQueue.size()){
                if(!animateQueue.get(animateIndex).animationDone){
                    animateQueue.get(animateIndex).anim();
                }else{
                    animateIndex++;
                    game.playSound();
                }

            }else{
                animateMoves = false;
                animateIndex = 0;
                animateQueue.clear();
                setAllColors();
                checkWin();
            }
        }else if(fastAnim){

            for(Box b : animateQueue){
                b.anim();
                game.playSound();
            }
            if(animateQueue.get(animateQueue.size()-1).animationDone){
                fastAnim = false;
                animateIndex = 0;
                animateQueue.clear();
                setAllColors();
                checkWin();

            }

        }

        mBatch.begin(cam);
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                boxes[i][j].mDraw();
                mBatch.render(boxes[i][j].instance, environment);
            }
        }
        for(int i = 0; i < 10; i++){
            colorBox[i].mDraw();
            mBatch.render(colorBox[i].instance, environment);
        }

        mBatch.end();




    }


    void setAllColors(){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){

                boxes[i][j].animationDone = true;
                boxes[i][j].rotation = 0;
                boxes[i][j].setColor();

            }
        }
    }

    /**
     Animates the array of color boxes one step.
     */
    void animateColor() {

        animateCurrentColor = false;
        colorBox[0].model.dispose();
        for (int i = 0; i < 9; i++) {
            colorBox[i] = colorBox[i+1];
            colorBox[i].setPosition(4+(i*1.2f),-3,-(i*2));
        }
        colorBox[9] = new ColorBox(12f,-3,20, colors[rand.nextInt(4)]);
        currentColor = colorBox[0].c;
        colorBox[0].selected = true;
        colorBoxAnim = 0;


    }


    public void dispose(){
        disposeOld();
        batch.dispose();
        mBatch.dispose();
        fnt.dispose();
    }

    public void mKeyReleased(int keycode){
        switch (keycode) {
            case Input.Keys.ENTER:
                if(animateQueue.size() < 1) {
                    if (!boxes[currentSelected[0]][currentSelected[1]].c.equals(currentColor)) {
                        changeColor(currentSelected[0], currentSelected[1], boxes[currentSelected[0]][currentSelected[1]].c);
                        animateMoves = true;
                        moves++;
                        levelMoves++;
                        nextColor();
                    }
                }else{
                    animateMoves = false;
                    fastAnim = true;
                }
                break;
        }
    }


    public void mKeyPressed(int keycode) {

        firstGame = false; //Let game start as soon as user does anything.
        game.firstGame = false;
        switch(keycode) {
            case Input.Keys.A:
                boxes[currentSelected[0]][currentSelected[1]].unSelect();
                if (currentSelected[0] < row-1)
                    currentSelected[0]++;
                boxes[currentSelected[0]][currentSelected[1]].select();

                break;
            case Input.Keys.D:
                boxes[currentSelected[0]][currentSelected[1]].unSelect();
                if (currentSelected[0] > 0)
                    currentSelected[0]--;
                boxes[currentSelected[0]][currentSelected[1]].select();
                break;
            case Input.Keys.S:
                boxes[currentSelected[0]][currentSelected[1]].unSelect();
                if (currentSelected[1] < col-1)
                    currentSelected[1]++;
                boxes[currentSelected[0]][currentSelected[1]].select();
                break;
            case Input.Keys.W:
                boxes[currentSelected[0]][currentSelected[1]].unSelect();
                if (currentSelected[1] > 0)
                    currentSelected[1]--;
                boxes[currentSelected[0]][currentSelected[1]].select();
                break;
            case Input.Keys.R:

                row = 1;
                col = 1;
                moves = 0;
                level = 0;
                game.firstGame = true;
                startGame();
                break;
        }

    }

    private void checkWin() {
        Color c = boxes[0][0].c;

        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                if (!boxes[x][y].c.equals(c)) return;
            }
        }
        if (level < 19) {
            game.nextLevel();
            game.levelBests[level-2] = levelMoves;
            levelMoves = 0;
            game.state = "LEVELDONE";
            game.absoulteFirst = false;

        } else {
            game.winMoves = moves;
            game.addHighScore();
            game.state = "WINSCREEN";
        }
    }

    /**
     Changes the current color to the next color.
     */
    void nextColor() {
        //colorBox[i] = new ColorBox((-300)-(40*i), 100-(40*i), colors[rand.nextInt(4)]);
        animateCurrentColor = true;
    }

    /**
     Change the color of the selected box. Then recurivly calls the same method for all neighbours, untill all the connected boxes with the same color
     has changed color.
     */
    void changeColor(int x, int y, Color colr) {
        //System.out.println();
        animateQueue.add(boxes[x][y]);
        boxes[x][y].animationDone = false;
        boxes[x][y].setColor(currentColor);
        if (x > 0) {
            if (boxes[x-1][y].c.equals(colr)) {
                changeColor(x-1, y, colr);
            }
        }
        if (x < row-1) {
            if (boxes[x+1][y].c.equals(colr)) {
                changeColor(x+1, y, colr);
            }
        }
        if (y > 0) {
            if (boxes[x][y-1].c.equals(colr)) {
                changeColor(x, y-1, colr);
            }
        }
        if (y < col-1) {
            if (boxes[x][y+1].c.equals(colr)) {
                changeColor(x, y+1, colr);
            }
        }
    }
}