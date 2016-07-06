package com.test1;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.test1.request.BaseRequest;
import com.test1.request.CreateRequest;

import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.phone)
    EditText phone;

    @BindView(R.id.international_code)
    EditText internationalCode;

    @BindView(R.id.result)
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.generate_random)
    public void generateRandomNumber() {
        int[] digitsPhone = new int[10];
        int[] digitsCode = new int[3];

        Random generator = new Random();

        for (int i = 0; i < 10; i++) {
            digitsPhone[i] = generator.nextInt(9);
        }

        for (int i = 0; i <= 2; i++) {
            digitsCode[i] = generator.nextInt(9);
        }

        phone.setText(Arrays.toString(digitsPhone).replaceAll("[^\\d]", ""));

        internationalCode.setText(Arrays.toString(digitsCode).replaceAll("[^\\d]", ""));


    }

    @OnClick(R.id.send_request)
    public void sendRequest() {
        CreateRequest request = new CreateRequest(this, createCallback, phone.getText().toString(), internationalCode.getText().toString());
        request.executeAsync();
    }

    BaseRequest.CallbackListener<JsonObject> createCallback = new BaseRequest.CallbackListener<JsonObject>() {
        @Override
        public void success(Response<JsonObject> response) {
            AccountManager accountManager = AccountManager.get(MainActivity.this);
            final Bundle extraData = new Bundle();
            extraData.putString("someKey", "stringData");
            Account account = new Account("testAccount", "testType");
            //for now if it will be successful add dummy data
            boolean accountCreated = accountManager.addAccountExplicitly(account, null, extraData);
            resultTextView.setText("success");
        }

        @Override
        public void failure(Throwable t) {
            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void error(Response<JsonObject> error) {
            Toast.makeText(MainActivity.this, error.message(), Toast.LENGTH_LONG).show();
        }
    };

}
