package com.ss.gameLogic.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.objects.Object;
import com.ss.gameLogic.objects.Shape;

import java.util.List;

public class Logic {
  private static Logic instance;
  private float r;
  private Vector2 c;
  public float degree;

  public static Logic getInstance() {
    return instance == null ? instance = new Logic() : instance;
  }

  public void calRC(Image img) {
    c = new Vector2(img.getX() + img.getWidth()/2, img.getY() + img.getHeight()/2);
    r = c.dst(img.getX(), img.getY());
  }

  //check point belong to what domain
  public float pointOfDomain(Image objC, Image obj) {
    float deg = 90 - (objC.getRotation() + degree);
    Vector2 a = new Vector2(c.x + r* MathUtils.cosDeg(deg), c.y - r*MathUtils.sinDeg(deg));
    Vector2 b = new Vector2(c.x - r* MathUtils.cosDeg(deg), c.y + r*MathUtils.sinDeg(deg));

    return (obj.getX() - a.x)*(b.y - a.y) - (obj.getY() - a.y)*(b.x - a.x);
  }

  public float pointOfDomain(Image objC, Vector2 v) {
    float deg = 90 - (objC.getRotation() + degree);
    Vector2 a = new Vector2(c.x + r* MathUtils.cosDeg(deg), c.y - r*MathUtils.sinDeg(deg));
    Vector2 b = new Vector2(c.x - r* MathUtils.cosDeg(deg), c.y + r*MathUtils.sinDeg(deg));

    return (v.x - a.x)*(b.y - a.y) - (v.y - a.y)*(b.x - a.x);
  }

  public float[] calVertices(Image box, float x, float y, int quadrant) {
    float[] v;
    if (quadrant == 0) {
      v = new float[] {
              x, y,
              x + box.getWidth(), y,
              x + box.getWidth(), y + box.getHeight(),
              x, y + box.getHeight()
      };
    }
    else {
      Vector2 c = new Vector2(x + box.getWidth()/2, y + box.getHeight()/2);
      float r = (float)Math.sqrt(box.getWidth()/2 * box.getWidth()/2 + box.getHeight()/2 * box.getHeight()/2);
      v = new float[] {
              c.x, c.y - r,
              c.x + r, c.y,
              c.x, c.y + r,
              c.x - r, c.y
      };
    }

    return v;
  }

  public Vector2 calPosObjWhileMoving(float x, float y, float p, int id) {

    Vector2 temp = new Vector2();
    switch (id) {
      case 0:
        temp.x = x + Config.MOVEBY*p;
        temp.y = y + Config.MOVEBY*p;
        break;
      case 1:
        temp.x = x;
        temp.y = y + Config.MOVEBY*p;
        break;
      case 2:
        temp.x = x - Config.MOVEBY*p;
        temp.y = y + Config.MOVEBY*p;
        break;
      case 3:
        temp.x = x - Config.MOVEBY*p;
        temp.y = y - Config.MOVEBY*p;
        break;
      case 4:
        temp.x = x;
        temp.y = y - Config.MOVEBY*p;
        break;
      case 5:
        temp.x = x + Config.MOVEBY*p;
        temp.y = y - Config.MOVEBY*p;
        break;
    }

    return temp;
  }

  public Vector2 posShowObj() {
    int pos = (int) Math.floor(Math.random() * 6);
//    Gdx.app.log("POS", pos + "");
    Vector2 v = new Vector2(); //x: position, y: degrees
    switch (pos) {
      case 0:
        v.x = 0;
        v.y = 0;
        break;
      case 1:
        v.x = 1;
        v.y = 45;
        break;
      case 2:
        v.x = 2;
        v.y = 0;
        break;
      case 3:
        v.x = 3;
        v.y = 0;
        break;
      case 4:
        v.x = 4;
        v.y = 45;
        break;
      case 5:
        v.x = 5;
        v.y = 0;
        break;
    }
    return v;
  }

  public float getDegree(int id) {

    float deg = 0;

    switch (id) {
      case 0: deg = -45; break;
      case 1: deg = 0; break;
      case 2: deg = 45; break;
      case 3: deg = 135; break;
      case 4: deg = 180; break;
      case 5: deg = -135; break;
    }

    return deg;
  }

  public Vector2 posOfAnim(Object obj) {
    float x = 0, y = 0;

    switch (obj.getId()) {
      case 0:
        x = obj.getShape().getX() - 15;
        y = obj.getShape().getY() - 15;
        break;
      case 1:
        x = obj.getShape().getX() + 20;
        y = obj.getShape().getY() - 30;
        break;
      case 2:
        x = obj.getShape().getX() + 70;
        y = obj.getShape().getY() - 15;
        break;
      case 3:
        x = obj.getShape().getX() + 55;
        y = obj.getShape().getY() + 65;
        break;
      case 4:
        x = obj.getShape().getX() + 25;
        y = obj.getShape().getY() + 65;
        break;
      case 5:
        x = obj.getShape().getX() - 15;
        y = obj.getShape().getY() + 55;
        break;
    }

    return new Vector2(x, y);
  }

//  public Object getObject(List<Shape> listShape) {
//    int i = (int) Math.floor(Math.random() * 3);
//
//    for (Object o : listShape.get(i))
//      if (!o.isAlive) {
//        o.turn = turn;
//        o.isAlive = true;
//        o.clear();
//        return o;
//      }
//    return null;
//  }

//  public boolean
}
