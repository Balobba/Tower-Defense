package towerdefense.graphics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * This class reads a level text document and creates a map from it (to a double array).
 */
class LevelFile {

    private static final int LEVEL_MAP_SIZE_X = 22;
    private static final int LEVEL_MAP_SIZE_Y = 14;
    private FileInputStream file = null;
    private InputStreamReader reader = null;
    private Scanner scanner = null;

    private final Level level = new Level();

    public Level getLevel(String filename){

        try{

            file = new FileInputStream("level/" +filename);
            reader = new InputStreamReader(file);

            scanner = new Scanner(reader);

            level.setMap(new int[LEVEL_MAP_SIZE_X][LEVEL_MAP_SIZE_Y]);

            int x = 0;
            int y = 0;

            while(scanner.hasNext()){

                level.getMap()[x][y] = scanner.nextInt();

                if(x<LEVEL_MAP_SIZE_X -1){
                    x++;
                }else{
                    y++;
                    x = 0;
                }
            }

            return level;

        }catch(FileNotFoundException e){

            e.printStackTrace();

        }

        return null;

    }

}
