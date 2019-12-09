package com.ss.gameLogic.objects;

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

  public void createShape(String name) {
    for (int i = 0; i < 15; i++) {
      shape0.add(new Object(name, 0));
      shape1.add(new Object(name, -1));
      shape2.add(new Object(name, 1));
    }
  }
}
