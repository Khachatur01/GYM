package com.fitness.DataSource.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static File file;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

    public static void initLogger() {
//        Log.file = new File(
//                System.getProperty("user.home") +
//                System.getProperty("file.separator") +
//                "gym_log"
//        );
//        try {
//            Log.file.createNewFile();
//            System.setErr(new PrintStream(
//                new FileOutputStream(
//                    Log.file,
//                    true)
//                )
//            );
//        } catch (IOException ignored) {
//
//        }
    }

    public static void info(String text) {
        System.err.println(dateFormatter.format(new Date()) + " | INFO     | " +  text);
    }
    public static void warning(String text) {
        System.err.println(dateFormatter.format(new Date()) + " | WARNING  | " +  text);
    }
    public static void error(String text) {
        System.err.println(dateFormatter.format(new Date()) + " | ERROR    | " +  text);
    }
}
