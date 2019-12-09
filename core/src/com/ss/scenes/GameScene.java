package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.math.Interpolation.*;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Level;
import com.ss.gameLogic.interfaces.ICollision;
import com.ss.gameLogic.logic.Logic;
import com.ss.gameLogic.objects.Object;
import com.ss.gameLogic.objects.Shape;

import java.util.ArrayList;
import java.util.List;

public class GameScene extends GScreen implements ICollision {

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

  private List<List<Object>> listObjGamePlay;
  private List<Object> listObjCollided;

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

    listObjGamePlay = new ArrayList<>();
    listObjCollided = new ArrayList<>();

    createShapeMain();
    initAsset();
    initLogic();
    eventClick();

//    createBox();

    createListObject();
    nextObj();
  }

  //caculate poin in center and radius
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

  public void createListObject() {
    listObjGamePlay.add(shape.shape0);
    listObjGamePlay.add(shape.shape1);
    listObjGamePlay.add(shape.shape2);
    Gdx.app.log("SIZE", listObjGamePlay.size() + "");
  }

  private void startGame(Object obj) {
    gUI.addActor(obj);
    obj.addAction(GSimpleAction.simpleAction(this::moveObject));
  }

  private boolean moveObject(float dt, Actor a) {
    Object obj = (Object) a;
    Vector2 v = logic.posShowObj();

    obj.addScence(gUI, gShapeRender);
    obj.setCollision(this);
    obj.setPos((int)v.x);
    obj.move(square, Level.LV3, logic, (int)v.x, (int)v.y);

    SequenceAction seq = sequence();
    seq.addAction(delay(Level.LV3.z));
    seq.addAction(Actions.run(this::nextObj));

    gUI.addAction(seq);
    return true;
  }

  private void nextObj() {
    Gdx.app.log("AA", "AA");
    startGame(getObject());
  }

  @Override
  public void collided(Object obj) {
    float[] v = obj.getVertices();
    Vector2 p2 = new Vector2(v[2], v[3]);
    Vector2 p4 = new Vector2(v[6], v[7]);

    obj.isAlive = false;

    float pOfM1 = logic.pointOfDomain(square.getShape(), p2);
    float pOfM2 = logic.pointOfDomain(square.getShape(), p4);

//    Gdx.app.log("COLLISION", pOfM1 + "  " + pOfM2);
    obj.remove();
  }

  private Object getObject() {
    int i = (int) Math.floor(Math.random() * 3);

    for (Object o : listObjGamePlay.get(i))
      if (!o.isAlive) {
        o.isAlive = true;
        o.clear();
        return o;
      }
    return null;
  }





































  @Override
  public void run() {

  }
}
