package ru.synergy.asyncexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadExample extends AppCompatActivity {

    ExecutorService service = Executors.newFixedThreadPool(3);

    int mCounter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            TextView mInfoTextView = (TextView) findViewById(R.id.textViewInfo);
            mInfoTextView.setText("Сегодня ворон было : " + mCounter + " штук");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thead_example);

    }
    public void onClick(View v) throws ExecutionException, InterruptedException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis() + 20 * 1000;
                while (System.currentTimeMillis()<endTime){
                    synchronized (this){
                        try {
                            wait(endTime-System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i("SPARROWS", ("Сегодня ворон было : " + mCounter++ + " штук"));

                    handler.sendEmptyMessage(0);
                }


                // Так нельзя
//                TextView mInfoTextView = (TextView) findViewById(R.id.textViewInfo);
//                mInfoTextView.setText("Сегодня ворон было : " + mCounter++ + " штук");
            }
        };

//      Thread thread = new Thread(runnable);
//      thread.start();

        // когда исполнится тогда и выстрелит
       // service.execute(runnable);
       Future future = service.submit(runnable);
       //future.get();


    }

}
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // do time consuming operations here
//
//            }
//        }).start();



// конструкции для Thread как пример
//    private Runnable doInBackgroundProccessing = new Runnable() {
//        @Override
//        public void run() {
//            backgroundThreadProccessing();
//        }
//    };
//
//    private void backgroundThreadProccessing() {
//        // трудоемкие операции
//    }
//
//    private  void mainProcessing(){
//
//        Thread thread = new Thread(null, doInBackgroundProccessing, "Background");
//
//    }