package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button startService;
    Button clientPut;
    Button clientGet;

    EditText serverPort;
    EditText clientIPAddr;
    EditText clientPort;
    EditText clientPutKey;
    EditText clientPutValue;
    EditText clientGetKey;

    TextView responseTextView;

    private ServerThread serverThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService = findViewById(R.id.StartServerButton);
        clientPut = findViewById(R.id.PutButton);
        clientGet = findViewById(R.id.GetButton);

        serverPort = findViewById(R.id.ServerPortEditText);
        clientIPAddr = findViewById(R.id.ClientConnectAddressEditText);
        clientPort = findViewById(R.id.ClientConnectPortEditText);
        clientPutKey = findViewById(R.id.ClientPutKeyEditText);
        clientPutValue = findViewById(R.id.ClientPutValueEditText);
        clientGetKey = findViewById(R.id.ClientGetKeyEditText);

        responseTextView = findViewById(R.id.ResultEditText);

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverThread = new ServerThread(Integer.parseInt(serverPort.getText().toString()));
                serverThread.start();
            }
        });

        clientPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientThreadPut clientThreadPut = new ClientThreadPut(
                        Integer.parseInt(clientPort.getText().toString()),
                        clientIPAddr.getText().toString(),
                        clientPutKey.getText().toString(),
                        clientPutValue.getText().toString(),
                        responseTextView);
                clientThreadPut.start();
            }
        });

        clientGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientThreadGet clientThreadGet = new ClientThreadGet(
                        Integer.parseInt(clientPort.getText().toString()),
                        clientIPAddr.getText().toString(),
                        clientPutKey.getText().toString(),
                        responseTextView);
                clientThreadGet.start();
            }
        });
    }
}