// Just copy-paste this code to wherever, and call
// destroyAllSSLSecurityForTheEntireVMForever method to freely enjoy the wild
// world of invlaid SSL certificates.

object SecurityBypasser {
  import java.security.cert.X509Certificate
  import javax.net.ssl._


  // The all-permisive trust manager.
  object AllTM extends X509TrustManager {
    def getAcceptedIssuers(): Array[X509Certificate] = null
    def checkClientTrusted(certs: Array[X509Certificate], authType: String) {}
    def checkServerTrusted(certs: Array[X509Certificate], authType: String) {}
  }


  // The all-permissive hostname verifier.
  object AllHosts extends HostnameVerifier {
    def verify(urlHostName: String, session: SSLSession) = true
  }


  def destroyAllSSLSecurityForTheEntireVMForever() {
    val trustAllCerts = Array[TrustManager](AllTM)
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, null);
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory())
    HttpsURLConnection.setDefaultHostnameVerifier(AllHosts);
  }
}
