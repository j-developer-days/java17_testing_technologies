package com.jdev;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Common {

    public static final String PATH_TO_RESOURCES_FOLDER = "src/test/resources/";

    public static String createPath(String filename) {
        return Common.PATH_TO_RESOURCES_FOLDER + filename;
    }

    public static Path getPath(String filename) {
        return Paths.get(createPath(filename));
    }

}
