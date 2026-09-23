package com.example;

import org.zeromq.ZMQ;

public class ZmqClient {
    public static void main(String[] args) {
        // Create ZeroMQ context
        ZMQ.Context context = ZMQ.context(1);

        // socket to send requests
        ZMQ.Socket requester = context.socket(ZMQ.REQ);
        requester.connect("tcp://localhost:5555");

        System.out.println("ZeroMQ Client started, sending request...");

        // send request to server
        String request = "Hello, Server!";
        requester.send(request.getBytes(ZMQ.CHARSET), 0);
        System.out.println("Sent request: " + request);

        // receive reply
        String reply = requester.recvStr(0);
        System.out.println("Received reply: " + reply);

        // close requester socket and context
        requester.close();
        context.close();
    }
}
