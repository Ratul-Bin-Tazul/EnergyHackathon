package com.drbt.energyhackathon;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class AddManageActivity extends AppCompatActivity {

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    TextView lightHeading;
    Switch light1;
    String address;
    //private ProgressDialog progress;
    BluetoothSocket btSocket = null;
    BluetoothAdapter myBluetooth = null;
    private boolean isBtConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manage);

        lightHeading = (TextView) findViewById(R.id.lightHeading);
        light1 = (Switch) findViewById(R.id.light1Switch);

        myBluetooth = BluetoothAdapter.getDefaultAdapter();


        if (myBluetooth == null) {

            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon, 1);


        }

        //address = myBluetooth.getAddress();
        address = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), "bluetooth_address");
        lightHeading.setText(address); //gets address

        //new ConnectBT().execute();
        try {
            if (btSocket == null || !isBtConnected) {
                BluetoothDevice device = myBluetooth.getRemoteDevice(address);
                btSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                btSocket.connect();
            }
        } catch (IOException e) {
            //msg(e.toString());
            System.err.print(e);
            //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            //ConnectSuccess = false;
        }

        if (light1.isChecked()) {
            SendChar("L255");
        } else {
            SendChar("L000");
        }


        light1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    SendChar("L255");
                else
                    SendChar("L000");
            }
        });


        Toast.makeText(getApplicationContext(), address, Toast.LENGTH_LONG).show();


    }

    private void SendChar(String str) {
        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write(str.getBytes());
                btSocket.close();
            } catch (IOException e) {
                msg(e + " Error, Can not send data!");
            }
        }
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            //progress = ProgressDialog.show(AddManageActivity.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (btSocket == null || !isBtConnected) {
                    BluetoothDevice device = myBluetooth.getRemoteDevice(address);
                    btSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID);
                    //BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                //msg(e.toString());
                System.err.print(e);
                //Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                //ConnectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Please try again.");
                finish();
            } else {
                msg("Connected.");
                isBtConnected = true;
            }
            //progress.dismiss();
        }
    }
}
