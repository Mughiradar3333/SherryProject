package com.example.sherryproject.Helper;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class InternetCheck extends AsyncTask<Void,Void,Boolean> {

   public interface Consumer{
        void accept(Boolean internet);
    }

    Consumer consumer;

    public InternetCheck(Consumer consumer){
        execute();
        this.consumer=consumer;
    }


    @Override
    protected Boolean doInBackground(Void... voids){
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("google.com",80),1500);
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }


    }


    @Override
    protected void onPostExecute(Boolean aBoolean){
        super.onPostExecute(aBoolean);
        consumer.accept(aBoolean);
    }

}
