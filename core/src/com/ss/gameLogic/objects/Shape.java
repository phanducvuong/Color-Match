package com.ss.gameLogic.objects;

import java.util.Stack;

public class Shape {

  private static Shape instance;

  public Stack<Object> shape0 = new Stack<>();
  public Stack<Object> shape1 = new Stack<>();
  public Stack<Object> shape2 = new Stack<>();

  public static Shape getInstance() {
    return instance == null ? instance = new Shape() : instance;
  }

  public void createShape(String name) {
    for (int i = 0; i < 15; i++) {
      shape0.push(new Object(name, 0));
      shape1.push(new Object(name, -1));
      shape2.push(new Object(name, 1));
    }
  }
}
