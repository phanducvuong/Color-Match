package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.action.exAction.GTemporalAction;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.interfaces.ICollision;
import com.ss.gameLogic.logic.Logic;
import com.ss.gameLogic.logic.ShapeLogic;

public class Object extends Actor {

  private Image shape;
  private ShapeLogic shapeLogic;
  private float[] vertices;
  private int id; //check special position
  public boolean isAlive = false;
  private int bound; // =1 + || =1 - || =0 half-color
  private float degrees = 0; //end game start degrees again
  private ICollision iCollision;
  public int p1 = 0;
  public int p2 = 0;
  private Color cLeft, cRight;
  private Image borderDown, borderUp;
  private Image imgBackLeft, imgBackRight;
  public Group gBorderSquare, gImgBack; //group contain border of object
  public int turn = 0; //turn to set zoom to polyAct

  public Object(String name, int bound) {
    this.gBorderSquare = new Group();
    this.gImgBack = new Group();
    this.bound = bound;
    this.vertices = new float[]{};

    this.shape = GUI.createImage(GMain.textureAtlas, name);

    vertices = new float[]{0,0,0,0,0,0,0,0};
    this.shapeLogic = new ShapeLogic(vertices);

    createBorder();
    createImgBack();
  }

  private void createImgBack() {
    imgBackLeft = GUI.createImage(GMain.textureAtlas, "half_back_obj");
    imgBackLeft.setPosition(1, 0);
    imgBackRight = GUI.createImage(GMain.textureAtlas, "half_back_obj");
    imgBackRight.setPosition(imgBackLeft.getX() - .5f + imgBackLeft.getWidth(), imgBackLeft.getY());
    gImgBack.addActor(imgBackLeft);
    gImgBack.addActor(imgBackRight);
    gImgBack.setOrigin(imgBackLeft.getWidth(), imgBackLeft.getHeight()/2);
  }

  private void createBorder() {
    borderDown = GUI.createImage(GMain.textureAtlas, "half_obj_down");
    borderUp = GUI.createImage(GMain.textureAtlas, "half_obj_up");
    gBorderSquare.addActor(borderDown);
    gBorderSquare.addActor(borderUp);
    gBorderSquare.setOrigin(borderDown.getWidth()/2, borderDown.getHeight()/2);
  }

  //update new position and vertices
  public void setPos(int pos) {

    shape.setRotation(0);

    switch (pos) {
      case 0:
        if (bound == 0) {
          int r = (int)Math.floor(Math.random() * 2);
          if (r == 0) {
            p1 = 1;
            p2 = -1;
            imgBackLeft.setColor(cRight);
            imgBackRight.setColor(cLeft);
          }
          else {
            p1 = -1;
            p2 = 1;
            imgBackLeft.setColor(cLeft);
            imgBackRight.setColor(cRight);
            shape.setOrigin(Align.center);
            shape.rotateBy(180);
            gBorderSquare.rotateBy(180);
          }
        }

        gBorderSquare.setPosition(Config.POST1.x - Config.ODDPOINTSTART, Config.POST1.y - Config.ODDPOINTSTART);
        shape.setPosition(Config.POST1.x, Config.POST1.y);
        gImgBack.setPosition(Config.POST_BACK1.x, Config.POST_BACK1.y);
        gImgBack.rotateBy(135);
        setVerLR(shape, vertices);

        break;
      case 1:
        shape.setOrigin(Align.center);
        if (bound == 0) {
          int r = (int)Math.floor(Math.random() * 2);

          if (r == 0) {
            p1 = 1;
            p2 = -1;
            imgBackLeft.setColor(cRight);
            imgBackRight.setColor(cLeft);
            shape.rotateBy(45);
            gBorderSquare.setRotation(45);
          }
          else {
            p1 = -1;
            p2 = 1;
            imgBackLeft.setColor(cLeft);
            imgBackRight.setColor(cRight);
            shape.rotateBy(225);
            gBorderSquare.setRotation(225);
          }
        }
        else {
          shape.rotateBy(45);
          gBorderSquare.setRotation(45);
        }

        gBorderSquare.setPosition(Config.POST2.x - Config.ODDPOINTSTART, Config.POST2.y - Config.ODDPOINTSTART);
        shape.setPosition(Config.POST2.x, Config.POST2.y);
        gImgBack.setPosition(Config.POST_BACK2.x, Config.POST_BACK2.y);
        gImgBack.rotateBy(180);

        setVerCenter(shape, vertices);

        break;
      case 2:
        if (bound == 0) {
          int r = (int)Math.floor(Math.random() * 2);

          shape.setOrigin(Align.center);
          id = 2;
          if (r == 0) {
            p1 = -1;
            p2 = 1;
            imgBackLeft.setColor(cRight);
            imgBackRight.setColor(cLeft);
            shape.rotateBy(90);
            gBorderSquare.rotateBy(90);
          }
          else {
            p1 = 1;
            p2 = -1;
            imgBackLeft.setColor(cLeft);
            imgBackRight.setColor(cRight);
            shape.rotateBy(270);
            gBorderSquare.rotateBy(270);
          }
        }

        gBorderSquare.setPosition(Config.POST3.x - Config.ODDPOINTSTART, Config.POST3.y - Config.ODDPOINTSTART);
        shape.setPosition(Config.POST3.x, Config.POST3.y);
        gImgBack.setPosition(Config.POST_BACK3.x, Config.POST_BACK3.y);
        gImgBack.rotateBy(-135);
        setVerLR(shape, vertices);

        break;
      case 3:
        if (bound == 0) {
          int r = (int)Math.floor(Math.random() * 2);

          if (r == 0) {
            p1 = 1;
            p2 = -1;
            imgBackLeft.setColor(cLeft);
            imgBackRight.setColor(cRight);
          }
          else {
            p1 = -1;
            p2 = 1;
            imgBackLeft.setColor(cRight);
            imgBackRight.setColor(cLeft);
            shape.setOrigin(Align.center);
            shape.rotateBy(180);
            gBorderSquare.rotateBy(180);
          }
        }

        gBorderSquare.setPosition(Config.POST4.x - Config.ODDPOINTSTART, Config.POST4.y - Config.ODDPOINTSTART);
        shape.setPosition(Config.POST4.x, Config.POST4.y);
        gImgBack.setPosition(Config.POST_BACK4.x, Config.POST_BACK4.y);
        gImgBack.rotateBy(-45);
        setVerLR(shape, vertices);

        break;
      case 4:
        shape.setOrigin(Align.center);
        if (bound == 0) {
          int r = (int)Math.floor(Math.random() * 2);

          if (r == 0) {
            p1 = 1;
            p2 = -1;
            imgBackLeft.setColor(cLeft);
            imgBackRight.setColor(cRight);
            shape.rotateBy(45);
            gBorderSquare.rotateBy(45);
          }
          else {
            p1 = -1;
            p2 = 1;
            imgBackLeft.setColor(cRight);
            imgBackRight.setColor(cLeft);
            shape.rotateBy(225);
            gBorderSquare.rotateBy(225);
          }
        }
        else {
          shape.rotateBy(45);
          gBorderSquare.rotateBy(45);
        }

        gBorderSquare.setPosition(Config.POST5.x - Config.ODDPOINTSTART, Config.POST5.y - Config.ODDPOINTSTART);
        shape.setPosition(Config.POST5.x, Config.POST5.y);
        gImgBack.setPosition(Config.POST_BACK5.x, Config.POST_BACK5.y);

        setVerCenter(shape, vertices);

        break;
      case 5:
        if (bound == 0) {
          int r = (int)Math.floor(Math.random() * 2);

          shape.setOrigin(Align.center);
          id = 5;
          if (r == 0) {
            p1 = -1;
            p2 = 1;
            imgBackLeft.setColor(cLeft);
            imgBackRight.setColor(cRight);
            shape.rotateBy(90);
            gBorderSquare.rotateBy(90);
          }
          else {
            p1 = 1;
            p2 = -1;
            imgBackLeft.setColor(cRight);
            imgBackRight.setColor(cLeft);
            shape.rotateBy(270);
            gBorderSquare.rotateBy(270);
          }
        }

        gBorderSquare.setPosition(Config.POST6.x - Config.ODDPOINTSTART, Config.POST6.y - Config.ODDPOINTSTART);
        shape.setPosition(Config.POST6.x, Config.POST6.y);
        gImgBack.setPosition(Config.POST_BACK6.x, Config.POST_BACK6.y);
        gImgBack.rotateBy(45);
        setVerLR(shape, vertices);

        break;
    }
  }

  //change shapeLogic shape in center
  public void setVerShapeMain(float deg, float scl) {
    degrees += deg;
    Vector2 c = new Vector2(shape.getX() + shape.getWidth()*scl, shape.getY() + shape.getHeight()*scl);
    float r = (float)Math.sqrt(shape.getWidth()/2 * scl * shape.getWidth()/2 * scl + shape.getHeight()/2 * scl * shape.getHeight()/2 * scl);

    if (degrees % 2 == 0) {
      vertices[0] = shape.getX() + shape.getWidth()/2 * scl; vertices[1] = shape.getY() + shape.getHeight()/2 * scl;
      vertices[2] = vertices[0] + shape.getWidth() * scl; vertices[3] = vertices[1];
      vertices[4] = vertices[2]; vertices[5] = vertices[3] + shape.getWidth()*scl;
      vertices[6] = vertices[0]; vertices[7] = vertices[5];
    }
    else {
      vertices[0] = c.x; vertices[1] = c.y - r;
      vertices[2] = c.x + r; vertices[3] = c.y;
      vertices[4] = c.x; vertices[5] = c.y + r;
      vertices[6] = c.x - r; vertices[7] = c.y;
    }

    shapeLogic.setVertices(vertices);
  }

  private void setVerLR(Image s, float[] v) {
    v[0] = s.getX(); v[1] = s.getY();
    v[2] = s.getX() + s.getWidth(); v[3] = v[1];
    v[4] = v[2]; v[5] = s.getY() + s.getHeight();
    v[6] = s.getX(); v[7] = v[5];

    shapeLogic.setVertices(v);
  }

  private void setVerCenter(Image s, float[] v) {
    Vector2 c = new Vector2(s.getX() + s.getWidth()/2, s.getY() + s.getHeight()/2);
    float r = (float)Math.sqrt(s.getWidth()/2 * s.getWidth()/2 + s.getHeight()/2 * s.getHeight()/2);

    v[0] = c.x; v[1] = c.y - r;
    v[2] = c.x + r; v[3] = c.y;
    v[4] = c.x; v[5] = c.y + r;
    v[6] = c.x - r; v[7] = c.y;

    shapeLogic.setVertices(v);
  }

  //set vertices
  private void setVertices(float[] v) {
    vertices = v;
    shapeLogic.setVertices(v);
  }

  public void move(Object box, Vector3 lv, Logic logic, int id, int quadrant) {
    float xS = shape.getX();
    float yS = shape.getY();
    float xB = gImgBack.getX();
    float yB = gImgBack.getY();
    float duration = lv.x;

    if (id == 1 || id == 4)
      duration -= Config.DELTATIME;

    gImgBack.addAction(Actions.alpha(1, 2.5f, Interpolation.linear));

    shape.addAction(GTemporalAction.add(duration, (p, a) -> {
      Vector2 tempPosShape = new Vector2(xS, yS);
      Vector2 temPosBackObj = new Vector2(xB, yB);

      if (Intersector.overlapConvexPolygons(this.getPolygon(), box.getPolygon())) {
        shape.clear();
        gImgBack.clearActions();
        iCollision.collided(this);
      }

      tempPosShape = logic.calPosObjWhileMoving(tempPosShape.x, tempPosShape.y, p, id);
      temPosBackObj = logic.calPosObjWhileMoving(temPosBackObj.x, temPosBackObj.y, p, id);
      this.setVertices(logic.calVertices(shape, tempPosShape.x, tempPosShape.y, quadrant));
      shape.setPosition(tempPosShape.x, tempPosShape.y);
      gImgBack.setPosition(temPosBackObj.x, temPosBackObj.y);
    }));
  }

  public void addScene(Group gUI, Group gShapeRender) {
    gUI.addActor(shape);
    gShapeRender.addActor(shapeLogic);
  }

  @Override
  public boolean remove() {
    p1 = 0;
    p2 = 0;
    id = -1;
    turn = 0;
    isAlive = false;

    shape.remove();
    shapeLogic.remove();

    gBorderSquare.setRotation(0);
    gBorderSquare.setScale(1);

    gImgBack.remove();
    gImgBack.getColor().a = 0;
    gImgBack.setRotation(0);
    return super.remove();
  }

  @Override
  public void clear() {
    super.clear();
    gBorderSquare.clearActions();
    shape.clear();
  }

  public Image getShape() {return shape;}

  private Polygon getPolygon() { return shapeLogic.getPolygon(); }

  public void setId(int id) { this.id = id; }

  public int getId() { return this.id; }

  public int getBound() { return bound; }

  public void setCollision(ICollision iCollision) {
    this.iCollision = iCollision;
  }

  public float[] getVertices() { return vertices; }

  public void setColorObject(Color cDown, Color cUp, Color cShape, Color cBackObj) {
    cLeft = cDown;
    cRight = cUp;

    borderDown.setColor(cDown);
    borderUp.setColor(cUp);
    if (cShape != null && cBackObj != null) {
      shape.setColor(cShape);
      imgBackLeft.setColor(cBackObj);
      imgBackRight.setColor(cBackObj);
    }
    gImgBack.getColor().a = 0;
  }
}
