package javax.net.ssl.disabler;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class TrustAllManager implements X509TrustManager {
    public void checkClientTrusted(final X509Certificate[] cert, final String authType)
            throws CertificateException {
    }

    public void checkServerTrusted(final X509Certificate[] cert, final String authType)
            throws CertificateException {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}