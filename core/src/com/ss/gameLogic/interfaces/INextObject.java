package com.ss.gameLogic.interfaces;

import com.badlogic.gdx.math.Vector2;
import com.ss.gameLogic.objects.Object;

public interface INextObject {
  void nextMove(Object obj, Vector2 pos);
}
