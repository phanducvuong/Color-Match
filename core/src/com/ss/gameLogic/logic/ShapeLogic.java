package com.ss.gameLogic.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.core.util.GStage;

import java.util.Arrays;

public class ShapeLogic extends Actor {

  private Polygon polygon;
  private ShapeRenderer shapeR;
  private float[] vertices = new float[]{};

  public ShapeLogic(float[] vertices){
    polygon = new Polygon(vertices);
    shapeR = new ShapeRenderer();
    shapeR.setProjectionMatrix(GStage.getCamera().combined);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
    shapeR.begin(ShapeRenderer.ShapeType.Line);
    shapeR.setColor(255, 0, 0, 1);
    shapeR.polygon(polygon.getTransformedVertices());
    shapeR.end();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    if (vertices != null && vertices.length >= 3)
      polygon.setVertices(vertices);
  }

  public void setVertices(float[] v) {
    vertices = v;
  }

  public void Log() {
    Gdx.app.log("aaa", Arrays.toString(polygon.getVertices()));
  }

  public Polygon getPolygon() {
    return polygon;
  }
  public ShapeRenderer getShapeR() { return shapeR; }
  public float[] getVertices() { return vertices; }
}
