package com.phongbm.ahihi;

public class NavigationItem {
    private int idAvatar;
    private String name, email;
    private int idIconMenu;
    private String nameMenu;

    public NavigationItem(int idAvatar, String name, String email,
                          int idIconMenu, String nameMenu) {
        this.idAvatar = idAvatar;
        this.name = name;
        this.email = email;
        this.idIconMenu = idIconMenu;
        this.nameMenu = nameMenu;
    }

    public int getIdAvatar() {
        return idAvatar;
    }

    public void setIdAvatar(int idAvatar) {
        this.idAvatar = idAvatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdIconMenu() {
        return idIconMenu;
    }

    public void setIdIconMenu(int idIconMenu) {
        this.idIconMenu = idIconMenu;
    }

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

}