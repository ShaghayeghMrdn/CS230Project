package AutoDaikon;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    private static Mock mut = new Mock();

    public static void main(String[] args) {
        String line = "";
        try {
            String path = args[0];
            System.out.println(path);
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                if(!line.equals("")){
                    mut.mock_0(line);
                }
            }
            reader.close();
        } catch (IOException ex) {
           ex.printStackTrace();
        }  
    }
}