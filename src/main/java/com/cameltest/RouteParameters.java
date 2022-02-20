package com.cameltest;


import org.springframework.lang.Nullable;

public class RouteParameters {
    private final StringBuilder from;
    private final StringBuilder to;

    public RouteParameters(String from, @Nullable String to, boolean noop, @Nullable String queue_name,
                           @Nullable String destination_name) {
        this.from = new StringBuilder(from);
        if(to!=null) {
            this.to = new StringBuilder(to);
            if(queue_name != null) {
                this.to.append(":")
                        .append(queue_name)
                        .append("?destinationName=")
                        .append(destination_name);
            }
        }
        else
            this.to = null;
        if(noop)
            this.from.append("?noop=true");

    }

    public String getFrom() {
        return from.toString();
    }

    public String getTo() {
        return to.toString();
    }
}
