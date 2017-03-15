package com.example.linxiaotao.aopexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.monitor.Monitor;
import com.example.monitor.TimeMonitor;

import java.util.concurrent.TimeUnit;

@Monitor
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        test();

    }

    @Monitor
    private void test(){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
