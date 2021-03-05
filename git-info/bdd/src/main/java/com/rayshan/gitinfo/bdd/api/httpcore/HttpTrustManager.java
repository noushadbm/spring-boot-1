package com.rayshan.gitinfo.bdd.api.httpcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpTrustManager implements TrustManager, X509TrustManager {
    private static final Logger log = LoggerFactory.getLogger(HttpTrustManager.class);

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    public static void trustAllHttpsCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[1];
            TrustManager tm = new HttpTrustManager();
            trustAllCerts[0] = tm;
            SSLContext sc = SSLContext.getInstance("TLS");
            SSLSessionContext serverSessionContext = sc.getServerSessionContext();
            serverSessionContext.setSessionTimeout(0);
            sc.init(null, trustAllCerts, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException | NoSuchAlgorithmException | IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }
}
