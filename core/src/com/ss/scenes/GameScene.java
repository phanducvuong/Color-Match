package com.ss.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.action.exAction.GTween;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Level;
import com.ss.gameLogic.interfaces.ICollision;
import com.ss.gameLogic.logic.Logic;
import com.ss.gameLogic.objects.Object;
import com.ss.gameLogic.objects.PolygonAct;
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

  private Image circleClick;

  private PolygonAct polygonAct;
  private int turn = 0;
  private int numberObjects = 5;
  private boolean endGame = false;

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
    gShapeRender.setSize(720, 1280);
    gShapeRender.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2, Align.center);

    listObjGamePlay = new ArrayList<>();

    initAsset();
    createShapeMain("mona_lisa");
    initLogic();
    eventClick();

    createListObject();
    createCircleClick();

    polygonAct = new PolygonAct(gUI);
    polygonAct.setDeltaScl(numberObjects);

    square.getShape().setZIndex(1000);
    nextObj();
  }

  //////////////////////////////////////////INIT////////////////////////////////////////////////////

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

    shape.createShape("mona_lisa2", "square");
    shape.createPointStart(gUI);
  }

  private void eventClick() {
    stageClick();
  }

  private void createCircleClick() {
    circleClick = GUI.createImage(textureAtlas, "circle_click");
    assert circleClick != null;
    circleClick.setOrigin(Align.center);
    circleClick.setScale(0);
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

  private void createShapeMain(String name) {
    square = new Object(name, 1);
    square.addScene(gUI, gShapeRender);
    square.getShape().setOrigin(Align.center);
    square.getShape().setPosition(gUI.getWidth()/2, gUI.getHeight()/2, Align.center);
    square.getShape().setScale(.5f);
    square.setVerShapeMain(0, .5f);
  }

  private void createListObject() {
    listObjGamePlay.add(shape.shape0);
    listObjGamePlay.add(shape.shape1);
    listObjGamePlay.add(shape.shape2);
  }

  //////////////////////////////////////////INIT////////////////////////////////////////////////////

  //////////////////////////////////////////GAME PLAY///////////////////////////////////////////////

  private void startGame(Object obj) {
    gUI.addActor(obj);
    obj.addAction(GSimpleAction.simpleAction(this::moveObject));
  }

  private boolean moveObject(float dt, Actor a) {
    Object obj = (Object) a;
    Vector2 v = logic.posShowObj();

    obj.addScene(gUI, gShapeRender);
    obj.setCollision(this);
    obj.setPos((int)v.x);

    gUI.addActor(obj.gBorderSquare);
    GTween.action(obj.gBorderSquare, scaleTo(.4f, .4f, 1f, linear),
            () -> {
              gUI.addActor(obj.gImgBack);
              obj.getShape().setZIndex(1000);
              obj.move(square, Level.LV1, logic, (int)v.x, (int)v.y);
              obj.gBorderSquare.remove();
            }
    );

    SequenceAction seq = sequence();
    seq.addAction(delay(Level.LV1.z));
    seq.addAction(Actions.run(this::nextObj));

    gUI.addAction(seq);

    return true;
  }

  private void nextObj() {
    turn++;
    if (turn <= numberObjects && !endGame)
      startGame(getObject());
  }

  @Override
  public void collided(Object obj) {
    float[] v = obj.getVertices();
    int id = obj.getId();
    int bound = obj.getBound();
    int p1 = obj.p1;
    int p2 = obj.p2;

    Vector2 pObj1 = new Vector2();
    Vector2 pObj2 = new Vector2();

    //get vertices
    if (id == 2 || id == 5) {
      pObj1.x = v[0]; pObj1.y = v[1];
      pObj2.x = v[4]; pObj2.y = v[5];
    }
    else {
      pObj1.x = v[2]; pObj1.y = v[3];
      pObj2.x = v[6]; pObj2.y = v[7];
    }

    //caculate point in which main
    float pOfM1 = logic.pointOfDomain(square.getShape(), pObj1);
    float pOfM2 = logic.pointOfDomain(square.getShape(), pObj2);

    //condition to continue game or end game
    if (bound == 0) {
      if (p1 < 0 && pOfM1 < 0 && p2 > 0 && pOfM2 > 0) {
        Gdx.app.log("BOUND", bound + "  id: " + id);
        eftCollition(obj);
      }
      else if (p1 > 0 && pOfM1 > 0 && p2 < 0 && pOfM2 < 0) {
        Gdx.app.log("BOUND", bound + "  id: " + id);
        eftCollition(obj);
      }
      else {
        Gdx.app.log("END", "End Game!");
        eftEndGame();
      }
    }
    else {
      if (bound > 0 && pOfM1 > 0 && pOfM2 > 0) {
        Gdx.app.log("BOUND", bound + "");
        eftCollition(obj);
      }
      else if (bound < 0 && pOfM1 < 0 && pOfM2 < 0) {
        Gdx.app.log("BOUND", bound + "");
        eftCollition(obj);
      }
      else {
        Gdx.app.log("END", "End Game!");
        eftEndGame();
      }
    }

    obj.remove();
  }

  private void eftCollition(Object obj) {
    if (obj.turn == numberObjects)
      polygonAct.updatePolyAct(true);
    else
      polygonAct.updatePolyAct(false);
  }

  private void eftEndGame() {
    endGame = true;
    for (int i = 0; i < listObjGamePlay.size(); i++)
      for (Object obj : listObjGamePlay.get(i))
        if (obj.isAlive) {
          obj.isAlive = false;
          obj.clear();
        }
    turn = 0;
  }

  private Object getObject() {
    int i = (int) Math.floor(Math.random() * 3);

    for (Object o : listObjGamePlay.get(i))
      if (!o.isAlive) {
        o.turn = turn;
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
