package com.example.grpcstream;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class NumberServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(50051)
                .addService(new NumberServiceImpl())
                .build()
                .start();

        System.out.println("Number Server started on port 50051...");
        server.awaitTermination();
    }

    static class NumberServiceImpl extends NumberServiceGrpc.NumberServiceImplBase {

        private final AtomicInteger totalCount = new AtomicInteger();
        private final AtomicInteger oddCount = new AtomicInteger();
        private final AtomicInteger evenCount = new AtomicInteger();

        @Override
        public StreamObserver<NumberRequest> checkNumbers(
                StreamObserver<NumberResponse> responseObserver) {
            return new StreamObserver<NumberRequest>() {
                @Override
                public void onNext(NumberRequest request) {
                    int number = request.getNumber();
                    totalCount.incrementAndGet();

                    String result;
                    if (number % 2 == 0) {
                        result = "EVEN";
                        evenCount.incrementAndGet();
                    } else {
                        result = "ODD";
                        oddCount.incrementAndGet();
                    }

                    NumberResponse response = NumberResponse.newBuilder()
                            .setNumber(number)
                            .setResult(result)
                            .build();

                    responseObserver.onNext(response);
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Client error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Client finished sending numbers.");
                    System.out.println("Total numbers received: " + totalCount.get());
                    System.out.println("Odd numbers: " + oddCount.get());
                    System.out.println("Even numbers: " + evenCount.get());
                    responseObserver.onCompleted();
                }
            };
        }
    }
}