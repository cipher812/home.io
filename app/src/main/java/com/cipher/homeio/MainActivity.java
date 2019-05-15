package com.cipher.homeio;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    TextView txtTemp,txtHmd,txtPsr,txtAlt;

    void firebase()
    {

    DatabaseReference TRef = database.getReference("Temprature");

    TRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            Float val=dataSnapshot.getValue(Float.class);
            Log.d("Test",String.valueOf(val));
            txtTemp.setText(String.valueOf(val)+" deg C");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Log.d("DB","Error");
        }
    });

    DatabaseReference hRef = database.getReference("Humidity");

    hRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
        {
            Float val=dataSnapshot.getValue(Float.class);
            Log.d("DB",String.valueOf(val));
            txtHmd.setText(String.valueOf(val)+" %");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            Log.d("DB","Error");
        }
    });

        DatabaseReference pRef = database.getReference("Pressure");

        pRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Float val=dataSnapshot.getValue(Float.class);
                Log.d("DB",String.valueOf(val));
                txtPsr.setText(String.valueOf(val)+" hPa");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference aRef = database.getReference("Altitude");

        aRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Float val=dataSnapshot.getValue(Float.class);
                Log.d("DB",String.valueOf(val));
                txtAlt.setText(String.valueOf(val)+" Mtr");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.d("DB","Error");
            }
        });
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Context context=getApplicationContext();
            Toast toast = Toast.makeText(context, "Not Yet Implemented", Toast.LENGTH_LONG);
            Toast home = Toast.makeText(context, "Home page", Toast.LENGTH_LONG);

            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    home.show();
                    return true;
                case R.id.navigation_dashboard:
                    toast.show();
                    return true;
                case R.id.navigation_notifications:
                    toast.show();
                    return true;
            }
            return false;
        }
    };

    private boolean isOnline()
    {
        Context context=getApplicationContext();
        ConnectivityManager cm= (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo ninfo=cm.getActiveNetworkInfo();

        if(ninfo!=null || ninfo.isConnectedOrConnecting())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        txtTemp=findViewById(R.id.txt_temp);
        txtHmd=findViewById(R.id.txt_hmd);
        txtPsr=findViewById(R.id.txt_psr);
        txtAlt=findViewById(R.id.txt_alt);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Context context=getApplicationContext();
        Toast toast;

        try {

            if (isOnline()) {
                toast = Toast.makeText(context, "Connection Active", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                toast = Toast.makeText(context, "Connection Offline", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        catch (NullPointerException e)
        {
            Log.d("Ex","Exception");
            toast = Toast.makeText(context, "Connection Offline", Toast.LENGTH_SHORT);
            toast.show();
        }

        firebase();

    }

}
