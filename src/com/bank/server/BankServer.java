package com.bank.server;

import com.bank.db.Database;
import java.io.*;
import java.net.*;

// WAP: Networking - Simple HTTP server on localhost:8080
public class BankServer {

    public static void startServer() {
        new Thread(() -> {
            try {
                ServerSocket server = new ServerSocket(8080);
                System.out.println("SecureBank server running at http://localhost:8080");
                while (true) {
                    Socket client = server.accept();
                    new Thread(() -> handleRequest(client)).start();
                }
            } catch (Exception e) {
                System.out.println("Server error: " + e.getMessage());
            }
        }).start();
    }

    private static void handleRequest(Socket client) {
        try {
            BufferedReader in  = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter    out = new PrintWriter(client.getOutputStream());

            String line = in.readLine();
            if (line == null) { client.close(); return; }

            String path = line.split(" ").length > 1 ? line.split(" ")[1] : "/";

            String body;
            if (path.startsWith("/balance/")) {
                String accNo = path.replace("/balance/", "").toUpperCase();
                var acc = Database.getAccount(accNo);
                body = acc != null
                    ? "{\"account\":\"" + accNo + "\",\"name\":\"" + acc.getHolderName() + "\",\"balance\":" + acc.getBalance() + "}"
                    : "{\"error\":\"Account not found\"}";
            } else {
                body = buildHomePage();
            }

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: " + (path.equals("/") ? "text/html" : "application/json"));
            out.println("Connection: close");
            out.println();
            out.println(body);
            out.flush();
            client.close();
        } catch (Exception e) { }
    }

    private static String buildHomePage() {
        return "<!DOCTYPE html><html><head><title>SecureBank</title>"
            + "<style>body{font-family:Arial;background:#0f1e46;color:white;display:flex;justify-content:center;align-items:center;height:100vh;margin:0}"
            + ".card{background:#192a5a;padding:40px;border-radius:12px;text-align:center;border:1px solid #0099ff44}"
            + "h1{color:#00b4ff}p{color:#8ca0dc}code{background:#223366;padding:6px 14px;border-radius:6px;color:#00ff88;font-size:15px}"
            + "</style></head><body><div class='card'>"
            + "<h1>🏦 SecureBank - Local Server</h1>"
            + "<p>Server is running! Use the API below:</p><br>"
            + "<p>Get account balance:</p>"
            + "<code>http://localhost:8080/balance/ACC001</code><br><br>"
            + "<code>http://localhost:8080/balance/ACC002</code><br><br>"
            + "<code>http://localhost:8080/balance/ACC003</code>"
            + "<br><br><p style='color:#8ca0dc;font-size:13px'>Open the Java app to use the full banking portal.</p>"
            + "</div></body></html>";
    }
}
