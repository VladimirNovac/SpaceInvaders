package com.company;

import javax.swing.ImageIcon;


public class Boss extends Sprite {
    private Bomb bomb;
    private ImageIcon ii;

    public Boss(int x, int y){
        initBoss(x,y);
    }

    private void initBoss(int x, int y){
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        //String bossImg = "src/images/bossImg.png";
        ii = new ImageIcon(ClassLoader.getSystemResource("bossImg.png"));
        setImage(ii.getImage());
        setVisible(false);
    }

    public void act(int direction) {
        this.x += direction;
    }

    public Bomb getBomb() {
        return bomb;
    }

}
