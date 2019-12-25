package com.ss.core.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.gameLogic.interfaces.IFinishAnim;
import com.ss.gameLogic.objects.Object;

public class Anim extends Actor {

    private Animation<TextureRegion> animLeft, animRight;
    private float elapsedTime;
    private IFinishAnim iFinishAnim;
    private Color cL, cR;
    private float rotation = 0;
    private Object obj;

    public Anim(String anim1, String anim2, Object obj) {
        this.animLeft = new Animation<>(.15f, AnimationEffect.animRight.get(anim1));
        this.animRight = new Animation<>(.15f, AnimationEffect.animLeft.get(anim2));
        this.obj = obj;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        TextureRegion textReLeft = animLeft.getKeyFrame(elapsedTime, false);
        TextureRegion textReRight = animRight.getKeyFrame(elapsedTime, false);

        float originXLeft = textReLeft.getRegionWidth()/2;
        float originYLeft = textReLeft.getRegionHeight()/2;
        float widthLeft = textReLeft.getRegionWidth();
        float heightLeft = textReLeft.getRegionHeight();

        float originXRight = textReRight.getRegionWidth()/2;
        float originYRight = textReRight.getRegionHeight()/2;
        float widthRight = textReRight.getRegionWidth();
        float heightRight = textReRight.getRegionHeight();

        batch.setColor(Color.WHITE);
        if (cL != null)
            batch.setColor(cL);
        batch.draw(textReLeft, getX()-originXLeft, getY()- originYLeft, originXLeft, originYLeft, widthLeft, heightLeft, 1.2f, 1.2f, rotation);

        batch.setColor(Color.WHITE);
        if (cR != null)
            batch.setColor(cR);
        batch.draw(textReRight, getX()-originXRight, getY()-originYRight, originXRight, originYRight, widthRight, heightRight, 1.2f, 1.2f, rotation);

        if (animLeft.isAnimationFinished(elapsedTime)
                && animRight.isAnimationFinished(elapsedTime)) {
            remove();
            iFinishAnim.finishedAnim(obj);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
    }

    public void start(Group gUI, float x, float y, float rotation) {
        this.rotation = rotation;
        setX(x);
        setY(y);
        gUI.addActor(this);
        elapsedTime = 0;
    }

    public void setColor(Color cL, Color cR) {
        this.cL = cL;
        this.cR = cR;
    }

    public void setiFinishAnim(IFinishAnim iFinishAnim) {
        this.iFinishAnim = iFinishAnim;
    }
}
