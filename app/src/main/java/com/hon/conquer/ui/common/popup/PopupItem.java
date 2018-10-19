package com.hon.conquer.ui.common.popup;

/**
 * Created by Frank_Hon on 10/19/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class PopupItem {

    private String desc;
    private int icon;

    public PopupItem(String desc, int icon) {
        this.desc = desc;
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
