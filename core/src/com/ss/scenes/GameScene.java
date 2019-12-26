package com.ss.scenes;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import static com.badlogic.gdx.math.Interpolation.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.platform.IPlatform;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.action.exAction.GTween;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.config.Level;
import com.ss.gameLogic.interfaces.ICollision;
import com.ss.gameLogic.interfaces.IFinishAnim;
import com.ss.gameLogic.interfaces.INextLevel;
import com.ss.gameLogic.logic.Logic;
import com.ss.gameLogic.objects.Object;
import com.ss.gameLogic.objects.PolygonAct;
import com.ss.gameLogic.objects.ShaderAct;
import com.ss.gameLogic.objects.Shape;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends GScreen implements ICollision, INextLevel, IFinishAnim {

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  private Group gMain = new Group();
  private Group gShapeRender = new Group();
  private Group gLogic = new Group();
  private Group gUI = new Group();
  private Object shapeMainCenter;
  private IPlatform plf = GMain.platform;
  private Logic logic;
  private Stage s;
  private boolean isTouchStage = true;
  private float gsWidth = GStage.getWorldWidth()/2;

  private Shape shape = Shape.getInstance();
  private List<List<Object>> listObjGamePlay;

  private Image circleClick;
  private ShaderAct shaderAct;

  public PolygonAct polygonAct;
  public int turn = 0;
  private int numberObjects = 0;
  private Vector3 level;
  public boolean endGame = false;
  public long lv = 1;

  public StartScene startScene;

  @Override
  public void dispose() {

  }

  @Override
  public void init() {
    s = GStage.getStage();
    GStage.addToLayer(GLayer.ui, gMain);
    gMain.addActor(gLogic);
    gMain.addActor(gShapeRender);
    gMain.addActor(gUI);
    gUI.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    gLogic.setSize(720, 1280);
    gLogic.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2, Align.center);
    gShapeRender.setSize(720, 1280);
    gShapeRender.setPosition(GStage.getWorldWidth()/2, GStage.getWorldHeight()/2, Align.center);

    listObjGamePlay = new ArrayList<>();
    logic = Logic.getInstance();

    shape.initListItems();

    initAsset();
    createShapeMain("square_1",1,1);
    initLevel();
    initLogic();
    eventClick();

    createListObject();
    createCircleClick();

    numberObjects = (int) level.y;
    polygonAct = new PolygonAct(this, gLogic, this);
    polygonAct.setDeltaScl(numberObjects);

    createShaderAct();

    startScene = new StartScene(gUI, this);
    startScene.gStart.setPosition(0, 0);
    gUI.addActor(startScene.gStart);

    shapeMainCenter.getShape().setZIndex(1000);
    isTouchStage = false;
//    nextObj();

//    test();
  }

  //////////////////////////////////////////INIT////////////////////////////////////////////////////

  //caculate poin in center and radius
  private void createShaderAct() {
    float x = shapeMainCenter.getShape().getX();
    float y = shapeMainCenter.getShape().getY();
    shaderAct = new ShaderAct(gLogic, "shader_square", x, y);
    shapeMainCenter.getShape().setZIndex(1000);
  }

  //init level and reset level when end game
  private void initLevel() {
    if (level == null)
      level = new Vector3();
    level.x = Level.LV1.x;
    level.y = Level.LV1.y;
    level.z = Level.LV1.z;
  }

  private void initLogic() {
    logic.degree = 45;
    logic.calRC(shapeMainCenter.getShape());
  }

  private void initAsset() {
    Image bg = GUI.createImage(textureAtlas, "bg");
    assert bg != null;
    bg.setSize(GStage.getWorldWidth(), GStage.getWorldHeight());
    gMain.addActor(bg);

    gLogic.setZIndex(1000);
    gShapeRender.setZIndex(1000);
    gUI.setZIndex(1000);

    shape.createShape("obj_square_1", "square", "square_1");
    shape.createPointStart(gLogic);
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
        if (isTouchStage) {
          if (x < gsWidth) {
            shapeMainCenter.getShape().addAction(rotateBy(-45, .15f, fastSlow));
            shapeMainCenter.setVerShapeMain(-45, .5f, shapeMainCenter.getIdShape());
            shaderAct.actionShader(-45, .1f);
          }
          else {
            shapeMainCenter.getShape().addAction(rotateBy(45, .15f, fastSlow));
            shapeMainCenter.setVerShapeMain(45, .5f, shapeMainCenter.getIdShape());
            shaderAct.actionShader(45, .1f);
          }
        }
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  private void createShapeMain(String name, int bound, int idShape) {
    shapeMainCenter = new Object(name, bound);
    shapeMainCenter.setIdShape(idShape);
    shapeMainCenter.addScene(gLogic, gShapeRender);
    shapeMainCenter.getShape().setOrigin(Align.center);
    shapeMainCenter.getShape().setPosition(gLogic.getWidth()/2 - shapeMainCenter.getShape().getWidth()/2, gLogic.getHeight()/2 - shapeMainCenter.getShape().getHeight()/2);
    shapeMainCenter.getShape().setScale(.5f);
    shapeMainCenter.setVerShapeMain(0, .5f, idShape);
  }

  private void reInitShapeMain(String name, int bound, int idShape) {
    TextureRegion textureRegion = textureAtlas.findRegion(name);
    float width = textureRegion.getRegionWidth();
    float height = textureRegion.getRegionHeight();

    shapeMainCenter.degrees = 0;
    shapeMainCenter.getShape().setRotation(0);
    shapeMainCenter.getShape().setDrawable(new TextureRegionDrawable(textureRegion));
    shapeMainCenter.setIdShape(idShape);
    shapeMainCenter.getShape().setSize(width, height);
    shapeMainCenter.getShape().setOrigin(Align.center);
    shapeMainCenter.getShape().setPosition(gLogic.getWidth()/2 - shapeMainCenter.getShape().getWidth()/2, gLogic.getHeight()/2 - shapeMainCenter.getShape().getHeight()/2);
    shapeMainCenter.getShape().setScale(.5f);
    shapeMainCenter.setVerShapeMain(0, .5f, idShape);

    initLogic();
  }

  private void createListObject() {
    listObjGamePlay.add(shape.shape0);
    listObjGamePlay.add(shape.shape1);
    listObjGamePlay.add(shape.shape2);
  }

  //////////////////////////////////////////INIT////////////////////////////////////////////////////

  //////////////////////////////////////////GAME PLAY///////////////////////////////////////////////

  private void startGame(Object obj) {
    gLogic.addActor(obj);
    obj.addAction(GSimpleAction.simpleAction(this::moveObject));
  }

  private boolean moveObject(float dt, Actor a) {
    Object obj = (Object) a;
    Vector2 v = logic.posShowObj();

    obj.addScene(gLogic, gShapeRender);
    obj.setCollision(this);
    obj.setPos((int)v.x);

    gLogic.addActor(obj.gBorderSquare);
    GTween.action(obj.gBorderSquare, scaleTo(.4f, .4f, 1f, linear),
            () -> {
              gLogic.addActor(obj.gImgBack);
              obj.getShape().setZIndex(1000);
              obj.move(shapeMainCenter, level, logic, (int)v.x, (int)v.y);
              obj.gBorderSquare.remove();
            }
    );

    SequenceAction seq = sequence();
    seq.addAction(delay(level.z));
    seq.addAction(Actions.run(this::nextObj));

    gLogic.addAction(seq);

    return true;
  }

  public void nextObj() {
    turn++;
    if (turn <= numberObjects && !endGame) {
      try {
        startGame(getObject());
      }
      catch (Exception ex){ /*todo: post an event when object is null */ }
    }
  }

  @Override
  public void collided(Object obj) {
    float[] v = obj.getVertices();
    int id = obj.getId();
    int bound = obj.getBound();
    int p1 = obj.p1;
    int p2 = obj.p2;

//    Gdx.app.log("ppp", p1 + "  " + p2);

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
    float pOfM1 = logic.pointOfDomain(shapeMainCenter.getShape(), pObj1);
    float pOfM2 = logic.pointOfDomain(shapeMainCenter.getShape(), pObj2);

    //condition to continue game or end game
    if (bound == 0) {
      if (p1 < 0 && pOfM1 < 0 && p2 > 0 && pOfM2 > 0) {
//        Gdx.app.log("BOUND", bound + "  id: " + id);
        eftCollition(obj);
      }
      else if (p1 > 0 && pOfM1 > 0 && p2 < 0 && pOfM2 < 0) {
//        Gdx.app.log("BOUND", bound + "  id: " + id);
        eftCollition(obj);
      }
      else {
//        Gdx.app.log("END", "End Game!");
        eftEndGame(obj);
      }
    }
    else {
      if (bound > 0 && pOfM1 > 0 && pOfM2 > 0) {
//        Gdx.app.log("BOUND", bound + "");
        eftCollition(obj);
      }
      else if (bound < 0 && pOfM1 < 0 && pOfM2 < 0) {
//        Gdx.app.log("BOUND", bound + "  POM1:" + pOfM1 + "  POM2:" + pOfM2 + "  id: " + id);
        eftCollition(obj);
      }
      else {
//        Gdx.app.log("END", "End Game!");
        eftEndGame(obj);
      }
    }

    obj.rmActor();
  }

  private void eftCollition(Object obj) {
    if (obj.turn == numberObjects)
      polygonAct.updatePolyAct(true);
    else
      polygonAct.updatePolyAct(false);

    startScene.eftLbScore(10);

    Vector2 temp = logic.posOfAnim(obj);
    obj.anim.setiFinishAnim(this);
    obj.anim.start(gLogic, temp.x, temp.y, logic.getDegree(obj.getId()));
  }

  @Override
  public void finishedAnim(Object obj) {
    obj.remove();
  }

  private void eftEndGame(Object objj) {

    startScene.turnToContinue = objj.turn; //save position in obj make end game
    endGame = true;

    for (int i = 0; i < listObjGamePlay.size(); i++) {

      for (Object obj : listObjGamePlay.get(i))

        if (obj.isAlive) {

          Vector2 temp = logic.posOfAnim(obj);
          obj.anim.setiFinishAnim(this);
          obj.anim.start(gLogic, temp.x, temp.y, logic.getDegree(obj.getId()));

          obj.rmActor();

          obj.isAlive = false;
          obj.clear();
          obj.remove();
        }
    }
//    turn = 0; //todo: if no watch ads => reset turn = 0
    setTouchStage(false);

    startScene.eftContinue(false);
  }

  @Override
  public void nextLevel() {
    endGame = false;
    turn = 0;
    numberObjects += Config.NUMOBJECT_NEXTLEVEL;
    polygonAct.setDeltaScl(numberObjects);

    updateLevel();
    GTween.setTimeout(gLogic, 2f, this::nextObj);
  }

  private void updateLevel() {
    if (level.z < Config.TIMESHOW_MAX) {
      level.x -= .15f;
      level.y += 10;
      level.z = Config.TIMESHOW_MAX;

      if (level.x < Config.DURATION_MAX)
        level.x = Config.DURATION_MAX;
    }
    else {
      level.x -= .25f;
      level.y += 10;
      level.z -= .15f;

      if (level.z < Config.TIMESHOW_MAX)
        level.z = Config.TIMESHOW_MAX;
      if (level.x < Config.DURATION_MAX)
        level.x = Config.DURATION_MAX;
    }
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

  private void resetGame() {
    shape.resetItem();

    endGame = false;
    numberObjects = 5;
    turn = 0;
    polygonAct.setScalePolyAct(1f);
    polygonAct.setDeltaScl(numberObjects);

    initLevel();
  }

  //call method newGame() when end game
  public void newGame() {
    resetGame();
    GTween.setTimeout(gLogic, 1f, () -> {
      isTouchStage = true;
      nextObj();
    });
  }

  public void setTouchStage(boolean touchStage) {
    isTouchStage = touchStage;
  }

  //////////////////////////////////////////GAME PLAY///////////////////////////////////////////////

  //////////////////////////////////////////EVENT CHANGE SHAPE CENTER///////////////////////////////

  public void changeShapeMainCenter(String name, int idShape, String color) {
    String tempName = "obj_" + name;
    reInitShapeMain(name, 1, idShape);
    shape.changeItem(tempName, color);
  }

  public void changeShaderAct(String name) {
    float xx = shapeMainCenter.getShape().getX();
    float yy = shapeMainCenter.getShape().getY();
    shaderAct.reInitShader(xx, yy, textureAtlas.findRegion(shape.getTextureRegionShader(name)));
  }

  private void test() {
    Image square = GUI.createImage(textureAtlas, "square_1");
    square.setPosition(100, 100);
    gUI.addActor(square);

    Image circle = GUI.createImage(textureAtlas, "circle_2");
    circle.setPosition(300, 100);
    gUI.addActor(circle);

    square.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        changeShapeMainCenter("square_1", 1, "square_1");
        changeShaderAct("square_1");
        return super.touchDown(event, x, y, pointer, button);
      }
    });

    circle.addListener(new InputListener() {
      @Override
      public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        changeShapeMainCenter("circle_2", 0, "circle_2");
        changeShaderAct("circle_2");
        return super.touchDown(event, x, y, pointer, button);
      }
    });
  }

  //////////////////////////////////////////EVENT CHANGE SHAPE CENTER///////////////////////////////





































  @Override
  public void run() {

  }
}
