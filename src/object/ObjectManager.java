package object;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ObjectManager {
    GamePanel gp;
    public Object[] object;
    public int[][][] mapObjectNum;
    public ObjectManager(GamePanel gp){
        this.gp = gp;
        object = new Object[25];
        mapObjectNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getObjectImage();
    }
    public void update(KeyHandler keyH){
        if(keyH.lPressed == true){
            setLoadMap(gp.currentMap);
        }
    }
    public void setLoadMap(int map){
        // with the specific input map, change the file where the matrix loads

        switch (map){
            case 0:
                loadMap("/maps/objectMap.txt", 0);
                break;
            case 1:
                loadMap("/maps/Level1-obj.txt", 1);
                break;
            case 2:
                loadMap("/maps/Level2-obj.txt", 2);
                break;
            case 3:
                loadMap("/maps/Level3-obj.txt", 3);
                break;
        }
    }
    public void getObjectImage(){
        setup(0, "road-bottom-left-up", false);
        setup(1, "road-bottom-left-up", false);
        setup(2, "road-bottom-up-right", false);
        setup(3, "road-horizontal", false);
        setup(4, "road-top-down-right", false);
        setup(5, "road-top-left-down", false);
        setup(6, "road-top-left-down-right", false);
        setup(7, "road-vertical", false);
        setup(8, "road-vertical-down-left-top", false);
        setup(9, "intersection", false);
        setup(10, "road-bottom-left-up-right", false);
        setup(11, "road-vertical-top-right-down", false);
        setup(12, "hotel", true);
        setup(13, "house-green", true);
        setup(14, "house-red", true);
        setup(15, "pub", true);
        setup(16, "tavern", true);
        setup(17, "tree", true);
        setup(18, "road-blocked", false);
        setup(19, "sign", false);
        setup(20, "start-finish", false);
        setup(21, "start-finish", true);
        setup(22, "pothole", false);
        setup(23, "turbo", false);
        setup(24, "mission", false);
    }
    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            object[index] = new Object();
            object[index].image = ImageIO.read(getClass().getResourceAsStream("/objects/" + imageName + ".png"));
            if(index >= 12 && index <= 16){
                object[index].image = uTool.scaleImage(object[index].image, gp.buildingSize, gp.buildingSize);
                object[index].collision = collision;
            } else if(index == 17 || index == 18 || index == 19 || index == 20 || index == 21 || index == 22 || index == 23 || index == 24){
                object[index].image = uTool.scaleImage(object[index].image, gp.tileSize, gp.tileSize);
                object[index].collision = collision;
            } else {
                object[index].image = uTool.scaleImage(object[index].image, gp.roadTileSize, gp.roadTileSize);
                object[index].collision = collision;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath, int map){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            switch(map){
                case 0:
                    while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                        String line = br.readLine();
                        while(col < gp.maxWorldCol){
                            String numbers[] = line.split(" ");
                            int num = Integer.parseInt(numbers[col]);
                            mapObjectNum[map][col][row] = num;
                            col++;
                        }
                        if(col == gp.maxWorldCol){
                            col = 0;
                            row++;
                        }
                    }
                    br.close();
                    break;
                case 1:
                    while(col < gp.maxWorldColLevel1 && row < gp.maxWorldRowLevel1){
                        String line = br.readLine();
                        while(col < gp.maxWorldColLevel1){
                            String numbers[] = line.split(" ");
                            int num = Integer.parseInt(numbers[col]);
                            mapObjectNum[map][col][row] = num;
                            col++;
                        }
                        if(col == gp.maxWorldColLevel1){
                            col = 0;
                            row++;
                        }
                    }
                    br.close();
                    break;
                case 2:
                    while(col < gp.maxWorldColLevel2 && row < gp.maxWorldRowLevel2){
                        String line = br.readLine();
                        while(col < gp.maxWorldColLevel2){
                            String numbers[] = line.split(" ");
                            int num = Integer.parseInt(numbers[col]);
                            mapObjectNum[map][col][row] = num;
                            col++;
                        }
                        if(col == gp.maxWorldColLevel2){
                            col = 0;
                            row++;
                        }
                    }
                    br.close();
                    break;
                case 3:
                    while(col < gp.maxWorldColLevel3 && row < gp.maxWorldRowLevel3){
                        String line = br.readLine();
                        while(col < gp.maxWorldColLevel3){
                            String numbers[] = line.split(" ");
                            int num = Integer.parseInt(numbers[col]);
                            mapObjectNum[map][col][row] = num;
                            col++;
                        }
                        if(col == gp.maxWorldColLevel3){
                            col = 0;
                            row++;
                        }
                    }
                    br.close();
                    break;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int objectNum = mapObjectNum[gp.currentMap][worldCol][worldRow];
            int worldX = worldCol * gp.tileSize; // is the position on the map
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX; // is where on the screen we draw it
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // La if schimbam ca sa se deseneza strada mai bine, gen sa nu dispara de pe ecran cand ne indepartam de ea
            if(     worldX + gp.roadTileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.roadTileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.roadTileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.roadTileSize < gp.player.worldY + gp.player.screenY &&
                    objectNum != 0 && objectNum >= 1 && objectNum <= 11){
                g2.drawImage(object[objectNum].image, screenX, screenY, null);
            } else if(worldX + gp.buildingSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.buildingSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.buildingSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.buildingSize < gp.player.worldY + gp.player.screenY &&
                    objectNum != 0 && objectNum >= 12 && objectNum <= 16) {
                g2.drawImage(object[objectNum].image, screenX, screenY, null);
            } else if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY &&
                    objectNum != 0 && objectNum >= 17 && objectNum <= 24) {

                g2.drawImage(object[objectNum].image, screenX, screenY, null);
            }
            worldCol++;
            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
