import java.util.regex.Pattern;

/**
 * Created by admin on 2016/6/25.
 */
public class Tool {
    private final static String LETTER_REG = "[A-Z]+(_\\{\\d+\\})?";

    public static boolean isLetter(String str){
        return Pattern.matches(LETTER_REG, str);
    }
}
