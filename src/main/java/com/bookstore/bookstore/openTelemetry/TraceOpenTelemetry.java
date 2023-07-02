package com.bookstore.bookstore.openTelemetry;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.beans.factory.annotation.Value;

public class TraceOpenTelemetry {

    @Value("otel.traces.api.version")
    private String tracesApiVersion;

    private Tracer tracer;

    public TraceOpenTelemetry(String tracerName) {
        this.tracer = GlobalOpenTelemetry.getTracer(tracerName,tracesApiVersion);
    }


    public Span startSpan(String spanName){
        return tracer.spanBuilder(spanName).startSpan();
    }

    public void endSpan(Span span){
        span.end();
    }


}
