package com.oneissue;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.onesignal.OneSignal;

public class CallUIActivity extends AppCompatActivity {
  private int notificationId;

  @Override
  public void onBackPressed() {
    // doing nothing on back button press (no super call) to not confuse user
  }

  @RequiresApi(api = Build.VERSION_CODES.O_MR1)
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    OneSignal.getNotifications().clearAllNotifications();
    super.onCreate(savedInstanceState);

    setShowWhenLocked(true);
    setTurnScreenOn(true);

    setContentView(R.layout.call_ui_activity);

    Intent fullScreenIntent = getIntent();
    notificationId = fullScreenIntent.getIntExtra("notificationId", 0);

    setButtonClickListeners();
  }
  private void setButtonClickListeners() {
    Button answerButton = findViewById(R.id.answer_button);
    answerButton.setOnClickListener(v -> {
      finish();
    });

    Button declineButton = findViewById(R.id.decline_button);
    declineButton.setOnClickListener(v -> {
      finish();
    });
  }

  @Override
  protected void onPause() {
    super.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public void finish() {
    // Tested remove, but didn't help anyhow
//    OneSignal.getNotifications().removeNotification(notificationId);
    super.finish();
  }
}
