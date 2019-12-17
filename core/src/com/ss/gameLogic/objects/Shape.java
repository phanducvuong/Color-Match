package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Colors;
import com.ss.gameLogic.config.Config;
import java.util.ArrayList;
import java.util.List;

public class Shape {

  private static Shape instance;

  public List<Object> shape0 = new ArrayList<>();
  public List<Object> shape1 = new ArrayList<>();
  public List<Object> shape2 = new ArrayList<>();

  public static Shape getInstance() {
    return instance == null ? instance = new Shape() : instance;
  }

  public void createShape(String name0, String name1) {
    for (int i = 0; i < 15; i++) {
      Object O1 = new Object(name0, 0);
      O1.setColorObject(Colors.SUNSET_ORANGE, Colors.CLARET, null, null);

      Object O2 = new Object(name1, -1);
      O2.setColorObject(Colors.SUNSET_ORANGE, Colors.SUNSET_ORANGE, Colors.SUNSET_ORANGE, Colors.SUNSET_ORANGE);

      Object O3 = new Object(name1, 1);
      O3.setColorObject(Colors.CLARET, Colors.CLARET, Colors.CLARET, Colors.CLARET);

      shape0.add(O1);
      shape1.add(O2);
      shape2.add(O3);
    }
  }

  public void createPointStart(Group gUI) {
    //polygon in center
    Image polygonCenter = GUI.createImage(GMain.textureAtlas, "polygon_center");
    assert polygonCenter != null;
    polygonCenter.setPosition(gUI.getWidth()/2 - polygonCenter.getWidth()/2, gUI.getHeight()/2 - polygonCenter.getHeight()/2);
    gUI.addActor(polygonCenter);

    //point start
    for (int i = 0; i < 6; i++) {
      Image img = GUI.createImage(GMain.textureAtlas, "point_start");
      assert img != null;
      if (i == 1 || i == 4) {
        img.setOrigin(Align.center);
        img.rotateBy(45);
      }
      img.setPosition(Config.arrPos.get(i).x, Config.arrPos.get(i).y);
      gUI.addActor(img);
    }
  }

  public boolean overlaps(Polygon polygon, Circle circle) {
    float []vertices = polygon.getTransformedVertices();
    Vector2 center = new Vector2(circle.x, circle.y);
    float squareRadius=circle.radius*circle.radius;

    for (int i=0 ; i<vertices.length; i += 2){
      if (i == 0){
        if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length-2], vertices[vertices.length-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
          return true;
      } else {
        if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
          return true;
      }
    }
    return false;
  }
}
