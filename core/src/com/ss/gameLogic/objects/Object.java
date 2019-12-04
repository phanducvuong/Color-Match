package com.ss.gameLogic.objects;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.util.GUI;
import com.ss.gameLogic.config.Config;
import com.ss.gameLogic.logic.ShapeLogic;

public class Object {

    private Group gUI, gLogic;
    private Image shape;
    private ShapeLogic shapeLogic;
    private float[] vertices;
    private int collision;
    private float degrees = 0;

    public Object(Group gUI, Group gLogic, String name){
        this.gUI = gUI;
        this.gLogic = gLogic;
        this.vertices = new float[]{};

        this.shape = GUI.createImage(GMain.textureAtlas, name);
        assert this.shape != null;
        gUI.addActor(this.shape);

        vertices = new float[]{0,0,0,0,0,0,0,0};
        this.shapeLogic = new ShapeLogic(vertices);
        gLogic.addActor(this.shapeLogic);
    }

    public void setPos(int pos) {
        switch (pos) {
            case 1:

                shape.setPosition(Config.POST1.x, Config.POST1.y);
                setVerLR(shape, vertices);

                break;
            case 2:

                shape.setPosition(Config.POST2.x, Config.POST2.y);
                shape.setOrigin(Align.center);
                shape.rotateBy(45);

                setVerCenter(shape, vertices);

                break;
            case 3:

                shape.setPosition(Config.POST3.x, Config.POST3.y);
                setVerLR(shape, vertices);

                break;
            case 4:

                shape.setPosition(Config.POST4.x, Config.POST4.y);
                setVerLR(shape, vertices);

                break;
            case 5:

                shape.setPosition(Config.POST5.x, Config.POST5.y);
                shape.setOrigin(Align.center);
                shape.rotateBy(45);

                setVerCenter(shape, vertices);

                break;
            case 6:

                shape.setPosition(Config.POST6.x, Config.POST6.y);
                setVerLR(shape, vertices);

                break;
        }
    }

    public void setVerShapeMain(float deg, float scl) {
        degrees += deg;
        Vector2 c = new Vector2(shape.getX() + shape.getWidth()*scl, shape.getY() + shape.getHeight()*scl);
        float r = (float)Math.sqrt(shape.getWidth()/2 * scl * shape.getWidth()/2 * scl + shape.getHeight()/2 * scl * shape.getHeight()/2 * scl);

        if (degrees % 2 == 0) {
            vertices[0] = shape.getX() + shape.getWidth()/2 * scl; vertices[1] = shape.getY() + shape.getHeight()/2 * scl;
            vertices[2] = vertices[0] + shape.getWidth() * scl; vertices[3] = vertices[1];
            vertices[4] = vertices[2]; vertices[5] = vertices[3] + shape.getWidth()*scl;
            vertices[6] = vertices[0]; vertices[7] = vertices[5];
        }
        else {
            vertices[0] = c.x; vertices[1] = c.y - r;
            vertices[2] = c.x + r; vertices[3] = c.y;
            vertices[4] = c.x; vertices[5] = c.y + r;
            vertices[6] = c.x - r; vertices[7] = c.y;
        }

        shapeLogic.setVertices(vertices);
    }

    private void setVerLR(Image s, float[] v) {
        v[0] = s.getX(); v[1] = s.getY();
        v[2] = s.getX() + s.getWidth(); v[3] = v[1];
        v[4] = v[2]; v[5] = s.getY() + s.getHeight();
        v[6] = s.getX(); v[7] = v[5];
    }

    private void setVerCenter(Image s, float[] v) {
        Vector2 c = new Vector2(s.getX() + s.getWidth()/2, s.getY() + s.getHeight()/2);
        float r = (float)Math.sqrt(s.getWidth()/2 * s.getWidth()/2 + s.getHeight()/2 * s.getHeight()/2);

        v[0] = c.x; v[1] = c.y - r;
        v[2] = c.x + r; v[3] = c.y;
        v[4] = c.x; v[5] = c.y + r;
        v[6] = c.x - r; v[7] = c.y;
    }

    public void setVertices(float[] v) {
        shapeLogic.setVertices(v);
    }

    public Image getShape() {return shape;}

    public Polygon getPolygon() { return shapeLogic.getPolygon(); }
}
