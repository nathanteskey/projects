package ie.ul.nathanteskey.thepersonalexcercisealarm;

//Nathaniel Teskey
//20247672

import static android.app.PendingIntent.getBroadcast;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import ie.ul.nathanteskey.thepersonalexcercisealarm.R;


public class LookAroundYou extends AppCompatActivity {
    private String CHANNEL_ID = "Alarm Notification";
    Button startAlarmButton;
    EditText timeInput;
    int time=0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.stepcounter) {

            Intent intent = new Intent(LookAroundYou.this, MainActivity.class);
            startActivity(intent);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_around_you);
        startAlarmButton=(Button)findViewById(R.id.startAlarmButton);
        timeInput=(EditText)findViewById(R.id.timeInput);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void StartAlarm(View view){
        try{
            time= Integer.parseInt(timeInput.getText().toString());
        }
        catch(NumberFormatException e) {
            //only allowing integer values
            Toast.makeText(getApplicationContext(), "Please enter an integer value", Toast.LENGTH_SHORT).show();
            time=1;
        }
        //pending intent to set time until alarm goes off
        Intent intent=new Intent(getApplicationContext(),MyBroadcastReceiver.class);
        intent.putExtra("time", time);
        PendingIntent pendingIntent=getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(1000*time),pendingIntent);
//Adding pending intent to issue notification when alarm is set
        Intent alarmNotification = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, alarmNotification, PendingIntent.FLAG_UPDATE_CURRENT);

        //structuring the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(notificationPendingIntent)
                .setContentTitle("Your Alarm is set!")
                .setContentText("Thank you for setting your alarm.")
                .setSmallIcon(android.R.drawable.btn_star_big_on);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Alarm set notification", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(0, notificationBuilder.build());
    }
}