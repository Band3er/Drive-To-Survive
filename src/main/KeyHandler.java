package main;

import object.ObjectManager;
import tile.TileManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, lPressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if(gp.gameState == gp.titleState){
            if(gp.ui.titleScreenState == 0){
                if(code == KeyEvent.VK_W){
                    --gp.ui.commandNum;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 2;
                    }
                }
                if(code == KeyEvent.VK_S){
                    ++gp.ui.commandNum;
                    if(gp.ui.commandNum > 2){
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNum == 0){
                        gp.gameState = gp.playState;
                        gp.tileM.setLoadMap(0);
                        gp.obj.setLoadMap(0);
                    }
                    if(gp.ui.commandNum == 1){
                        // TODO : add the new Game implementation
                    }
                    if(gp.ui.commandNum == 2){
                        System.exit(0);
                    }
                }
            }else if(gp.ui.titleScreenState == 1){
                if(code == KeyEvent.VK_W){
                    --gp.ui.commandNum;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 3;
                    }
                }
                if(code == KeyEvent.VK_S){
                    ++gp.ui.commandNum;
                    if(gp.ui.commandNum > 3){
                        gp.ui.commandNum = 0;
                    }
                }
                if(code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNum == 0){
                        gp.gameState = gp.playState;
                        gp.tileM.setLoadMap(0);
                        gp.obj.setLoadMap(0);
                        //gp.playMusic(0);
                    }
                    if(gp.ui.commandNum == 1){
                        gp.gameState = gp.playState;
                        //gp.playMusic(0);
                    }
                    if(gp.ui.commandNum == 2){
                        gp.gameState = gp.playState;
                        //gp.playMusic(0);
                    }
                    if(gp.ui.commandNum == 3){
                        gp.ui.titleScreenState = 0;
                    }
                }
            }
        }

        // PLAY STATE
        if(gp.gameState == gp.playState){
            if(code == KeyEvent.VK_W){
                upPressed = true;
            }
            if(code == KeyEvent.VK_S){
                downPressed = true;
            }
            if(code == KeyEvent.VK_A){
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D){
                rightPressed = true;
            }
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.pauseState;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
            if(code == KeyEvent.VK_L){
                lPressed = true;
                ++gp.currentMap;
                gp.player.setValues();
                gp.npc.setValues();
                gp.tileM.update(gp.keyH);
                gp.obj.update(gp.keyH);
                try {
                    gp.ui.AddLifeDB(gp.player.life);
                    gp.player.AddSpeedDB(gp.player.speed);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(code == KeyEvent.VK_P){
                --gp.currentMap;
                gp.player.setValues();
                gp.npc.setValues();
                gp.tileM.update(gp.keyH);
                gp.obj.update(gp.keyH);
            }
        }
        // PAUSE STATE
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
        }

        // DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }

    }
}
