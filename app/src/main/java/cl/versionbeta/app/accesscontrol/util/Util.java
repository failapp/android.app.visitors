package cl.versionbeta.app.accesscontrol.util;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Util {

    public static final SimpleDateFormat formatDateUI = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat formatDateTimeService = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat formatDateService = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static final SimpleDateFormat formatDateMonthYear = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    public static final SimpleDateFormat formatDateDay = new SimpleDateFormat("EEEE", Locale.getDefault());

    public static final DecimalFormat decimalFormat = new DecimalFormat("##,###,###");

    public static final String REGEX_WORDS = "^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]+$";
    public static final String REGEX_EMAIL ="^[a-zA-Z0-9\\._-]+@[a-zA-Z0-9-]{2,}[.][a-zA-Z]{2,4}$";
    public static final String REGEX_VISA = "^4[0-9]{3}-?[0-9]{4}-?[0-9]{4}-?[0-9]{4}$";

    public static final String REGEX_DNI = "^(\\d{1}|\\d{2})\\.(\\d{3}\\.\\d{3}-)([kK]{1}$|\\d{1}$)";

    public static final String REGEX_DNI2 = "^(\\d{1,8}-)([kK]{1}$|\\d{1}$)";


}
