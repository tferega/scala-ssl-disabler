package javax.net.ssl.disabler;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class AllHosts implements HostnameVerifier {
    public boolean verify(final String hostname, final SSLSession session) {
        return true;
    }
}
