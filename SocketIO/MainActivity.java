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

    private EditText inputMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.sendButton);
        inputMessage = findViewById(R.id.inputText);

        String userId = "5eff14d255ad5b2d840a2257";
        String chatId = "5efeeef8b1631c3cfcbb84f7_5eff14d255ad5b2d840a2257";
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVlZmYxNGQyNTVhZDViMmQ4NDBhMjI1NyIsImVtYWlsIjoicG9pc29ud2VlYkBnbWFpbC5jb20iLCJ1c2VybmFtZSI6IkphbkZyYW5jbyIsImlzVmVyaWZpZWQiOnRydWUsInN1YkV4cGlyZSI6IjIwMjAtMDctMDZUMTU6NTY6NDUuNDk5WiIsImNyZWRpdHMiOjUsImlhdCI6MTU5Mzg3NTc2OCwiZXhwIjoxNTkzODc5MzY4fQ.EDxOTtJL_x8yDtiMwOYOXVMNbJlNhTkrA_mx3IaBiGE";
        IO.Options options = new IO.Options();
        options.forceNew = true;
        options.reconnectionAttempts = Integer.MAX_VALUE;
        options.timeout = 10000;
        options.query = "auth=" + accessToken;

        try {
            Socket mSocket = IO.socket("http://192.168.1.38:5000", options);
            mSocket.emit("listen_channnels", userId);
            mSocket.emit("listen_channel", chatId);
            mSocket.on("new_message", listenMessages);
            mSocket.on("new_chat", listenChats);

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
        // post /chat/message
    }

    private Emitter.Listener listenMessages = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    try {
                        if (data.has("success"))
                            Log.d("NEW_MSG", data.getString("success"));
                        if (data.has("message"))
                            Log.d("NEW_MSG", data.getString("message"));
                        if (data.has("sender"))
                            Log.d("NEW_MSG", data.getString("sender"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener listenChats = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];

                    try {
                        if (data.has("success"))
                            Log.d("NEW_MSG", data.getString("success"));
                        if (data.has("message"))
                            Log.d("NEW_MSG", data.getString("message"));
                        if (data.has("chatId"))
                            Log.d("NEW_MSG", data.getString("chatId"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

}
