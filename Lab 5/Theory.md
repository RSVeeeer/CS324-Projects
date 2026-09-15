# Lab 5: Remote Method Invocation in Java

## Activity Overview

This activity demonstrates Java Remote Method Invocation (RMI). RMI allows a client program to invoke methods on an object running in a separate server program. The server creates an RMI registry, registers a remote service, and waits for client requests. The client connects to the registry, looks up the service, and calls its remote methods as if they were local methods.

The project contains two NetBeans applications:

- **Bank**: The server application. It defines and implements the remote banking service, stores the account balance, creates the RMI registry, and registers the service.
- **Customer**: The client application. It connects to the server registry, obtains a reference to the banking service, and invokes remote operations.

The `Api` interface is shared by both applications. `ApiImpl` is only in the server because the server contains the implementation and account state. `Data` is used to transfer values between the client and server and implements `Serializable` so that RMI can transmit it.

## Questions and Answers

### Question 1

`Api.java` contains the remote service interface. It declares the methods that can be called remotely and extends `Remote`. Each remote method declares `RemoteException` because communication can fail.

Both projects contain a copy because the client needs the interface to compile its remote calls, while the server needs it to implement the service. Both copies must have the same package, method names, parameter types, and return types.

### Question 2

Only the Bank project contains `ApiImpl.java` because it contains the implementation of the remote methods. The server owns the account balance and performs the requested operations. The Customer project only uses the remote interface and does not need to know how the methods are implemented.

### Question 3

When the client calls `setBalance(1000)`, the client-side RMI proxy sends the method request to the server. The server dispatches the request to `ApiImpl.setBalance`, changes the account balance to `1000`, prints the new balance, and sends the return value back to the client.

### Question 4

The server creates the registry and registers its service in `Bank.java` with:

```java
Registry registry = LocateRegistry.createRegistry(1099);
registry.rebind(Api.class.getSimpleName(), new ApiImpl());
```

The client connects to the registry and looks up the service with:

```java
registry = LocateRegistry.getRegistry(HOST, PORT);
Api remoteApi = (Api) registry.lookup(Api.class.getSimpleName());
```

### Question 5

Each original client run prints:

```text
New balance = 1000
```

The server prints `new balance: 1000` for each request. Since `setBalance` explicitly resets the balance to `1000`, repeated client runs produce the same result.

### Question 6

The server continues running because the RMI registry and the exported remote object keep listening for requests from clients. The server is designed to remain available for multiple client invocations rather than exit after serving one request.

### Question 7

The client process appears only while the client program is running. It ends after the `main` method completes because it performs its remote calls and then has no more work to do.

### Question 8

The three additional methods are:

- `addBalance`: adds an integer amount to the balance.
- `withdrawBalance`: subtracts an integer amount from the balance.
- `addInterest`: increases the balance by a percentage interest rate.

The client currently starts by setting the balance to `1000`, adds `250`, withdraws `100`, and adds `10%` interest. Its output is:

```text
New balance = 1000
After deposit = 1250
After withdrawal = 1150
After interest = 1265
```

### Question 9

In the object-based version, the client passes `Data` objects and receives `Data` objects. The output remains:

```text
New balance = 1000
After deposit = 1250
After withdrawal = 1150
After interest = 1265
```

The value is accessed using `getValue()` after the remote method returns.

### Question 10

Serializing an object means converting its state into a format that can be written to a stream or transmitted across a network. RMI serializes the `Data` object on the client side, sends it to the server, and reconstructs it there. The server serializes the return object so that the client can reconstruct it.

The `Data` class implements `Serializable` because objects passed as parameters or returned from remote methods must be transferable between the client and server. The `serialVersionUID` identifies the serialized class version.
