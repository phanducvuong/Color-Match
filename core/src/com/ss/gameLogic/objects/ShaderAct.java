package com.ss.gameLogic.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GUI;

public class ShaderAct {

  private Image shader;

  public ShaderAct(Group gUI, String name, float x, float y) {

    shader = GUI.createImage(GMain.textureAtlas, name);
    assert shader != null;
    shader.setOrigin(Align.center);
    shader.setScale(.5f);
    shader.setPosition(x + 5, y + 5);
    gUI.addActor(shader);
  }

  public void actionShader(float degrees, float duration) {
    shader.addAction(Actions.rotateBy(degrees, duration, Interpolation.linear));
  }

  public void reInitShader(float x, float y, TextureRegion textureRegion) {
    shader.setRotation(0);
    shader.setDrawable(new TextureRegionDrawable(textureRegion));

    shader.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    shader.setOrigin(Align.center);

    shader.setPosition(x+5, y+5);
    shader.setScale(.5f);
  }

  public Image getShader() { return shader; }
}
