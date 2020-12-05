package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.*;

public class Board extends JPanel implements Runnable, Commons {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension d;
    private ArrayList<Alien> aliens;
    private Player player;
    private Shot shot;
    private Boss boss;

    private int direction = -1;
    private int deaths = 0;
    private int score = 0;
    private int aliensRemaining = 24;
    private int bossCounter = 0;

    private boolean inGame = true;
    private String message = "Game Over";

    //private Sound sound = null;
    private music music;
    private Thread animator;

    private JPanel gameOverPanel;
    private JButton gameOverButton;
    private GameOverHandler gameOverHandler = new GameOverHandler();
    JFrame vlad;
    //private boolean gameWon = false;

    public Board(JFrame vlad) {
        this.vlad = vlad;
        initBoard();
    }

    private void initBoard() {


        this.getInputMap(WHEN_IN_FOCUSED_WINDOW ).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0,false),"upAction");
        this.getActionMap().put("upAction", new KeyBoardControl("UP"));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW ).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0,true),"upAction_Released");
        this.getActionMap().put("upAction_Released", new KeyBoardControl("UP_RELEASED"));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW ).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0,false),"downAction");
        this.getActionMap().put("downAction", new KeyBoardControl("DOWN"));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW ).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0,true),"downAction_Released");
        this.getActionMap().put("downAction_Released", new KeyBoardControl("DOWN_RELEASED"));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW ).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0,false),"leftAction");
        this.getActionMap().put("leftAction", new KeyBoardControl("LEFT"));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW ).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0,true),"leftAction_Released");
        this.getActionMap().put("leftAction_Released", new KeyBoardControl("LEFT_RELEASED"));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW ).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0,false),"rightAction");
        this.getActionMap().put("rightAction", new KeyBoardControl("RIGHT"));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW ).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0,true),"rightAction_Released");
        this.getActionMap().put("rightAction_Released", new KeyBoardControl("RIGHT_RELEASED"));
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW ).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0,false),"space");
        this.getActionMap().put("space", new KeyBoardControl("SPACE"));

        this.setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);
        gameInit();
        setDoubleBuffered(true);
    }




    public void gameInit() {
        aliens = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                int ALIEN_INIT_Y = 5;
                int ALIEN_INIT_X = 150;
                Alien alien = new Alien(ALIEN_INIT_X + 50 * j, ALIEN_INIT_Y + 42 * i);
                aliens.add(alien);
            }
        }


        player = new Player();
        shot = new Shot();
        boss = new Boss(BOARD_WIDTH / 2, 5);



        if (animator == null || !inGame) {
            animator = new Thread(this);
            animator.start();
            music = new music(ClassLoader.getSystemResource("backgroundMusic.wav"));
        }
    }

    public class GameOverHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            vlad.dispose();
            new SpaceInvaders();
        }
    }


    public void drawAliens(Graphics g) {
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    public void drawPlayer(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
        if (player.isDying()) {
            player.die();
            music.stopMusic();
            inGame = false;
        }
    }

    public void drawBoss(Graphics g) {
        if (boss.isVisible()) {
            g.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);
        }
        if (boss.isDying()) {
            boss.die();
            music.stopMusic();
            inGame = false;
        }
    }

    public void drawShot(Graphics g) {
        if (shot.isVisible()) {
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    public void drawBombing(Graphics g) {
        for (Alien a : aliens) {
            Bomb b = a.getBomb();
            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);

        g.setColor(Color.green);
        ImageIcon backgroundImg = new ImageIcon(ClassLoader.getSystemResource("background.png"));
        g.drawImage(backgroundImg.getImage(), 0, 0, d.width, d.height, this);

        g.setColor(Color.white);
        Fonts fonts = new Fonts("PressStart2P");
        g.setFont(fonts.getFont());
        g.drawString("SCORE " + score, 700, 30);

        ImageIcon alienSc = new ImageIcon(ClassLoader.getSystemResource("alienScore.png"));
        g.drawImage(alienSc.getImage(), 600, 8, 25, 25, this);


        g.setColor(Color.white);
        //g.setFont(new Font("Helvetica", Font.PLAIN,18));
        fonts = new Fonts("PressStart2P");
        g.setFont(fonts.getFont());
        g.drawString(" X " + aliensRemaining, 630, 30);

        if (inGame) {

            g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawBoss(g);
            drawShot(g);
            drawBombing(g);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void gameOver() {


        Graphics g = this.getGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(150, BOARD_WIDTH / 2 - 60, BOARD_WIDTH - 350, 115);
        g.setColor(Color.white);
        g.drawRect(150, BOARD_WIDTH / 2 - 60, BOARD_WIDTH - 350, 115);

        Fonts fonts = new Fonts("ethnocentric rg");
        fonts.setFontSize(36f);
        FontMetrics metrics = this.getFontMetrics(fonts.getFont());

        g.setColor(Color.white);
        g.setFont(fonts.getFont());
        g.drawString(message, (BOARD_WIDTH - metrics.stringWidth(message)+90) / 2,
                BOARD_WIDTH / 2);

        if (aliensRemaining == 0 || !boss.isVisible()) {
            ImageIcon alienWin = new ImageIcon(ClassLoader.getSystemResource("Alien win.png"));
            g.drawImage(alienWin.getImage(), 190, 375, 100, 100, this);
            music.stopMusic();
        } else {
            ImageIcon alienLose = new ImageIcon(ClassLoader.getSystemResource("Alien lose.png"));
            g.drawImage(alienLose.getImage(), 200, 375, 100, 100, this);
            music.stopMusic();
        }
        Font scoreOver = new Font("Helvetica", Font.BOLD, 20);

        g.setColor(Color.white);
        g.setFont(scoreOver);
        g.drawString("Score " + score, 330, 460);

        resetGame();

    }

    public void resetGame(){
        gameOverButton = new JButton("PLAY AGAIN");
        gameOverButton.setBackground(Color.lightGray);
        gameOverButton.setForeground(Color.white);
        gameOverButton.setFont(new Fonts("ethnocentric rg").getFont());
        gameOverButton.addActionListener(gameOverHandler);

        gameOverButton.setVisible(true);

        gameOverPanel = new JPanel();
        //gameOverPanel.setPreferredSize(new Dimension(200,200));
        gameOverPanel.setBackground(Color.black);
        gameOverPanel.setVisible(true);
        gameOverPanel.add(gameOverButton);

        this.add(gameOverPanel,BorderLayout.NORTH);

        this.requestFocus();
        this.validate();
    }

    public void animationCycle() {

        if (deaths == 24) {
            boss.setVisible(true);
            int x = boss.getX();
            if (x + 180 >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
            }
            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;
            }
            boss.act(direction);
        }

        if (bossCounter == 1 || bossCounter == 3 || bossCounter == 5) {
            ImageIcon bossDmg = new ImageIcon(ClassLoader.getSystemResource("bossDamage.png"));
            boss.setImage(bossDmg.getImage());
        } else {
            ImageIcon bossNoDmg = new ImageIcon(ClassLoader.getSystemResource("bossImg.png"));
            boss.setImage(bossNoDmg.getImage());
        }

        // if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
        if (bossCounter == 5) {
            inGame = false;
            message = "Game won!";
            new Sound(ClassLoader.getSystemResource("playerWin.wav"));
        }

        // player
        player.act();

        // shot
        if (shot.isVisible()) {
            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien : aliens) {
                int alienX = alien.getX();
                int alienY = alien.getY();
                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + ALIEN_HEIGHT)) {
                        ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("explosion.png"));
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        new Sound(ClassLoader.getSystemResource("alienExplosion.wav"));
                        deaths++;
                        score += 100;
                        aliensRemaining--;
                        shot.die();
                    }
                }
            }
            int bossX = boss.getX();
            int bossY = boss.getY();
            if (boss.isVisible() && shot.isVisible()) {
                if (shotX >= (bossX)
                        && shotX <= (bossX + BOSS_WIDTH)
                        && shotY >= (bossY)
                        && shotY <= (bossY + BOSS_WIDTH)) {
                    bossCounter++;
                    new Sound(ClassLoader.getSystemResource("alienExplosion.wav"));
                    shot.die();
                }
            }

            int y = shot.getY();
            y -= 4;
            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens
        for (Alien alien : aliens) {
            int x = alien.getX();
            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
                Iterator<Alien> i1 = aliens.iterator();

                while (i1.hasNext()) {
                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;
                Iterator<Alien> i2 = aliens.iterator();
                while (i2.hasNext()) {
                    Alien a = (Alien) i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }

        Iterator<Alien> it = aliens.iterator();
        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            if (alien.isVisible()) {
                int y = alien.getY();
                if (y > GROUND - ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }
                alien.act(direction);
            }
        }

        // bombs
        Random generator = new Random();
        for (Alien alien : aliens) {
            int shot = generator.nextInt(15);
            Bomb b = alien.getBomb();

            if (shot == CHANCE && alien.isVisible() && b.isDestroyed()) {
                b.setDestroyed(false);
                b.setX(alien.getX());
                b.setY(alien.getY());
                new Sound(ClassLoader.getSystemResource("bomb1.wav"));
            }

            if (shot == CHANCE && boss.isVisible() && b.isDestroyed()) {
                b.setDestroyed(false);
                b.setX(boss.getX() + BOSS_WIDTH / 2);
                b.setY(boss.getY() + BOSS_HEIGHT);
                new Sound(ClassLoader.getSystemResource("bomb1.wav"));
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {
                if (bombX >= (playerX)
                        && bombX <= (playerX + PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + PLAYER_HEIGHT)) {
                    ImageIcon ii = new ImageIcon(ClassLoader.getSystemResource("explosion.png"));
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    b.setDestroyed(true);
                    new Sound(ClassLoader.getSystemResource("playerExplosion.wav"));
                }
            }

            if (!b.isDestroyed()) {
                b.setY(b.getY() + 1);

                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (inGame) {
            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
        }
        gameOver();
    }


    public class KeyBoardControl extends AbstractAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String key;

        public KeyBoardControl(String key) {
            this.key = key;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            player.keyPressed(key);
            int x = player.getX();
            int y = player.getY();

            if(key.equals("SPACE")){
                if(!shot.isVisible()){
                    shot = new Shot(x + 32, y);
                    new Sound(ClassLoader.getSystemResource("pew.wav"));
                }
            }
        }
    }
}
