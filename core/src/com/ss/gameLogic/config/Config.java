package com.ss.gameLogic.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Config {
  public static float ratio = Gdx.graphics.getWidth() / 720;
  public static final float MOVEBY = 600;
  public static final float ODDPOINTSTART = 31;

  //time for obj in pos1 and pos 4
  public static final float DELTATIME = 1.75f;

  public static final Vector2 POST1 = new Vector2(60, 340);
  public static final Vector2 POST2 = new Vector2(342, 220);
  public static final Vector2 POST3 = new Vector2(624, 340);
  public static final Vector2 POST4 = new Vector2(624, 900);
  public static final Vector2 POST5 = new Vector2(342, 1020);
  public static final Vector2 POST6 = new Vector2(60, 900);

  public static List<Vector2> arrPos = new ArrayList<>();

  public static void addArrPos() {
    arrPos.add(POST1);
    arrPos.add(POST2);
    arrPos.add(POST3);
    arrPos.add(POST4);
    arrPos.add(POST5);
    arrPos.add(POST6);
  }
}
