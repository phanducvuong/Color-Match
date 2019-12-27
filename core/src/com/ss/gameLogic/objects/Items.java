package com.ss.gameLogic.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ss.GMain;
import com.ss.core.util.GUI;
import com.ss.gameLogic.interfaces.IChangeLbItem;

public class Items {

  private TextureAtlas textureAtlas = GMain.textureAtlas;
  public int coin;
  public String name;
  public String nameJson;
  public Image item;
  public int idShape;
  public boolean isLock;
  public IChangeLbItem iChangeLbItem;

  Items(String textureRegion, int coin, int idShape, boolean isLock) {

    item = GUI.createImage(textureAtlas, textureRegion);
    nameJson = textureRegion;
    name = textureRegion.split("item_")[1].trim();
    this.coin = coin;
    this.isLock = isLock;
    this.idShape = idShape;

    assert item != null;
    item.addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        iChangeLbItem.changeLbItem(Items.this);
        super.clicked(event, x, y);
      }
    });
  }

  public String getName() { return name; }
  public Image getItem() { return item; }
}
