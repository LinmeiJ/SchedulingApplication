package controller;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {

        private static FileHandler handler = null;
        /**
         * initializes the logger file
         */
        public static void init(){
            try {
                handler = new FileHandler("login_activity.txt", 1024 * 1024, 10, true);
            } catch (SecurityException | IOException e) {
                e.printStackTrace();
            }
            Logger logger = Logger.getLogger("");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.setLevel(Level.INFO);
        }
}
