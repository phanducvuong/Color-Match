package com.ss.gameLogic.objects;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GUI;
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

  public void createShape(String name0, String name1, String name2) {
    for (int i = 0; i < 15; i++) {
      shape0.add(new Object(name0, 0));
      shape1.add(new Object(name1, -1));
      shape2.add(new Object(name2, 1));
    }
  }

  public void createPointStart(Group group) {
    //polygon in center
    Image polygonCenter = GUI.createImage(GMain.textureAtlas, "polygon_center");
    assert polygonCenter != null;
    polygonCenter.setPosition(group.getWidth()/2 - polygonCenter.getWidth()/2, group.getHeight()/2 - polygonCenter.getHeight()/2);
    group.addActor(polygonCenter);

    //point start
    for (int i = 0; i < 6; i++) {
      Image img = GUI.createImage(GMain.textureAtlas, "point_start");
      assert img != null;
      if (i == 1 || i == 4) {
        img.setOrigin(Align.center);
        img.rotateBy(45);
      }
      img.setPosition(Config.arrPos.get(i).x, Config.arrPos.get(i).y);
      group.addActor(img);
    }
  }
}
