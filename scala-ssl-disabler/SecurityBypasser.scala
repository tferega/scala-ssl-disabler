// Just copy-paste this code to wherever, and call
// destroyAllSSLSecurityForTheEntireVMForever method to freely enjoy the wild
// world of invlaid SSL certificates.
object SecurityBypasser {
  import java.security.cert.X509Certificate
  import javax.net.ssl._

  private[this] case class Defaults(sslSocketFactory: SSLSocketFactory, hostnameVerifier: HostnameVerifier)
  private[this] var defaults: Option[Defaults] = None

  // The all-permisive trust manager.
  private[this] object AllTM extends X509TrustManager {
    def getAcceptedIssuers: Array[X509Certificate] = null
    def checkClientTrusted(certs: Array[X509Certificate], authType: String) {}
    def checkServerTrusted(certs: Array[X509Certificate], authType: String) {}
  }

  // The all-permissive hostname verifier.
  private[this] object AllHosts extends HostnameVerifier {
    def verify(urlHostName: String, session: SSLSession) = true
  }

  def destroySSLSecurity(): Unit = {
    // Save the current defaults so that they can later be restored.
    defaults = Some(Defaults(HttpsURLConnection.getDefaultSSLSocketFactory, HttpsURLConnection.getDefaultHostnameVerifier))

    // Create the new, permissive SSL context.
    val trustAllCerts = Array[TrustManager](AllTM)
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, null)

    // Set the connection.
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory)
    HttpsURLConnection.setDefaultHostnameVerifier(AllHosts)
  }

  def restoreSllSecurity(): Unit = {
    // If defaults is set, restore the originals.
    defaults.foreach { d =>
      HttpsURLConnection.setDefaultSSLSocketFactory(d.sslSocketFactory)
      HttpsURLConnection.setDefaultHostnameVerifier(d.hostnameVerifier)
    }
  }

  // Executes a single function in a bypassed environment.
  def executeBypassed[T](f: => T): T = {
    destroySSLSecurity()
    var r = f
    restoreSllSecurity()
    r
  }
}
