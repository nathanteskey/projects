package ie.ul.nathanteskey.thepersonalexcercisealarm;

//20247672
//Nathaniel Teskey
import android.content.Context;
import android.content.Intent;

import android.widget.Toast;

public class MyBroadcastReceiver extends android.content.BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        int res=intent.getIntExtra("time",0);
        Toast.makeText(context.getApplicationContext(),"Take a look around, alarm noise for " + res + "seconds ",Toast.LENGTH_SHORT).show();
    }
}
