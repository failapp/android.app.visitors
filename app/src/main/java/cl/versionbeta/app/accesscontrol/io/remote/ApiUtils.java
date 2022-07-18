package cl.versionbeta.app.accesscontrol.io.remote;


//import android.util.Log;

import cl.versionbeta.app.accesscontrol.ui.MainActivity;

public class ApiUtils {

    private ApiUtils() {}

    //public static final String BASE_URL = "http://192.168.1.11:8899/api/";
    //public static final String BASE_URL = "http://192.168.0.125:5005/api/";
    public static final String BASE_URL = "http://192.168.0.7:5005/api/";
    public static final String KEY_URL_WEBSERVICE = "url_webservice";
    public static final String KEY_TCP_PORT = "tcp_port";

    public static final String BASE_URL_WS = MainActivity.preferences.getString(KEY_URL_WEBSERVICE,"192.168.1.11")
                                          + ":" + MainActivity.preferences.getString(KEY_TCP_PORT,"8899") + "/api/";

    public static PersonService getPersonService() {

        return RetrofitClient.getClient(BASE_URL).create(PersonService.class);

    }

    public static AccessLogService getAccessLogService() {

        return RetrofitClient.getClient(BASE_URL).create(AccessLogService.class);

    }

    public static AreaService getAreaService() {

        return RetrofitClient.getClient(BASE_URL).create(AreaService.class);
    }

    public static ContactService getContactService() {

        return RetrofitClient.getClient(BASE_URL).create(ContactService.class);

    }

    public static StatService getStatService() {

        return RetrofitClient.getClient(BASE_URL).create(StatService.class);

    }


}
