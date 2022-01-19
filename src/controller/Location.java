package controller;

import java.util.Locale;
import java.util.ResourceBundle;

public class Location {
    private static Locale locale;
    private static ResourceBundle bundle;
    public static void setLocaleAndBundle(){
        locale = Locale.getDefault();
        bundle = ResourceBundle.getBundle("Resource", locale);
    }
}
