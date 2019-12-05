package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.math.Interpolation.*;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.core.action.exAction.GTemporalAction;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.C;
import com.ss.gameLogic.config.Level;
import com.ss.gameLogic.logic.Logic;
import com.ss.gameLogic.objects.Object;
import com.ss.gameLogic.objects.Shape;

public class GameScene extends GScreen {

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private Group gMain = new Group();
  private Group gShapeRender = new Group();
  private Group gUI = new Group();
  private Object square;
  private IPlatform plf = GMain.platform;
  private Logic logic;
  private Stage s;
  private float gsWidth = GStage.getWorldWidth()/2;

  private Shape shape = Shape.getInstance();

  @Override
  public void dispose() {

  }

  @Override
  public void init() {
    s = GStage.getStage();
    GStage.addToLayer(GLayer.ui, gMain);
    gMain.addActor(gUI);
    gMain.addActor(gShapeRender);
    gUI.setSize(720, 1280);
    gUI.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2, Align.center);

    createShapeMain();
    initAsset();
    initLogic();
    eventClick();

    createBox();
  }

  private void initLogic() {
    logic = Logic.getInstance();
    logic.degree = 45;
    logic.calRC(square.getShape());
  }

  private void initAsset() {
    Image bg = GUI.createImage(textureAtlas, "bg");
    assert bg != null;
    bg.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    gMain.addActor(bg);

    gUI.setZIndex(1000);
    gShapeRender.setZIndex(1000);

    shape.createShape("box");
  }

  private void eventClick() {
    stageClick();
  }

  private void stageClick() {
    s.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (x < gsWidth) {
          square.getShape().addAction(rotateBy(-45, .15f, fastSlow));
          square.setVerShapeMain(-45, .5f);
        }
        else {
          square.getShape().addAction(rotateBy(45, .15f, fastSlow));
          square.setVerShapeMain(45, .5f);
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void createShapeMain() {
    square = new Object("square", 1);
    square.addScence(gUI, gShapeRender);
    square.getShape().setOrigin(Align.center);
    square.getShape().setPosition(gUI.getWidth()/2, gUI.getHeight()/2, Align.center);
    square.getShape().setScale(.5f);
    square.setVerShapeMain(0, .5f);
  }

  private void createBox() {
    int rand = (int)Math.round(Math.random() * 3);
    Object box;
    if (rand == 0) {
      box = shape.shape0.pop();
      box.addScence(gUI, gShapeRender);
      box.setPos(1);
      moveShape(box);
    }
    else if (rand == 1) {
      box = shape.shape1.pop();
      box.addScence(gUI, gShapeRender);
      box.setPos(1);
      moveShape(box);
    }
    else {
      box = shape.shape2.pop();
      box.addScence(gUI, gShapeRender);
      box.setPos(1);
      moveShape(box);
    }

//    Gdx.app.log("SIZE", "shape0: " + shape.shape0.size() + " shape1: " + shape.shape1.size() + " shape2: " + shape.shape2.size());
//    Gdx.app.log("TIME", Gdx.graphics.getDeltaTime() + "");
  }

  private void moveShape(Object box) {
    assert box != null;
    float x = box.getShape().getX();
    float y = box.getShape().getY();
    float duration = Level.LV8.x;
    box.getShape().addAction(GTemporalAction.add(duration, (p, a) -> {
      Vector2 temp = new Vector2(x, y);

      if (Intersector.overlapConvexPolygons(box.getPolygon(), square.getPolygon())) {
        Gdx.app.log("COLLI", C.lang.title);
        box.getShape().clear();
      }
      else {
        temp = logic.calPosObj(temp.x, temp.y, p, 1);
        box.setVertices(logic.calVertices(box.getShape(), temp.x, temp.y, 1));
        box.getShape().setPosition(temp.x, temp.y);
      }
    }));
  }

  @Override
  public void run() {

  }
}
