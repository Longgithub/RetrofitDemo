package com.braval.retrofitdemo.net;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Https 认证类
 * Created by zhanglong on 2016/9/27.
 */

public class HttpsCertificates {
    /**
     * Generate the wrapped trust manager
     *
     * @param trustManagers The input trust manager
     * @return The wrapped trust manager
     */
    private static TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];

        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
                        originalTrustManager.checkClientTrusted(x509Certificates, authType);
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
                        originalTrustManager.checkServerTrusted(x509Certificates, authType);
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }
                }
        };
    }

    /**
     * Get the certificate
     *
     * @param stream   The input stream for the keystore certificate
     * @param password The password for the certificate
     * @return The generated ssl socket factory
     */
    public static SSLSocketFactory getCertificates(InputStream stream, String password) {

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//            Certificate certificate = certificateFactory.generateCertificate(stream);
            KeyStore keyStore = KeyStore.getInstance("BKS");
            keyStore.load(stream, password.toCharArray());
//            keyStore.load(null, null);
//            keyStore.setCertificateEntry("ca", certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(trustManagerFactory.getTrustManagers());

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, wrappedTrustManagers, null);

            return sslContext.getSocketFactory();

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
