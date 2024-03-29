package main;

import entity.NPC;
import entity.Player;
import object.ObjectManager;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    static final int originalTileSize = 16; // 16x16 tile
    static final int scale = 3;
    final int roadScale = 12;
    final int buildingScale = 18;

    public static int tileSize = originalTileSize * scale; // 48x48 tile
    public final int roadTileSize = originalTileSize * roadScale; // 192x192 tile
    public final int buildingSize = originalTileSize * buildingScale; // 288x288 tile
    public final int maxScreenCol = 16; // change from here
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    public final int maxMap = 4;
    public int currentMap = 0;

    //WORLD Map Parameters - Settings
    public final int maxWorldCol = 120;
    public final int maxWorldRow = 120;
    public final int maxWorldColLevel1 = 32;
    public final int maxWorldColLevel2 = 60;
    public final int maxWorldColLevel3 = 80;
    public final int maxWorldRowLevel1 = 100;
    public final int maxWorldRowLevel2 = 100;
    public final int maxWorldRowLevel3 = 100;

    // FPS
    int FPS = 60;

    public KeyHandler keyH = new KeyHandler(this);
    public TileManager tileM = new TileManager(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    //public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public ObjectManager obj = new ObjectManager(this);
    public NPC npc = new NPC(this);

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame(){
        //playMusic(0); // TODO : The background music
        //stopMusic();
        npc.setValues();
        gameState = titleState;
    }
    public void update(){
        if(gameState == playState){
            // PLAYER
            player.update();

            // NPC
            npc.update();
        }
        if(gameState == pauseState){
            // nothing
        }
    }
    public void paintComponent(Graphics g){
        // draws every element on the screen after a logical order

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        // OTHERS
        else {
            // TILE
            tileM.draw(g2, 0);

            // OBJECT
            obj.draw(g2);

            // NPC
            npc.draw(g2);

            // PLAYER
            player.draw(g2);

            // UI
            ui.draw(g2);
            g2.dispose();
        }
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){ // SE - Sound Effects
        se.setFile(i);
        se.play();
    }
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000){
                System.out.println("FPS:" + drawCount);
                System.out.println("Current level: " + currentMap);
                System.out.println(" ");
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
}
