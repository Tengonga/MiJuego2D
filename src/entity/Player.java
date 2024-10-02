package entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Player extends Entity {
    private GamePanel gp;
    private KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = loadImage("/player/boy_up_1.png");
            up2 = loadImage("/player/boy_up_2.png");
            down1 = loadImage("/player/boy_down_1.png");
            down2 = loadImage("/player/boy_down_2.png");
            left1 = loadImage("/player/boy_left_1.png");
            left2 = loadImage("/player/boy_left_2.png");
            right1 = loadImage("/player/boy_right_1.png");
            right2 = loadImage("/player/boy_right_2.png");
        } catch (IOException e) {
            System.err.println("Error loading player images:");
            e.printStackTrace();
            // Aquí podrías lanzar una excepción personalizada o manejarla según sea necesario en tu aplicación
        }
    }

    private BufferedImage loadImage(String path) throws IOException {
        BufferedImage image;
        try (InputStream inputStream = getClass().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + path);
            }
            image = ImageIO.read(inputStream);
            if (image == null) {
                throw new IOException("Failed to load image from path: " + path);
            }
        } catch (IOException e) {
            System.err.println("Error loading image from path: " + path);
            throw e; // Lanza la excepción para que la clase que llama pueda manejarla según sea necesario
        }
        return image;
    }

    public void update() {
        if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
            if (keyH.upPressed == true) {
                direction = "up";
                y -= speed;
            } else if (keyH.downPressed == true) {
                direction = "down";
                y += speed;
            } else if (keyH.leftPressed == true) {
                direction = "left";
                x -= speed;
            } else if (keyH.rightPressed == true) {
                direction = "right";
                x += speed;
            }

            spriteCounter++;
            /* MÉTODO DE ACTUALIZACIÓN: Cada 12 frames se actualiza la imagen y crea el efecto de mover los pies*/
            if(spriteCounter > 12){
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
            default:
                // En caso de una dirección no válida, usar una asignación por defecto
                image = down1;
                break;
        }

        if (image != null) {
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        }
    }
}
