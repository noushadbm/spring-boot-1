package com.rayshan.gitinfo.clients.config;

import com.google.common.flogger.FluentLogger;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Data
public class HttpConfig<T> {
    private String baseUrl;
    private String endpointPath;
    private long readTimeout;
    private long writeTimeout;
    private long connectTimeout;
    private long callTimeout;
    private Integer poolSize;

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public <T> T getHttpService(Class<T> clazz) {
        return buildRetrofit().create(clazz);
    }

    private Retrofit buildRetrofit() {
        ConnectionPool pool = new ConnectionPool(this.getPoolSize(), 5, TimeUnit.MINUTES);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        OkHttpClient client =
                clientBuilder
                        .callTimeout(this.getCallTimeout(), TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .connectionPool(pool)
                        .build();

        return new Retrofit.Builder()
                .baseUrl(this.getBaseUrl())
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
