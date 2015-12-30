package nu.geeks.boxes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by hannespa on 15-12-23.
 */

public class Box {

    Random rand;
    public Model model;
    public ModelInstance instance;
    public int rotation = 0;
    ModelBuilder mBuilder;

    float x, y, z;
    Color c;
    int rotAmount, rotX,rotY,rotZ;
    boolean animationDone = true;

    public boolean selected = false;

    public Box(float x, float y, float z, Color c){
        this.c = c;
        this.x = x;
        this.y = y;
        this.z = z;
        rotAmount = 1;
        rotZ = 1;
        rotX = 0;
        rotY = 0;

        rand = new Random();

        mBuilder = new ModelBuilder();
        model = mBuilder.createBox(0.8f,0.8f,0.8f,
                new Material(ColorAttribute.createDiffuse(c)),
                VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);

        instance = new ModelInstance(model);
        instance.transform.setToTranslation(new Vector3(x,y,z));


    }

    public void anim(){
        if(selected){
            animationDone = true;
            setColor();
        }
        if(!animationDone){
        instance.transform.rotate(new Vector3(1,1,0), 45);
        rotation++;
          //  Gdx.app.log(""+rotation, "rot");
        }
        if(rotation == 4){
            animationDone = true;
            rotation = 0;
            setColor();
        }


    }

    public void setColor() {

        instance.materials.get(0).set(ColorAttribute.createDiffuse(c));
    }

    public void select(){
        instance.transform.setToTranslation(x,y,z+1);
        selected = true;
    }

    public void unSelect(){
        instance.transform.setToTranslation(x,y,z);
        selected = false;
    }

    public void mDraw(){

        if(selected) {
            instance.transform.rotate(new Vector3(rotX,rotY,rotZ), rotAmount);
            instance.calculateTransforms();
        }

    }

    public void setPosition(float x, float y, float z){
        instance.transform.setToTranslation(x,y,z);
        instance.calculateTransforms();
    }

    public void setColor(Color c){

        this.c = c;
    }


}
