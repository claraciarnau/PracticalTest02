package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread {

    private int port;
    private ServerSocket serverSocket;
    private HashMap<String, ro.pub.cs.systems.eim.practicaltest02.GenericResults> data;

    public ServerThread(int port) {
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            Log.e("Err", "Server Error");
            e.printStackTrace();
        }

        this.data = new HashMap<>();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client_socket = serverSocket.accept();

                String clientResponse = "";

                if (client_socket != null) {
                    BufferedReader bufferedReader = Utils.getReader(client_socket);
                    String[] request_data = bufferedReader.readLine().split(",");

                    if(request_data[0].equals("get")) {
                        if (data.containsKey(request_data[1])) {
                            clientResponse = data.get(request_data[1]).getValue();
                        } else {
                            clientResponse = "Data does not exist!";
                        }
                    } else {
                        String key = request_data[1];
                        String value = request_data[2];

                        GenericResults newRes = new GenericResults();

                        newRes.setValue(value.toString());

                        this.data.put(key, newRes);

                        clientResponse = "Data added to hashmap!";
                    }

                    // Write to client socket the response
                    PrintWriter printWriter = Utils.getWriter(client_socket);
                    printWriter.println(clientResponse);

                    client_socket.close();
                } else {
                    Log.e("Error", "Null client socket");
                }
            } catch (IOException e) {
                Log.e("Error", "Server Socket error");
                e.printStackTrace();
            }
        }
    }
}
