package com.blacksw.basicsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // Logger for this class
    private static final String TAG = "MainActivity";

    private EditText mNameText;
    private DatePicker mDobPicker;
    private EditText mEmailText;

    // SharedPrefereces에 읽기 & 쓰기를 위한 helper
    private SharedPreferencesHelper mSharedPreferencesHelper;

    // email input field 텍스트 값 검증 validator
    private EmailValidator mEmailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Shortcut input fields
        mNameText = (EditText) findViewById(R.id.userNameInput);
        mDobPicker = (DatePicker) findViewById(R.id.dateOfBirthInput);
        mEmailText = (EditText) findViewById(R.id.emailInput);

        // EmailValidator 적용
        mEmailValidator = new EmailValidator();
        mEmailText.addTextChangedListener(mEmailValidator);

        // SharedPreferenceHelper 초기화
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences
                (this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(sharedPreferences);

        // SharedPreferences에 저장된 개인정보 값 꺼내서 input field 텍스트로 채움
        populateUi();
    }

    /**
     * {@link SharedPreferences}에 있는 유저 정보 값 꺼내서 input field의 text 초기화
     */
    private void populateUi() {
        SharedPreferenceEntry sharedPreferenceEntry;
        sharedPreferenceEntry = mSharedPreferencesHelper.getPersonalInfo();
        mNameText.setText(sharedPreferenceEntry.getName());
        Calendar dateOfBirth = sharedPreferenceEntry.getDateOfBirth();
        mDobPicker.init(dateOfBirth.get(Calendar.YEAR), dateOfBirth.get(Calendar.MONTH),
                dateOfBirth.get(Calendar.DAY_OF_MONTH), null);
        mEmailText.setText(sharedPreferenceEntry.getEmail());
    }

    /**
     * "Save" 버튼을 클릭 했을 경우, 호출되는 메소드
     */
    public void onSaveClick(View view) {
        // 이메일 인풋 필드 값이 유효 하지 않으면 개인 정보 저장하지 않음
        if(!mEmailValidator.isValid()) {
            mEmailText.setError("Invalid email");
            Log.w(TAG, "Not saving personal information: Invalid email");
            return;
        }

        // input field의 텍스트 값 꺼냄
        String name = mNameText.getText().toString();
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(mDobPicker.getYear(), mDobPicker.getMonth(), mDobPicker.getDayOfMonth());
        String email = mEmailText.getText().toString();

        // input field 값을 이용해서 SharedPreferencesEntry 모델 객체 생성
        SharedPreferenceEntry sharedPreferenceEntry =
                new SharedPreferenceEntry(name, dateOfBirth, email);

        // 개인 정보를 SharedPreferences에 저장
        boolean isSuccess = mSharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry);
        if(isSuccess) {
            Toast.makeText(this, "Personal information saved", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Personal information saved");
        } else {
            Log.e(TAG, "Failed to write personal information to SharedPreferences");
        }
    }

    /**
     * "Revert" 버튼을 클릭 했을 경우, 호출되는 메소드
     */
    public void onRevertClick(View view) {
        populateUi();
        Toast.makeText(this, "Personal information reverted", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Personal information reverted");
    }

}