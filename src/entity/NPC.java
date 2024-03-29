package entity;

import main.GamePanel;


public class NPC extends Entity {
    int i = 0;
    public NPC(GamePanel gp){
        super(gp);

        direction = "up";
        speed = 14;

        getImage();
        setDialogue();
        setValues();
    }
   public void update(){
        // at every update, cheks the collisions and the position on the map
       collisionOn = false;
       gp.cChecker.checkTile(this);
       gp.cChecker.checkObject(this);
       gp.cChecker.checkPlayer(this);

       // IF COLLISION FALSE, IT CAN MOVE
       if(collisionOn == false){
           switch (direction){
               case "up": worldY -= speed;break;
               case "down":worldY += speed;break;
               case "left":worldX -= speed;break;
               case "right":worldX += speed;break;
           }
       }
        if(gp.tileM.mapTileNum[gp.currentMap][worldX/gp.tileSize][worldY/gp.tileSize] == 12){
            direction = "left";
        } else if(gp.tileM.mapTileNum[gp.currentMap][worldX/gp.tileSize][worldY/gp.tileSize] == 13){
            direction = "right";
        } else if(gp.tileM.mapTileNum[gp.currentMap][worldX/gp.tileSize][worldY/gp.tileSize] == 14){
            direction = "up";
        } else if(gp.tileM.mapTileNum[gp.currentMap][worldX/gp.tileSize][worldY/gp.tileSize] == 15){
            direction = "down";
        }

       ++i;
       if(i == 60){
           System.out.println("matrix: " + gp.obj.mapObjectNum[gp.currentMap][worldX/gp.tileSize+2][worldY/gp.tileSize]);
           i = 0;
       }
    }

    public void getImage(){
        // upload the images of the car
        up = setup("/npc/car-top");
        down = setup("/npc/car-down");
        left = setup("/npc/car-left");
        right = setup("/npc/car-right");
    }
    public void setDialogue(){
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "So you come to \nthis island to finish the races?";
        dialogues[2] = "I can help you \nbut it will be hard.";
        dialogues[3] = "I wish you \ngood luck!";
    }
    public void speak(){
        super.speak();
    }


    public void setValues(){
        // set the position values for every map

        if(gp.currentMap == 1){
            worldX = gp.tileSize * 16; // player position on the World Map
            worldY = gp.tileSize * 93;

            speed = 14;
            direction = "up";

        } else if(gp.currentMap == 2){
            worldX = gp.tileSize * 14; // player position on the World Map
            worldY = gp.tileSize * 93;

            speed = 14;
            direction = "up";

        } else if(gp.currentMap == 3){
            worldX = gp.tileSize * 16; // player position on the World Map
            worldY = gp.tileSize * 93;

            speed = 14;
            direction = "up";

        }
    }
}
