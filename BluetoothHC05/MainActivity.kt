package com.janfranco.fpilot

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.getDefaultAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    private var bluetoothAdapter: BluetoothAdapter = getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null
    private var isBluetoothConnected: Boolean = false
    private val phoneUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!bluetoothAdapter.isEnabled)
            bluetoothAdapter.enable()

        button_light_on.setOnClickListener {
            sendSignal("1")
        }

        button_light_off.setOnClickListener {
            sendSignal("0")
        }

        button_connect_hc05.setOnClickListener {
            connect()
        }
    }

    private fun connect() {
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
        pairedDevices?.forEach { device ->
            if (device.name.equals("HC-05")) {
                address = device.address
                Thread {
                    try {
                        if (bluetoothSocket == null || !isBluetoothConnected) {
                            val disp: BluetoothDevice? = bluetoothAdapter.getRemoteDevice(address)
                            bluetoothSocket = disp?.createInsecureRfcommSocketToServiceRecord(phoneUUID)
                            bluetoothSocket?.connect()
                        }
                    } catch (e: Exception) {
                        bluetoothSocket = null
                    }
                }.start()
            }
        }
    }

    private fun sendSignal(number: String) {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket?.outputStream?.write(number.toByteArray())
            } catch (_: IOException) { }
        } else {
            Toast.makeText(baseContext, "Not connected", Toast.LENGTH_LONG).show()
            connect()
        }
    }

    override fun onDestroy() {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket?.close()
            } catch (_: IOException) { }
        }

        super.onDestroy()
    }

}
