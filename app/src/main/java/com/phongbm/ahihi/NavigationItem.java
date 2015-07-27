package com.phongbm.ahihi;

public class NavigationItem {
    private int idIconMenu;
    private String nameMenu;

    public NavigationItem(int idIconMenu, String nameMenu) {
        this.idIconMenu = idIconMenu;
        this.nameMenu = nameMenu;
    }

    public int getIdIconMenu() {
        return idIconMenu;
    }

    public String getNameMenu() {
        return nameMenu;
    }

}