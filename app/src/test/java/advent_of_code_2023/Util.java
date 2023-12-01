package advent_of_code_2023;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Util {

    public static Util INSTANCE = new Util();

    public String[] readFile(String filename) {
        try {
            return new String(getClass().getClassLoader().getResourceAsStream(filename).readAllBytes(), StandardCharsets.UTF_8).split("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
