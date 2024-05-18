import java.util.Locale;

public class HowToGetLocale {
    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        System.out.println("Default Locale: " + locale.toString());
        System.out.println("Language: " + locale.getLanguage());
        System.out.println("Country: " + locale.getCountry());
        System.out.println("Display Language: " + locale.getDisplayLanguage());
        System.out.println("Display Country: " + locale.getDisplayCountry());
    }
}