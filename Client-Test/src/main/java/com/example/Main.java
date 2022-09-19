package com.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger("Client");

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter server IP and port: ");

        String string = scanner.nextLine();
        String[] split = string.split(":");

        String ip;
        String stringPort;

        try {
            ip = split[0];
            stringPort = split[1];
        } catch (ArrayIndexOutOfBoundsException ex) {
            logger.severe("You've entered invalid IP!");
            return;
        }

        int port;

        try {
            port = Integer.parseInt(stringPort);
        } catch (NumberFormatException ex) {
            logger.severe("Port cannot be string");
            return;
        }

        scheduler.scheduleAtFixedRate(() -> {
            try {
                Socket socket = new Socket(ip, port);

                InputStream inputStream = socket.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(inputStream);

                String message = dataInputStream.readUTF();
                logger.info("The message sent from the server: " + message);
            } catch (IOException ex) {
                logger.severe("Program returned an error!");
            }
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

}