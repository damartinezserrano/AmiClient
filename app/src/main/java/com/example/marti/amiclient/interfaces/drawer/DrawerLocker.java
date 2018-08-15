package com.example.marti.amiclient.interfaces.drawer;

/**
 * Created by Marti on 13/08/18.
 */

public interface DrawerLocker {
    public void setDrawerEnabled(boolean enabled);
    public void hideToolbar();
    public void showToolbar();
    public void toolbarBackground(int color);
    public void hamburgerColor(int color);
}
