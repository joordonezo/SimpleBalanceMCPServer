package com.app.simplebalancemcpserver.models;

public record Transaction(
    String id,
    String item,
    Double value
) {}
