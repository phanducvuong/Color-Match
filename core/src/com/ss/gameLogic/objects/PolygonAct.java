package com.ss.gameLogic.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.action.exAction.GTween;
import com.ss.core.util.GUI;

public class PolygonAct {

  private float deltaScl = 0;
  private float scl = 0;
  private Group gUI;

  private Image polyAct, borderPolyAct;

  public PolygonAct(Group gUI) {
    this.gUI = gUI;

    polyAct = GUI.createImage(GMain.textureAtlas, "action_polygon");
    assert polyAct != null;
    polyAct.setOrigin(Align.center);
    polyAct.setPosition(gUI.getWidth()/2 - polyAct.getWidth()/2, gUI.getHeight()/2 - polyAct.getHeight()/2);
    gUI.addActor(polyAct);

    borderPolyAct = GUI.createImage(GMain.textureAtlas, "border_poly_act");
    assert borderPolyAct != null;
    borderPolyAct.setPosition(gUI.getWidth()/2 - borderPolyAct.getWidth()/2, gUI.getHeight()/2 - borderPolyAct.getHeight()/2);
    borderPolyAct.getColor().a = 0;
    gUI.addActor(borderPolyAct);
  }

  public void updatePolyAct(boolean isBorderPoly) {
    scl = polyAct.getScaleX() + deltaScl;
    polyAct.addAction(Actions.scaleTo(scl, scl, .25f, Interpolation.linear));

    if (isBorderPoly) {
      Gdx.app.log("BORDER", "SHOW BORDER");
      GTween.action(borderPolyAct, Actions.alpha(1, .5f, Interpolation.linear),
              () -> borderPolyAct.addAction(Actions.alpha(0, .5f, Interpolation.linear)));
    }
  }

  public void setDeltaScl(int turn) {
    deltaScl = (float) (3.5/turn);
  }
}
