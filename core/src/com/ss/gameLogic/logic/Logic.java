package com.ss.gameLogic.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.gameLogic.config.Config;

import java.util.concurrent.CopyOnWriteArrayList;

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

  public float vDomain(Image shape, Image img) {
    float deg = 90 - (shape.getRotation() + degree);
    Vector2 a = new Vector2(c.x + r* MathUtils.cosDeg(deg), c.y - r*MathUtils.sinDeg(deg));
    Vector2 b = new Vector2(c.x - r* MathUtils.cosDeg(deg), c.y + r*MathUtils.sinDeg(deg));

    return (img.getX() - a.x)*(b.y - a.y) - (img.getY() - a.y)*(b.x - a.x);
  }

  public float vDomain(Image shape, Vector2 v) {
    float deg = 90 - (shape.getRotation() + degree);
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

  public Vector2 calPosObj(float x, float y, float p, int id) {

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
}
