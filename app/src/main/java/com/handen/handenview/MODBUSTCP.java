package com.handen.handenview;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Vanya on 29.05.2018.
 */

public class MODBUSTCP {

    public static boolean isConnected = false;
    public static String ip = "192.168.4.1";
    public static String status = "";
    public static float[] values = new float[256];

    public MODBUSTCP() {
        for (int i=0;i<values.length;i++)
            values[i] = Float.NaN;
    }

    public void exchange() {
        Socket clientSocket = null;
        DataOutputStream outToServer = null;
        DataInputStream inFromServer = null;
        status = "Поток запущен...";
        while (true) {
            if(isConnected) {
                if(clientSocket == null) {
                    try {
                        clientSocket = new Socket (ip, 502);
                        outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        inFromServer = new DataInputStream(clientSocket.getInputStream());
//                        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                        status = "Соединение установлено";
                    }
                    catch (IOException e) {
                        status = "Ошибка соединения";
                        e.printStackTrace();
                    }
                }
                else {
                    byte[] chars = new byte[6 + 3 + 64 * 2];
                    for (int j=0;j<8 ;j++) {
                        byte[] bytes = new byte[12];
                        bytes[0] = bytes[1] = bytes[2] = bytes[3] = bytes[4] = 0;
                        bytes[5] = 6;
                        bytes[6] = 1;
                        bytes[7] = 3;
                        bytes[8] = 0;
                        bytes[9] = (byte)(j * 64);
                        bytes[10] = 0;
                        bytes[11] = 64;
                        try {
                            if(outToServer != null)
                                outToServer.write(bytes);
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            Thread.sleep(250);
                        }
                        catch(InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            if(inFromServer.available() > 0)
                            {
                                int countOfBytes = inFromServer.read(chars, 0, 6);
                                if(countOfBytes == 6) {
                                    inFromServer.read(chars, 0, 3);
                                    byte b[] = new byte[4];
                                    for(int i = 0; i < 32; i++) {
//                                        int pos = i * 4 + 6 + 3;
//                                        float fl = Float.intBitsToFloat((int) chars[pos] + ((int) chars[pos + 1] << 8) + ((int) chars[pos + 2] << 16) + ((int) chars[pos + 3] << 24));
                                        inFromServer.read(b);
//                                        float fl = inFromServer.readFloat();
                                        float fl = Float.intBitsToFloat((int) b[0] + ((int) b[1] << 8) + ((int) b[2] << 16) + ((int) b[3] << 24));
                                        values[j * 32 + i] = fl;
                                        Log.d("bytes",  Integer.toString (j * 32 + i) + ": " + Float.toString(fl));
                                    }
                                }
                            }
                        }
                        catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                    MainActivity.redrawValues();
                }
            }
            else {
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static float getCurrentValue(int position) {
        if(position >= 0 && position < values.length) {
            return values[position];
        }
        else
            return Float.NaN;
    }
}
