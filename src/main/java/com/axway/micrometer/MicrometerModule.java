package com.axway.micrometer;

import com.vordel.config.ConfigContext;
import com.vordel.config.LoadableModule;
import com.vordel.es.Entity;
import com.vordel.es.EntityStoreException;
import com.vordel.trace.Trace;
import io.micrometer.cloudwatch2.CloudWatchConfig;
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;

import java.time.Duration;

public class MicrometerModule implements  LoadableModule {
    @Override
    public void load(LoadableModule loadableModule, String s) throws FatalException {

    }

    @Override
    public void unload() {

    }

    @Override
    public void configure(ConfigContext configContext, Entity entity) throws EntityStoreException, FatalException {
        Trace.info("Loading micrometer");
        String namespace = entity.getStringValue("namespace");
        int frequency = entity.getIntegerValue("frequency");
        CloudWatchConfig cloudWatchConfig = new CloudWatchConfig() {
            @Override
            public String get(String s) {
                return null;
            }
            @Override
            public String namespace() {
                return namespace;
            }
            public Duration step() {
                return Duration.ofMinutes(frequency);
            }

        };

       // export AWS_PROFILE="myProfile" for custom aws credential profile - ref - https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html


        SdkAsyncHttpClient httpClient = NettyNioAsyncHttpClient.builder()
                .connectionTimeout(Duration.ofSeconds(30))
                .readTimeout(Duration.ofSeconds(30))
                .build();

        MeterRegistry registry = new CloudWatchMeterRegistry(cloudWatchConfig, Clock.SYSTEM, CloudWatchAsyncClient.builder().httpClient(httpClient).build());
        new JvmMemoryMetrics().bindTo(registry);
        new JvmGcMetrics().bindTo(registry);
        new ProcessorMetrics().bindTo(registry);
        new JvmThreadMetrics().bindTo(registry);
        Trace.info("started micrometer");
    }
}
