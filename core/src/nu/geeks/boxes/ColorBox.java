package nu.geeks.boxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

/**
 * Created by hannespa on 15-12-23.
 */
public class ColorBox extends Box {

    AnimationController controller;

    boolean anim = false;
    private int animCount = 0;

    public ColorBox(float x, float y, float z, Color c){
        super(x,y,z,c);

    }

    public void animStep(){
        instance.transform.setToTranslation(x, y - (0.12f), z -(0.2f));
       // instance.calculateTransforms();
    }

    @Override
    public void mDraw(){
        super.mDraw();
    }
}
