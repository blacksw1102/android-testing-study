package com.blacksw.basicsample;

import android.content.SharedPreferences;

import java.util.Calendar;

public class SharedPreferencesHelper {

    // SharedPreferences에 값들을 저장하기 위한 Keys
    static final String KEY_NAME = "key_name";
    static final String KEY_DOB = "key_dob_millis";
    static final String KEY_EMAIL = "key_email";

    private final SharedPreferences mSharedPreferences;

    /**
     * 의존 주입(DI)를 위한 생성자
     *
     * @param mSharedPreferences {@link SharedPreferences}는 DAO로서 사용된다.
     */
    public SharedPreferencesHelper(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    /**
     * {@link SharedPreferenceEntry}에 담긴 유저의 개인 정보를 {@link SharedPreferences}에 저장한다.
     *
     * @param sharedPreferenceEntry {@link SharedPreferences}에 저장할 유저 개인정보를 가지고 있다.
     * @return {@code true} {@link SharedPreferences}에 commit을 성공 했을 경우  {@code false} 그렇지
     *          않을 경우
     */
    public boolean savePersonalInfo(SharedPreferenceEntry sharedPreferenceEntry) {
        // SharedPreferences 트랜잭션 작업을 시작한다.
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_NAME, sharedPreferenceEntry.getName());
        editor.putLong(KEY_DOB, sharedPreferenceEntry.getDateOfBirth().getTimeInMillis());
        editor.putString(KEY_EMAIL, sharedPreferenceEntry.getEmail());

        // SharedPreferences 변경사항을 commit한다.
        return editor.commit();
    }

    /**
     * {@link SharedPreferences}에서 유저의 개인정보를 꺼내서 {@link SharedPreferenceEntry} 모델 객체로
     * 회수한다.
     *
     * @return {@link SharedPreferenceEntry}를 회수한다.
     */
    public SharedPreferenceEntry getPersonalInfo() {
        String name = mSharedPreferences.getString(KEY_NAME, "");
        /*
        * 날짜 값은 저장 및 관리 시에는 millis 값으로 지니고 있다가
        * 사용할 때에는 Calendar 인스턴스를 생성해서 millis 값으로 시간을 지정해서 쓴다.
        * */
        Long dobMillis =
                mSharedPreferences.getLong(KEY_DOB, Calendar.getInstance().getTimeInMillis());
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.setTimeInMillis(dobMillis);
        String email = mSharedPreferences.getString(KEY_EMAIL, "");


        return new SharedPreferenceEntry(name, dateOfBirth, email);
    }

}
