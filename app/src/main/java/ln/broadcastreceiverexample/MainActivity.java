package ln.broadcastreceiverexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        findViewById(R.id.button_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MyService.class);
                intent.setAction(MyService.ACTION_START);
                startService(intent);
            }
        });
        findViewById(R.id.button_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MyService.class);
                intent.setAction(MyService.ACTION_STOP);
                startService(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceivers();
    }

    private void registerReceivers() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String value = intent.getStringExtra(MyService.EXTRA_VALUE);
                textView.setText(value);
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyService.ACTION_BROADCAST);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unRegisterReceivers();
    }

    private void unRegisterReceivers() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
