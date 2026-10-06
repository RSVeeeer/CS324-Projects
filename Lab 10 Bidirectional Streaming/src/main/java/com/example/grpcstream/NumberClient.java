package com.example.grpcstream;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberClient {

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        NumberServiceGrpc.NumberServiceStub stub =
                NumberServiceGrpc.newStub(channel);

        AtomicInteger responsesReceived = new AtomicInteger();
        CountDownLatch completed = new CountDownLatch(1);

        StreamObserver<NumberResponse> responseObserver =
                new StreamObserver<NumberResponse>() {
            @Override
            public void onNext(NumberResponse response) {
                int count = responsesReceived.incrementAndGet();
                // Display every 1000th response to avoid flooding the console.
                if (count % 1000 == 0) {
                    System.out.println(
                            "Response " + count + ": "
                            + response.getNumber() + " -> "
                            + response.getResult());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Server error: " + t.getMessage());
                completed.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Server completed the response stream.");
                completed.countDown();
            }
        };

        StreamObserver<NumberRequest> requestObserver =
                stub.checkNumbers(responseObserver);

        Random random = new Random();
        int numbersSent = 0;
        long programStart = System.currentTimeMillis();

        for (int second = 1; second <= 10; second++) {
            long secondStart = System.currentTimeMillis();

            for (int i = 0; i < 10000; i++) {
                int number = random.nextInt(1_000_000) + 1;
                NumberRequest request = NumberRequest.newBuilder()
                        .setNumber(number)
                        .build();
                requestObserver.onNext(request);
                numbersSent++;
            }

            long elapsed = System.currentTimeMillis() - secondStart;
            long sleepTime = 1000 - elapsed;
            if (sleepTime > 0) {
                Thread.sleep(sleepTime);
            }

            System.out.println("Second " + second + " completed.");
        }

        requestObserver.onCompleted();
        completed.await(30, TimeUnit.SECONDS);

        long programEnd = System.currentTimeMillis();
        double executionTime = (programEnd - programStart) / 1000.0;

        System.out.println();
        System.out.println("----- CLIENT SUMMARY -----");
        System.out.println("Total numbers sent: " + numbersSent);
        System.out.println("Total responses received: "
                + responsesReceived.get());
        System.out.printf("Execution time: %.2f seconds%n", executionTime);

        channel.shutdown();
        channel.awaitTermination(5, TimeUnit.SECONDS);
    }
}
