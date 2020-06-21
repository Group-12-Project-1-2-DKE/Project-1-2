package Reader;

import com.mygdx.game.Variables;
import java.util.*;
import java.io.*;

/**
 * Object that is used to read a text file that contains the default dettings of the golf game.
 */
public class FileReaders{
    /**
     * Default constructor.
     */
    public FileReaders(){}

    /**
     * This method is used to read a specific file that contains the default values of the golf game.
     * @param fileName String that represent the name of the text file that contain the default values.
     */
    public void read (String fileName){
        // Get the path of the project on the computer
        String path = System.getProperty("user.dir");
        // Specify he path to be the full path of the file
        path = path + "\\core\\src\\Reader\\" + fileName;
        File file = new File(path);

        // If the file doesn't exist print error.
        if (!file.exists()) {
            path = System.getProperty("user.dir");
            path = path + "/core/src/Reader/" + fileName;
            file = new File(path);
        }
        if(!file.exists()){
            System.out.println("System couldn't file source file!");
            System.out.println("Application will explode");
        }

        LinkedList<Float> num = new LinkedList<>();
        try {
            FileReader fr = new FileReader(path);
            try {
                BufferedReader br = new BufferedReader(fr);

                String line = "";
                while ((line = br.readLine()) != null) {       // For each line in the text file
                    // Read the line, and convert the string to a list of numbers
                    String[] numbers = line.split(" ");

                    for (int i = 0; i < numbers.length; i++) {
                        if (numbers[i].matches(".*\\d.*")) {
                            float num1 = Float.parseFloat(numbers[i]);
                            num.add(num1);
                        } else if (numbers[i].equals("Function=")) {
                            Variables.function = numbers[++i];
                        }
                    }
                }
                // Set the default values of the game as the values on the text file.
                Variables.gravity = num.pop();
                Variables.ballMass = num.pop();
                Variables.tolerance = num.pop();
                Variables.startX = num.pop();
                Variables.startY = num.pop();
                Variables.goalX = num.pop();
                Variables.goalY = num.pop();
                Variables.shootX = num.pop();
                Variables.shootY = num.pop();

                // Exceptions.
            } finally {
                fr.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}