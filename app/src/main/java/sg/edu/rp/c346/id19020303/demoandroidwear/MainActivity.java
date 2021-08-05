package sg.edu.rp.c346.id19020303.demoandroidwear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import androidx.core.app.RemoteInput;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    int notificationId = 001; // An unique ID for our notification
    Button btnNotify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNotify = findViewById(R.id.btnNotify);

        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager nm = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new
                            NotificationChannel("default", "Default Channel",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    channel.setDescription("This is for default notification");
                    nm.createNotificationChannel(channel);
                }

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Action action = new
                        NotificationCompat.Action.Builder(
                                R.mipmap.ic_launcher, "This is an Action", pendingIntent).build();

                Intent intentReply = new Intent(MainActivity.this, ReplyActivity.class);

                PendingIntent pendingIntentReply = PendingIntent.getActivity(
                        MainActivity.this, 0, intentReply,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteInput ri = new RemoteInput.Builder("status")
                        .setLabel("Status Report")
                        .setChoices(new String [] {"Done ", "Not Yet"})
                        .build();

                NotificationCompat.Action action2 = new
                        NotificationCompat.Action.Builder(
                                R.mipmap.ic_launcher,
                        "Reply", pendingIntent)
                        .addRemoteInput(ri)
                        .build();

                NotificationCompat.WearableExtender extender = new
                        NotificationCompat.WearableExtender();
                extender.addAction(action);
                extender.addAction(action2);

                String text = getString(R.string.basic_notify_msg);
                String title = getString(R.string.notification_title);

                NotificationCompat.Builder builder = new
                        NotificationCompat.Builder(MainActivity.this, "default");

                builder.setContentText(text);
                builder.setContentText(title);
                builder.setSmallIcon(android.R.drawable.btn_star_big_off);

                //Attach the action for wear notification created above
                builder.extend(extender);
                Notification notification = builder.build();
                nm.notify(notificationId, notification);
            }
        });
    }
}