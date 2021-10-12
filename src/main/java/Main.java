import io.micrometer.cloudwatch2.CloudWatchConfig;
import io.micrometer.cloudwatch2.CloudWatchMeterRegistry;
import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
//import io.micrometer.jmx.JmxConfig;
//import io.micrometer.jmx.JmxMeterRegistry;
import software.amazon.awssdk.services.cloudwatch.CloudWatchAsyncClient;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String args[]) throws InterruptedException {
//        CompositeMeterRegistry compositeRegistry = new CompositeMeterRegistry();
//        MeterRegistry meterRegistry = new SimpleMeterRegistry();
//        MeterRegistry registry = new JmxMeterRegistry(new JmxConfig() {
//            @Override
//            public String get(String s) {
//                return null;
//            }
//        }, Clock.SYSTEM);
//
//        new ClassLoaderMetrics().bindTo(meterRegistry);
//        new JvmMemoryMetrics().bindTo(meterRegistry);
//        new JvmGcMetrics().bindTo(meterRegistry);
//        new ProcessorMetrics().bindTo(meterRegistry);
//        new JvmThreadMetrics().bindTo(meterRegistry);
//        compositeRegistry.add(meterRegistry);
//        compositeRegistry.add(registry);
//        Timer timer = meterRegistry.timer("app.event", "type","ping");
//        timer.record(System.currentTimeMillis() - new Date().getTime(), TimeUnit.MILLISECONDS);
//
//
//        while(true){
//            Thread.sleep(1000);
//        }


//        MeterRegistry registry = new JmxMeterRegistry(new JmxConfig() {
//            @Override
//            public Duration step() {
//                return Duration.ofSeconds(1);
//            }
//
//            @Override
//            public String get(String s) {
//                return null;
//            }
//        }, Clock.SYSTEM);

//        new ClassLoaderMetrics().bindTo(registry);
//        new JvmMemoryMetrics().bindTo(registry);
//        new JvmGcMetrics().bindTo(registry);
//        new ProcessorMetrics().bindTo(registry);
//        new JvmThreadMetrics().bindTo(registry);
//
//
        CloudWatchConfig cloudWatchConfig = new CloudWatchConfig() {
            @Override
            public String get(String s) {
                return null;
            }

            @Override
            public String namespace() {
                return "shutterfly";
            }
        };
        MeterRegistry meterRegistry = new CloudWatchMeterRegistry(cloudWatchConfig, Clock.SYSTEM, CloudWatchAsyncClient.create());
        new JvmMemoryMetrics().bindTo(meterRegistry);
        new JvmGcMetrics().bindTo(meterRegistry);
        new ProcessorMetrics().bindTo(meterRegistry);
        new JvmThreadMetrics().bindTo(meterRegistry);

//
//        Counter counter = Counter
//                .builder("my.counter")
//                .description("counts something important")
//                .tag("environment", "test")
//                .tag("region", "us-east")
//                .register(registry);
//
//        counter.increment();
//        counter.increment(2.5);
//
//        Timer timer = Timer.builder("my.timer").register(registry);
//
//        timer.record(() -> {
//            System.out.println("sleeping");
//            try {
//                Thread.sleep(550);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//
//        timer.record(Duration.ofMillis(3));

        // Wait some time before application exit
        // This gives some time to use JConsole to connect to the
        // application and inspect the metrics
        System.out.println("Keeping application alive");
        Thread.sleep(240000);
        System.out.println("done");
    }
}
