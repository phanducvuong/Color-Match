package com.ss.gameLogic.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.core.util.GStage;

import java.util.Arrays;

public class ShapeLogic extends Actor {

  private Polygon polygon;
  private Circle circle;
  private ShapeRenderer shapeR;
  private float[] vertices = new float[]{};

  public ShapeLogic(float[] vertices) {
    circle = new Circle();
    polygon = new Polygon(vertices);
    shapeR = new ShapeRenderer();
    shapeR.setProjectionMatrix(GStage.getCamera().combined);
  }

  public void freeShapeLogic() {
    circle = null;
    polygon = null;
    shapeR = null;
    vertices = null;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
//    batch.end();
//
//    try {
//      shapeR.begin(ShapeRenderer.ShapeType.Line);
//      shapeR.setColor(255, 0, 0, 1);
//      shapeR.polygon(polygon.getTransformedVertices());
//      shapeR.circle(circle.x, circle.y, circle.radius);
//      shapeR.end();
//    }
//    catch (Exception ex) {}
//
//    batch.begin();
    super.draw(batch, parentAlpha);
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    if (vertices != null && vertices.length >= 3)
      polygon.setVertices(vertices);
  }

  public void setCircle(float x, float y, float radius) {
    circle.set(x, y, radius);
  }

  public void setVertices(float[] v) {
    vertices = v;
  }

  public void Log() {
    Gdx.app.log("aaa", Arrays.toString(polygon.getVertices()));
  }

  public Circle getCircle() { return circle; }
  public Polygon getPolygon() {
    return polygon;
  }
  public ShapeRenderer getShapeR() { return shapeR; }
  public float[] getVertices() { return vertices; }
}
