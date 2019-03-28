package com.hon.pagerecyclerview.item;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class LoadingPageItem implements PageItem {
    @Override
    public int itemType() {
        return PageItem.LOADING_ITEM;
    }
}
