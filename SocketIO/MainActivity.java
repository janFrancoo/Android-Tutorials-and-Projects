package com.janfranco.socketio;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Transport;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Manager;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Socket mSocket;

    private EditText inputMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.sendButton);
        inputMessage = findViewById(R.id.inputText);

        try {
            mSocket = IO.socket("http://192.168.1.38:5000/api");
            mSocket.on("message", listenMessages);
            mSocket.connect();
            Log.d("NEW_MSG", "Connected");
            mSocket.io().on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Transport transport = (Transport) args[0];
                    transport.on(Transport.EVENT_ERROR, new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
                            Exception e = (Exception) args[0];
                            e.printStackTrace();
                            Objects.requireNonNull(e.getCause()).printStackTrace();
                        }
                    });
                }
            });
        } catch (URISyntaxException ignored) {}

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String message = inputMessage.getText().toString().trim();
        if (message.equals(""))
            return;

        inputMessage.setText("");
        mSocket.emit("message", message);
    }

    private Emitter.Listener listenMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String sender;
                    String message;
                    try {
                        sender = data.getString("sender");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    Log.d("NEW_MSG", sender + " " + message);
                }
            });
        }
    };

}
