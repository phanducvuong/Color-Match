package com.ss.core.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.gameLogic.interfaces.INextObject;
import com.ss.gameLogic.objects.Object;

public class Anim extends Actor {

    private Animation<TextureRegion> anim;
    private float elapsedTime;
    private float offset = 19;
    private INextObject iNextObject;
    private Object obj;
    private Vector2 v;
    private float rotation = 0;

    public Anim(String ani) {
        this.anim = new Animation<>(.1f, AnimationEffect.anims.get(ani));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        TextureRegion textureRegion = anim.getKeyFrame(elapsedTime, false);
        float originX = textureRegion.getRegionWidth()/2;
        float originY = textureRegion.getRegionHeight()/2;
        float width = textureRegion.getRegionWidth();
        float height = textureRegion.getRegionHeight();

        batch.setColor(Color.RED);
        batch.draw(textureRegion, getX()-originX, getY()-originY, originX, originY, width, height, 1, 1, rotation);
        if (anim.isAnimationFinished(elapsedTime)) {
            remove();
            iNextObject.nextMove(obj, v);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
    }

    public void start(Group gUI, Object obj, Vector2 v) {
        this.obj = obj;
        this.v = v;
        gUI.addActor(this);
        elapsedTime = 0;
    }

    public void setXY(float x, float y) {
        setX(x+offset);
        setY(y+offset);
    }

    public void setiNextObject(INextObject iNextObject) {
        this.iNextObject = iNextObject;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
