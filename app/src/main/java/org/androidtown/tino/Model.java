package org.androidtown.tino;


import java.io.Serializable;

public class Model implements Serializable {

    private String itemName;
    private boolean isSelected;

    public Model(String itemName, boolean isSelected) {
        this.itemName = itemName;
        this.isSelected = isSelected;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
