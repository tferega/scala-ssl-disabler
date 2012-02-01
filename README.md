# Scala SSL Disabler
A quick and dirty fix for problems with Java's SSL certificate verification
system. Works by disabling all SSL security for the entire VM running instance.


## Background
SSL verification problems are most commonly caused by trying to connect to a
server with an invalid certificate via HTTPS, or by not having the certificate
in question in your trust-store.
They are usually associated with the following exceptions:

    javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
    sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
    sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target

**OR**

    java.io.IOException: HTTPS hostname wrong


## Solution
I greatly recommend solving the underlying problem before using this _solution_
(as it is not so much of a solution as it is a nuclear option that will lay
waste to all that lies before it).

Failing that, you should try bypassing the security on a per-connection basis.
Create your own `SSLSocketFactory` and `HostnameVerifier` which contain
exceptions for your specific situation, and use them when opening the connection
(or pass them to the library you are using).

Only if there is no way to solve the underlying problem, and no way to specify
your own factory and verifier, should this tool be employed.
Also if you are just testing and debugging, and don't want to fiddle with all
that.


## Usage
Just put the `SecurityBypasser` object anywhere in your project, and call the
the `destroyAllSSLSecurityForTheEntireVMForever` method on startup, or before
establishing the first connection (there no benefit to calling it multiple
times).


## License
[Unlicensend](http://unlicense.org/)