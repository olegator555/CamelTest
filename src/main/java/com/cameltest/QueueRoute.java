package com.cameltest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;


public class QueueRoute extends RouteBuilder {
    private RouteParameters routeParameters;

    public QueueRoute(RouteParameters routeParameters) {
        this.routeParameters = routeParameters;
    }

    DataFormat bindy = new BindyCsvDataFormat(Model.class);

    public QueueRoute() {
    }

    @Override
    public void configure() {
        from(routeParameters.getFrom())
                .threads(20)
                .routeId("CsvFileRoute")
                .split(body().tokenize("\n")).streaming()
                .unmarshal(bindy)
                .marshal()
                .json(JsonLibrary.Gson)
                .process(new FileNameProcessor())
                .log("Unmarshalled model: ${body}")
                .to(routeParameters.getTo());
    }

}
