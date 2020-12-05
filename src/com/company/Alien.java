package com.company;

import javax.swing.ImageIcon;

public class Alien extends Sprite {
    private Bomb bomb;

    public Alien(int x, int y) {
        initAlien(x, y);
    }

    private void initAlien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        //String alienImg = "src/images/alien.png";
        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("alien.png"));
        setImage(ii.getImage());
    }

    public void act(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

}