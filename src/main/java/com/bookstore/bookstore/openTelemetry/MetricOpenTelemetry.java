package com.bookstore.bookstore.openTelemetry;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.HashMap;

import static java.lang.Runtime.getRuntime;

public class MetricOpenTelemetry {

    @Value("otel.metrics.api.version")
    private String metricsApiVersion;

    private Meter meter;

    private HashMap<String,LongCounter> staticMetrics;

    public MetricOpenTelemetry(String meterName) {
        this.meter = GlobalOpenTelemetry
                .meterBuilder(meterName)
                .setInstrumentationVersion(metricsApiVersion)
                .build();
        staticMetrics = new HashMap<>();
    }



    @PostConstruct
    public void createStaticMetrics(String name, String counter, String description, String unit){
        LongCounter value=
                    meter
                            .counterBuilder(counter)
                            .setDescription(description)
                            .setUnit(unit)
                            .build();
        this.staticMetrics.put(name,value);

    }

    public LongCounter getStaticMetrics(String name) {
        return this.staticMetrics.get(name);
    }
    public void removeStaticMetrics(String name) {
        this.staticMetrics.remove(name);
    }


    @PostConstruct
    public void createAutomaticMetrics(String name, String description, String unit){
        meter
                .gaugeBuilder(name)
                .setDescription(description)
                .setUnit("byte")
                .buildWithCallback(
                        r ->{
                            r.record(getRuntime().totalMemory() - getRuntime().freeMemory());
                        }
                );


    }

}
