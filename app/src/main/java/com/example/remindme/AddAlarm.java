package com.example.remindme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddAlarm extends AppCompatActivity {

    EditText message_input, time_input, date_input;
    Button add_button;
    Context context = this;
    int y, m, d, h, mi;
    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        message_input = findViewById(R.id.message_input);
        time_input = findViewById(R.id.time_input);
        date_input = findViewById(R.id.date_input);
        add_button = findViewById(R.id.add_button);

        time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar takvim = Calendar.getInstance();
                int saat = takvim.get(Calendar.HOUR_OF_DAY);
                int dakika = takvim.get(Calendar.MINUTE);

                TimePickerDialog tpd = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time_input.setText(hourOfDay + ":" + minute);
                                h = hourOfDay;
                                mi = minute;
                            }
                        }, saat, dakika, true);
                tpd.setButton(TimePickerDialog.BUTTON_POSITIVE, "Seç", tpd);
                tpd.setButton(TimePickerDialog.BUTTON_NEGATIVE, "İptal", tpd);
                tpd.show();
            }
        });
        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog;
                Calendar calendar;
                int year, month, dayOfMonth;
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddAlarm.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date_input.setText(day + "/" + (month + 1) + "/" + year);
                                y = year;
                                m = month;
                                d = day;
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddAlarm.this);
                myDB.addAlarm(message_input.getText().toString(),
                        time_input.getText().toString(),
                        date_input.getText().toString());
                Calendar current = Calendar.getInstance();
                Calendar cal = Calendar.getInstance();
                cal.set(y, m, d, h, mi, 00);
                System.out.println(cal);
                if (cal.compareTo(current) <= 0) {
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                } else {
                    setAlarm(cal);
                }

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setAlarm(Calendar targetCal) {
        createNotificationChannel();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(context, AlarmReceiver.class);
        notificationIntent.putExtra("Message", message_input.getText().toString());

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), broadcast);
    }

    public void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = null;
            notificationChannel = new NotificationChannel("alarms", "Alarms", importance);
            notificationChannel.setDescription("Reminder");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

}
