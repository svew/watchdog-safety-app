package hackisu.fall2019.watchdog;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class Alarm extends Service {
    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) { return null; }

    public void onCreate() { player = MediaPlayer.create(this, R.raw.alarm); }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_NOT_STICKY;
    }

    public void onDestroy() {
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }
}
