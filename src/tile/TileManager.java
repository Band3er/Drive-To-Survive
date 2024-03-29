package tile;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;
    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[19];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
    }
    public void update(KeyHandler keyH){
        if(keyH.lPressed == true){
            setLoadMap(gp.currentMap);
        }
    }
    public void setLoadMap(int map){
        switch (map){
            case 0:
                loadMap("/maps/tileMap.txt", 0);
                break;
            case 1:
                loadMap("/maps/Level1-tile.txt", 1);
                break;
            case 2:
                loadMap("/maps/Level2-tile.txt", 2);
                break;
            case 3:
                loadMap("/maps/Level3-tile.txt", 3);
                break;
        }
    }
    public void getTileImage(){
        setup(0, "weak-grass", false);
        setup(1, "power-grass", false);
        setup(3, "bottom-left-corner-mud", true);
        setup(4, "bottom-right-corner-mud", true);
        setup(5, "bottom-mud", true);
        setup(6, "left-mud", true);
        setup(7, "right-mud", true);
        setup(8, "top-left-corner-mud", true);
        setup(9, "top-right-corner-mud", true);
        setup(10, "top-mud", true);
        setup(11, "water", true);
        setup(12, "weak-grass", false); // left
        setup(13, "weak-grass", false); // right
        setup(14, "weak-grass", false); // top
        setup(15, "weak-grass", false); // down
    }
    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath, int map){
        try{
            switch(map){
                case 0:
                    InputStream is = getClass().getResourceAsStream(filePath);
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    int col = 0;
                    int row = 0;

                    while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                        String line = br.readLine();
                        while(col < gp.maxWorldCol){
                            String numbers[] = line.split(" ");
                            int num = Integer.parseInt(numbers[col]);
                            mapTileNum[map][col][row] = num;
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
                     is = getClass().getResourceAsStream(filePath);
                     br = new BufferedReader(new InputStreamReader(is));
                     col = 0;
                     row = 0;

                    while(col < gp.maxWorldColLevel1 && row < gp.maxWorldRowLevel1){
                        String line = br.readLine();
                        while(col < gp.maxWorldColLevel1){
                            String numbers[] = line.split(" ");
                            int num = Integer.parseInt(numbers[col]);
                            mapTileNum[map][col][row] = num;
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
                    is = getClass().getResourceAsStream(filePath);
                    br = new BufferedReader(new InputStreamReader(is));
                    col = 0;
                    row = 0;

                    while(col < gp.maxWorldColLevel2 && row < gp.maxWorldRowLevel2){
                        String line = br.readLine();
                        while(col < gp.maxWorldColLevel2){
                            String numbers[] = line.split(" ");
                            int num = Integer.parseInt(numbers[col]);
                            mapTileNum[map][col][row] = num;
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
                    is = getClass().getResourceAsStream(filePath);
                    br = new BufferedReader(new InputStreamReader(is));
                    col = 0;
                    row = 0;

                    while(col < gp.maxWorldColLevel3 && row < gp.maxWorldRowLevel3){
                        String line = br.readLine();
                        while(col < gp.maxWorldColLevel3){
                            String numbers[] = line.split(" ");
                            int num = Integer.parseInt(numbers[col]);
                            mapTileNum[map][col][row] = num;
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
    public void draw(Graphics2D g2, int map){

        switch (map){
            case 0:
                int worldCol = 0;
                int worldRow = 0;

                while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
                    int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
                    int worldX = worldCol * gp.tileSize; // is the position on the map
                    int worldY = worldRow * gp.tileSize;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX; // is where on the screen we draw it
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY && tileNum!=13){
                        g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                    }

                    worldCol++;
                    if(worldCol == gp.maxWorldCol){
                        worldCol = 0;
                        worldRow++;
                    }
                }
                break;
            case 1:
                worldCol = 0;
                worldRow = 0;

                while(worldCol < gp.maxWorldColLevel1 && worldRow < gp.maxWorldRowLevel1){
                    int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
                    int worldX = worldCol * gp.tileSize; // is the position on the map
                    int worldY = worldRow * gp.tileSize;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX; // is where on the screen we draw it
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                        g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                    }

                    worldCol++;
                    if(worldCol == gp.maxWorldColLevel1){
                        worldCol = 0;
                        worldRow++;
                    }
                }
                break;
            case 2:
                worldCol = 0;
                worldRow = 0;

                while(worldCol < gp.maxWorldColLevel2 && worldRow < gp.maxWorldRowLevel2){
                    int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
                    int worldX = worldCol * gp.tileSize; // is the position on the map
                    int worldY = worldRow * gp.tileSize;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX; // is where on the screen we draw it
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                        g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                    }

                    worldCol++;
                    if(worldCol == gp.maxWorldColLevel2){
                        worldCol = 0;
                        worldRow++;
                    }
                }
                break;
            case 3:
                worldCol = 0;
                worldRow = 0;

                while(worldCol < gp.maxWorldColLevel3 && worldRow < gp.maxWorldRowLevel3){
                    int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
                    int worldX = worldCol * gp.tileSize; // is the position on the map
                    int worldY = worldRow * gp.tileSize;
                    int screenX = worldX - gp.player.worldX + gp.player.screenX; // is where on the screen we draw it
                    int screenY = worldY - gp.player.worldY + gp.player.screenY;

                    if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&
                            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){
                        g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                    }

                    worldCol++;
                    if(worldCol == gp.maxWorldColLevel3){
                        worldCol = 0;
                        worldRow++;
                    }
                }
                break;
        }
    }
}
