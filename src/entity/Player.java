package entity;
import main.KeyHandler;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Player extends Entity {
    KeyHandler keyH;
    public int contor;
    public final int screenX; // this is where we draw the player on the map
    public final int screenY;
    public Player(GamePanel gp, KeyHandler keyH){
        // Constructor

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize/2);
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPLayerImage();
    }
    public void setValues(){
        // set the position values and the speed on every map

        if(gp.currentMap == 0){
            setDefaultValues();
        }else if(gp.currentMap == 1){
            worldX = gp.tileSize * 15; // player position on the World Map
            worldY = gp.tileSize * 93;

            speed = 14;
            direction = "up";

        } else if(gp.currentMap == 2){
            worldX = gp.tileSize * 13; // player position on the World Map
            worldY = gp.tileSize * 93;

            speed = 14;
            direction = "up";

        } else if(gp.currentMap == 3){
            worldX = gp.tileSize * 15; // player position on the World Map
            worldY = gp.tileSize * 93;

            speed = 14;
            direction = "up";

        }
    }
    public void setDefaultValues(){
        // the default position values on the main map

        contor = 0;
        worldX = gp.tileSize * 14; // player position on the World Map
        worldY = gp.tileSize * 14;

        speed = 14;
        direction = "down";

        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }
    public void getPLayerImage(){
        up = setup("/player/car-top");
        down = setup("/player/car-down");
        left = setup("/player/car-left");
        right = setup("/player/car-right");
    }

    public void update(){
        if(keyH.upPressed == true){
            direction = "up";
            if(collisionOn == false){
                worldY -= speed;
            }
        } else if(keyH.downPressed == true){
            direction = "down";
            if(collisionOn == false){
                worldY += speed;
            }
        } else if(keyH.leftPressed == true){
            direction = "left";
            if(collisionOn == false){
                worldX -= speed;
            }

        } else if(keyH.rightPressed == true){
            direction = "right";
            if(collisionOn == false){
                worldX += speed;
            }
        }

        // CHECK TILE COLLISION
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // Check Object Collision
        gp.cChecker.checkObject(this);
        // TODO : Object Collision Checker

        // CHECK NPC COLLISION
        int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
        interactNPC(npcIndex);
        /*if(gp.player.solidArea.intersects(gp.obj.solidAreaObj)){
            System.out.println("contor:" + contor);
            ++contor;
            if(contor == 5){
                gp.player.life -= 1;
                contor = 0;
            }
        }*/
    }
    public static void AddSpeedDB(int s) throws SQLException {
        // add to the database the actual speed of the player

        Connection c = null;
        Statement stmt = null;
        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DriveToSurvive.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS DriveToSurvive" + "(SPEED)";
            stmt.executeUpdate(sql);
            c.setAutoCommit(false);

            String sql1 = "INSERT INTO DriveToSurvive (SPEED)" + "VALUES (" + speed + ");";
            stmt.executeUpdate(sql1);

            stmt.close();
            c.commit();
            c.close();
        } catch(Exception e) {
            System.err.println(e.getClass().getName() + ":" + e.getMessage());
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
    public void pickUpObject(int i){

        if(i != 999){

        }

        /*if(i != 999){
            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key":
                    //gp.playSE(0); // TODO : Play sound effects, and put the right index there
                    ++hasKey; // to collect keys
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got something cool!!!");
                    break;
                case "Boots":
                    speed += 2;
                    gp.obj[i] = null;
                    new java.util.Timer().schedule(
                            new java.util.TimerTask(){
                                @Override
                                public void run(){
                                    speed -= 2;
                                }
                            },
                            3000
                    );
                    gp.ui.gameFinished = true;
                    //gp.stopMusic();
                    //gp.playSE(Sunet de final);
                    break;
            }
        }*/
    }
    public void interactNPC(int i){
        // if the collision is true and the enter is pressed, the dialogue begins
        if(i != 999){
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.dialogueState;
                gp.npc.speak();
            }
        }
        gp.keyH.enterPressed = false;
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch (direction){
            case "up":
                image = up;
                break;
            case "down":
                image = down;
                break;
            case "left":
                image = left;
                break;
            case "right":
                image = right;
                break;
        }
        g2.drawImage(image, screenX, screenY, null);
    }
}
