package com.cameltest;


import org.springframework.lang.Nullable;

public class RouteParameters {
    private final StringBuilder from;
    private final StringBuilder to;

    public RouteParameters(String from, String to, @Nullable boolean noop, String queue_name, String destination_name) {
        this.from = new StringBuilder(from);
        this.to = new StringBuilder(to);
        if(noop)
            this.from.append("?noop=true");
        this.to.append(":")
                .append(queue_name)
                .append("?destinationName=")
                .append(destination_name);
    }

    public String getFrom() {
        return from.toString();
    }

    public String getTo() {
        return to.toString();
    }
}
