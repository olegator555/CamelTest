package com.cameltest;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spi.DataFormat;


public class QueueRoute extends RouteBuilder {
    DataFormat bindy = new BindyCsvDataFormat(Model.class);
    @Override
    public void configure() throws Exception {
        from("file://csv_files?noop=true")
                .routeId("CsvFileRoute")
                .split(body().tokenize("\n")).streaming()
                .unmarshal(bindy)
                .marshal()
                .json(JsonLibrary.Gson)
                .process(new FileNameProcessor())
                .log("Unmarshalled model: ${body}")
                .to("activeMQ:queue:Json_converted?destinationName=${header.CamelFileName}");
    }

}
