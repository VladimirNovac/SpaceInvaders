package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SpaceInvaders implements Commons {
    JFrame vlad;
    Container con;
    JPanel splashScreen;
    JPanel startButtonPanel;
    JLabel startScreenLabel;
    JButton startButton;
    Font tittleFont = new Font("Helvetica", Font.PLAIN, 20);
    TitleScreenHandler titleScreenHandler = new TitleScreenHandler();
    Fonts fonts;


    public SpaceInvaders() {
        initUI();
    }

    private void initUI() {

        vlad = new JFrame();

        vlad.setTitle("Space Invaders");
        vlad.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        vlad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vlad.getContentPane().setBackground(Color.black);
        //vlad.setLayout(null);
        vlad.setLayout(new BorderLayout());
        vlad.setLocationRelativeTo(null);
        vlad.setResizable(false);
        vlad.setVisible(true);
        //con.setLayout(new BorderLayout(vlad));
        con = vlad.getContentPane();
        //con.setLayout(null);

        ImageIcon startScreenImage = new ImageIcon(ClassLoader.getSystemResource("startScreen.png"));
        startScreenLabel = new JLabel(startScreenImage);
        splashScreen = new JPanel();
        splashScreen.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        splashScreen.add(startScreenLabel);
        //splashScreen.setBackground(Color.darkGray);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(329, 600, 200, 100);
        startButtonPanel.setBackground(new Color(0, 0, 0, 0));

        startButton = new JButton("START");
        startButton.setBackground(Color.lightGray);
        startButton.setForeground(Color.white);
        fonts = new Fonts("ethnocentric rg");
        fonts.setFontSize(18f);
        startButton.setFont(fonts.getFont());
        startButton.addActionListener(titleScreenHandler);
        startButton.setFocusPainted(false);


        startButtonPanel.add(startButton);
        //splashScreen.add(startButtonLabel);

        con.add(startButtonPanel);
        con.add(splashScreen);

        con.validate();
    }

    public class TitleScreenHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startGame();
        }
    }


    public void startGame() {
        splashScreen.setVisible(false);
//        startButtonLabel.setVisible(false);
        startButton.setVisible(false);
        startButton.removeActionListener(titleScreenHandler);
        titleScreenHandler = null;
        vlad.remove(startButton);
//        vlad.remove(startButtonLabel);
        vlad.remove(splashScreen);
        vlad.getContentPane().add(new Board(vlad));
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            new SpaceInvaders();
            //ex.setVisible(true);
        });
    }


}

