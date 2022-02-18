package com.cameltest;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FileNameProcessor implements Processor {

    public static int row_count;

    @Override
    public void process(Exchange exchange) throws Exception {
        row_count++;
        String headers = exchange.getIn().getHeader("CamelFileName").toString();
        final String file_extension = row_count + "_converted_" +
                headers.split("\\.")[0] + ".json";
        exchange.setProperty("fileName", file_extension);
        System.out.println("Колличество обработанных строк: " + row_count);
    }

}
