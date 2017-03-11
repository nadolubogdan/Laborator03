package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PhoneDialerActivity extends AppCompatActivity {

    private TextView phoneNumberEditText;
    private ImageButton backSpaceImageButton, callImageButton, hangupImageButton;
    private Button genericButton;
    private static final int REQUEST_CALL_PHONE = 1;

    final private static int[] buttonIds = {
            R.id.number_0_button,
            R.id.number_1_button,
            R.id.number_2_button,
            R.id.number_3_button,
            R.id.number_4_button,
            R.id.number_5_button,
            R.id.number_6_button,
            R.id.number_7_button,
            R.id.number_8_button,
            R.id.number_9_button,
            R.id.star_button,
            R.id.pound_button
    };

    private GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private CallButtonClickListener callButtonClickListener = new CallButtonClickListener();
    private HangupButtonClickListener hangupButtonClickListener = new HangupButtonClickListener();

    private class GenericButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            phoneNumberEditText.setText(phoneNumberEditText.getText().toString() +
                    ((Button)v).getText().toString());
        }
    }

    private class BackspaceButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumberEditText.setText(phoneNumber.substring(0, phoneNumber.length() - 1));
            }
        }
    }

    private class CallButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private class HangupButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        phoneNumberEditText = (TextView) findViewById(R.id.phone_number_edit_text);
        backSpaceImageButton = (ImageButton) findViewById(R.id.backspace_button);
        callImageButton = (ImageButton) findViewById(R.id.call_button);
        hangupImageButton = (ImageButton) findViewById(R.id.hangup_button);

        for (int index = 0; index < buttonIds.length; index++) {
            genericButton = (Button) findViewById(buttonIds[index]);
            genericButton.setOnClickListener(genericButtonClickListener);
        }

        backSpaceImageButton.setOnClickListener(backspaceButtonClickListener);
        callImageButton.setOnClickListener(callButtonClickListener);
        hangupImageButton.setOnClickListener(hangupButtonClickListener);

    }

}
