package com.cameltest;

public class Main {

    public static final String FROM_DIRECTORY = "file://csv_files";
    public final static String QUEUE_TYPE = "activeMQ";
    public final static String BROKER_URL = "tcp://0.0.0.0:61616";
    public final static String QUEUE_NAME = "Json_converted";
    public static final String DESTINATION_FILE_NAME = "json_converted";
    public static void main(String[] args) throws Exception {
        try {
            new ContextConfigurator(QUEUE_TYPE, BROKER_URL).configure(new QueueRoute(new RouteParameters(FROM_DIRECTORY,
                    QUEUE_TYPE, true, QUEUE_NAME, DESTINATION_FILE_NAME)));
        } catch (Exception e) {
            System.err.println("Context configuration failed");
        }
    }
}
