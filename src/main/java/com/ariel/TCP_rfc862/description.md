# Protohackers - Problem 0: Smoke Test

## Problem Description

The goal of this challenge is to implement a simple **TCP Echo Server** that follows the RFC 862 specification. The server must:

- Accept TCP connections from multiple clients.
- Read any data sent by a client and send it back unmodified (echo the data).
- Properly handle binary data.
- Support at least 5 simultaneous clients.
- Close the connection only after the client has finished sending data.

## Implementation in Java

The following Java implementation creates a multithreaded server that listens for TCP connections on port `8080` and echoes back any received data.

---

### Running "Smoke Test" Server (Problem 0)
#### Server
1. Navigate to the project directory.
2. Compile and run the server:
   ```sh
   javac -d out src/com/ariel/TCP_rfc862/Server.java
   java -cp out com.ariel.TCP_rfc862.Server

#### Client
Connect as a client to your Java TCP server via a terminal
1. Run the following command in a terminal:
    ```sh
   nc localhost 8080
   echo "Hello, Server!"
