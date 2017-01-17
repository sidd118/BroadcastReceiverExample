package ln.broadcastreceiverexample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    public static String ACTION_START = "Start";
    public static String ACTION_STOP = "Stop";

    public static String ACTION_BROADCAST = "Broadcast";

    public static String EXTRA_VALUE = "Value";

    Timer timer;
    TimerTask timerTask;
    int counter = 0;

    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action.equals(ACTION_START)) {
            startTimer();
        } else if (action.equals(ACTION_STOP)) {
            stopTimer();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                counter += 1;
                sendBroadcast(String.valueOf(counter));
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void stopTimer() {
        counter = 0;
        timer.cancel();
    }

    private void sendBroadcast(String value) {
        Intent intent = new Intent();
        intent.setAction(ACTION_BROADCAST);
        intent.putExtra(EXTRA_VALUE, value);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
