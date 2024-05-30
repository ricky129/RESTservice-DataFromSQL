package com.esempio.demo;

import java.io.*;
import java.net.*;

public class HTTPFrontEnd {

    public static void main(String[] args) throws Exception {

        int port = 5000;

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server avviato sulla porta " + port + " ...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String RESTHostname = "localhost";  // Hostname del server locale
            int RESTPort = 8080;                // Porta per l'applicazione locale

            try {
                // Create a connection to the backend server
                Socket RESTSocket = new Socket(RESTHostname, RESTPort);
                OutputStream RESTOutput = RESTSocket.getOutputStream();
                PrintWriter RESTWriter = new PrintWriter(RESTOutput, true);

                // Send a request to the backend
                RESTWriter.println("GET / HTTP/1.1");
                RESTWriter.println("Host: " + RESTHostname);
                RESTWriter.println("Connection: close");
                RESTWriter.println();

                // Read the response from the backend
                BufferedReader RESTReader = new BufferedReader(new InputStreamReader(RESTSocket.getInputStream()));
                StringBuilder RESTResponse = new StringBuilder();
                String line;
                while ((line = RESTReader.readLine()) != null) {
                    RESTResponse.append(line);
                }

                // Log the response from the backend
                System.out.println("Response from backend: " + RESTResponse);

                // Write the response to the client
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: application/json");
                out.println();
                out.println(RESTResponse);

                // Close connections
                RESTReader.close();
                RESTWriter.close();
                RESTSocket.close();
            } catch (IOException e) {
                // Handle connection errors
                System.err.println("Error connecting to backend: " + e.getMessage());
                out.println("HTTP/1.1 500 Internal Server Error");
                out.println("Content-Type: text/plain");
                out.println();
                out.println("Error connecting to backend: " + e.getMessage());
            }

            // Close client connections
            out.close();
            in.close();
            clientSocket.close();
        }
    }
}
