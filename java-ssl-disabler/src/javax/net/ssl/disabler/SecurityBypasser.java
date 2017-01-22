package javax.net.ssl.disabler;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.*;

public class SecurityBypasser {
    private static boolean isSet = false;
    private static SSLSocketFactory defaultSSLSocketFactory = null;
    private static HostnameVerifier defaultHostnameVerifier = null;

    public static void destroySSLSecurity() {
        try {
            defaultSSLSocketFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
            defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
            isSet = true;

            final TrustManager[] trustAllCerts = new TrustManager[]{new TrustAllManager()};
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new AllHosts());
        } catch (final NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static void restoreSSLSecurity() {
        if (isSet) {
            HttpsURLConnection.setDefaultSSLSocketFactory(defaultSSLSocketFactory);
            HttpsURLConnection.setDefaultHostnameVerifier(defaultHostnameVerifier);
        }
    }
}
