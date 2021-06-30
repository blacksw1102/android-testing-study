package com.blacksw.basicsample;

import java.util.Calendar;

/**
 * 유저의 개인 정보를 SharedPreference에 저장하기 위해서 사용할 Model 클래스
 * 여기서 Model이란 DTO 또는 VO를 의미한다.
 */
public class SharedPreferenceEntry {

    // 유저의 이름
    private final String mName;
    // 유저의 생년월일
    private final Calendar mDateOfBirth;
    // 유저의 이메일
    private final String mEmail;

    public SharedPreferenceEntry(String mName, Calendar mDateOfBirth, String mEmail) {
        this.mName = mName;
        this.mDateOfBirth = mDateOfBirth;
        this.mEmail = mEmail;
    }

    public String getName() {
        return mName;
    }

    public Calendar getDateOfBirth() {
        return mDateOfBirth;
    }

    public String getEmail() {
        return mEmail;
    }

}
