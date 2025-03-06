# Protohackers - Problem 1: Prime Time

## Problem Description

A government department is outsourcing its primality testing to the lowest bidder. The task is to implement a TCP server that follows a JSON-based request-response protocol to determine whether a given number is prime.


## Solution Overview

The solution implements a **multi-threaded TCP server** that follows the JSON-based request-response protocol described in the problem statement. The server efficiently handles multiple clients simultaneously, processes primality checks, and ensures proper error handling.

### 1. TCP Server Setup
- The server listens on a specified port for incoming TCP connections.
- It accepts multiple clients and handles each one in a separate thread.

### 2. Handling Client Requests
- When a client connects, the server continuously listens for JSON-encoded requests.
- Each request is parsed and validated:
    - The `"method"` field must be `"isPrime"`.
    - The `"number"` field must be a valid JSON number.

### 3. Primality Check
- If the number is **an integer**, a primality test is applied:
    - Small integers are checked using a fast prime-checking function.
    - Large integers are processed using **probabilistic primality testing**.
- If the number is **a floating-point value**, it is automatically considered **not prime**.

### 4. Sending Responses
- The server responds with a JSON object containing:
  ```json
  {"method":"isPrime","prime":true}
  ```
- or if its a malformed request
  ```json
    malformed
    ```

---

### Running "Prime" Server (Problem 1)
#### Server
1. Navigate to the project directory.
2. Compile and run the server:
   ```sh
   javac -d out src/com/ariel/TCP_rfc862_json/Server_2.java
   java -cp out com.ariel.TCP_rfc862_json.Server_json

#### Client
Connect as a client to your Java TCP server via a terminal
1. Run the following command in a terminal:
    ```sh
   echo '{"method":"isPrime","number":7}' | nc localhost 8080