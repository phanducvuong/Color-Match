package com.ss.gameLogic.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.ss.GMain;
import com.ss.core.util.GUI;
import com.ss.data.ItemShape;
import com.ss.gameLogic.config.Colors;
import com.ss.gameLogic.config.Config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Shape {

  private static Shape instance;
  private Colors colorShape = Colors.getInstance();
  public List<ItemShape> itemShapeList;

  public Color c1, c2;

  public List<Object> shape0 = new ArrayList<>();
  public List<Object> shape1 = new ArrayList<>();
  public List<Object> shape2 = new ArrayList<>();

  public List<Items> listItems = new ArrayList<>();

  public static Shape getInstance() {
    return instance == null ? instance = new Shape() : instance;
  }

  //name0: object have two colors || name1: object have one color || color: name order to get color
  public void createShape(String name0, String name1, String color) {
    List<Color> colors = colorShape.colors(color);
    Color c1 = colors.get(0);
    Color c2 = colors.get(1);

    for (int i = 0; i < 15; i++) {
      Object O1 = new Object(name0, 0);
      O1.setColorObject(c1, c2, null, null);

      Object O2 = new Object(name1, -1);
      O2.setColorObject(c1, c1, c1, c1);

      Object O3 = new Object(name1, 1);
      O3.setColorObject(c2, c2, c2, c2);

      shape0.add(O1);
      shape1.add(O2);
      shape2.add(O3);
    }
  }

  public void changeItem(String name, String color) {

    List<Color> colors = colorShape.colors(color);
    c1 = colors.get(0);
    c2 = colors.get(1);

    for (Object obj : shape0) {
      obj.setDrawable(GMain.textureAtlas.findRegion(name));
      obj.setColorObject(c1, c2, null, null);
      obj.remove();
    }

    for (int i=0; i<shape1.size(); i++) {
      shape1.get(i).setColorObject(c1, c1, c1, c1);
      shape2.get(i).setColorObject(c2, c2, c2, c2);

      shape1.get(i).remove();
      shape2.get(i).remove();
    }
  }

  public String getTextureRegionShader(String name) {
    if (name.equals("circle_1") || name.equals("circle_2") || name.equals("circle_3"))
      return "shader_circle_1";
    else if (name.equals("circle_4"))
      return "shader_circle_4";
    else if (name.equals("circle_5"))
      return "shader_circle_5";
    else if (name.equals("flower_1") || name.equals("flower_2") || name.equals("flower_3"))
      return "shader_flower";
    else if (name.equals("sun_flower_1") || name.equals("sun_flower_2") || name.equals("sun_flower_3"))
      return "shader_sun_flower";
    else if (name.equals("hexagon_1") || name.equals("hexagon_2") || name.equals("hexagon_3"))
      return "shader_hexagon";
    else if (name.equals("pinwheel_1") || name.equals("pinwheel_2") || name.equals("pinwheel_3"))
      return "shader_pinwheel";
    else return "shader_square";
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

  public void resetItem() {
    for (int i=0; i<shape0.size(); i++) {
      shape0.get(i).remove();
      shape0.get(i).clear();

      shape1.get(i).remove();
      shape1.get(i).clear();

      shape2.get(i).remove();
      shape2.get(i).clear();
    }
  }

  public void initListItems() {

    readFileJson();

    for (ItemShape i : itemShapeList) {
      Items items = new Items(i.getNameShape(), i.getCoin(), i.getIdShape(), i.getIsLock());
      listItems.add(items);
    }
  }

  private void readFileJson() {

    Gson gson = new Gson();
    itemShapeList = new ArrayList<>();

    try {

      BufferedReader buf = new BufferedReader(new FileReader("data.json"));
      JsonArray jsonArray = gson.fromJson(buf, JsonArray.class);

      for (int i=0; i<jsonArray.size();i++) {
        ItemShape itemShape = gson.fromJson(jsonArray.get(i), ItemShape.class);
        itemShapeList.add(itemShape);
      }

    } catch (FileNotFoundException e) { e.printStackTrace(); }
  }

  public void writeFileJson(Items itemsChange) {

    Gson gson = new Gson();
    notifyListItem(itemsChange);
    try {

      String json = gson.toJson(itemShapeList);

      FileWriter fWriter = new FileWriter("data.json");
      fWriter.write(json);
      fWriter.close();

    }
    catch (Exception ex) { ex.printStackTrace(); }
  }

  private void notifyListItem(Items itemsChange) {

    for (ItemShape items : itemShapeList) {
      if (items.getNameShape().equals(itemsChange.nameJson)) {
        items.setIsLock(false);
        break;
      }
    }
  }
}
