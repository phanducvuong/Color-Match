package com.ss.data;

import com.google.gson.annotations.SerializedName;

public class ItemShape {

  @SerializedName("nameShape")
  private String nameShape;

  @SerializedName("coin")
  private int coin;

  @SerializedName("isShape")
  private int idShape;

  @SerializedName("isLock")
  private boolean isLock;

  public ItemShape(String nameShape, int coin, int idShape, boolean isLock) {
    this.nameShape = nameShape;
    this.coin = coin;
    this.idShape = idShape;
    this.isLock = isLock;
  }

  public String getNameShape() {
    return nameShape;
  }

  public void setNameShape(String nameShape) {
    this.nameShape = nameShape;
  }

  public int getCoin() {
    return coin;
  }

  public void setCoin(int coin) {
    this.coin = coin;
  }

  public int getIdShape() {
    return idShape;
  }

  public void setIdShape(int idShape) {
    this.idShape = idShape;
  }

  public boolean getIsLock() {
    return isLock;
  }

  public void setIsLock(boolean lock) {
    isLock = lock;
  }
}
