package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile; // tile array
    int mapTileNum[][]; // array

    public TileManager(GamePanel gp){
        this.gp = gp;

        tile = new Tile[10]; // 10 tiles
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap("/maps/map01.txt");
    }
    public void getTileImage() {
        try{
            tile[0] = new Tile(); //grass
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/002.png"));

            tile[1] = new Tile(); //wall
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/032.png"));

            tile[2] = new Tile(); //water
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/019.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxScreenCol && row < gp.maxScreenRow){
                // read this text file map01.txt and maxCol, maxRow is the limit
                String line = br.readLine(); // mit its gonna read the single line and put it into the String line

                while(col < gp.maxScreenCol){
                    String numbers[] = line.split(" "); // Splits this string around mathces at a space

                    int num = Integer.parseInt(numbers[col]); // use col as an index for numbers

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e){

        }
    }

    public void draw(Graphics2D g2){
        // draw a tile for testing: 5 tiles horizontally and 5 tiles vertically

        // efficient: while loop
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }

    }
}
