package com.company;

import javax.swing.*;

public class Player extends Sprite implements Commons {



    public Player() {
        initPlayer();
    }

    private void initPlayer() {
        ImageIcon ii;
        ii = new ImageIcon(ClassLoader.getSystemResource("player.png"));
        //int width = ii.getImage().getWidth(null);
        setImage(ii.getImage());
        int START_X = 270;
        setX(START_X);
        int START_Y = 480;
        setY(START_Y);
    }

    public void act() {
        x += dx;
        y += dy;

        if (x <= 2) {
            x = 2;
        }
        if (x >= BOARD_WIDTH - 2 * 42) {
            x = BOARD_WIDTH - 2 * 42;
        }
        if (y <= 2){
            y = 2;
        }
        if (y >= BOARD_HEIGHT - 2 * 65){
            y = BOARD_HEIGHT - 2 * 65;
        }
    }

    public void keyPressed(String key) {

        if (key.equals("LEFT")) {
            dx = -2;
            ImageIcon left = new ImageIcon(ClassLoader.getSystemResource("player left.png"));
            setImage(left.getImage());
        }
        if (key.equals("LEFT_RELEASED")) {
            dx = 0;
            ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("player.png"));
            setImage(ii.getImage());
        }
        if (key.equals("RIGHT")) {
            dx = 2;
            ImageIcon right = new ImageIcon(ClassLoader.getSystemResource("player right.png"));
            setImage(right.getImage());
        }
        if (key.equals("RIGHT_RELEASED")) {
            dx = 0;
            ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("player.png"));
            setImage(ii.getImage());
        }
        if (key.equals("UP")){
            dy = -2;
        }
        if (key.equals("UP_RELEASED")){
            dy = 0;
        }
        if(key.equals("DOWN")){
            dy = 2;
        }
        if(key.equals("DOWN_RELEASED")){
            dy = 0;
        }
    }

}