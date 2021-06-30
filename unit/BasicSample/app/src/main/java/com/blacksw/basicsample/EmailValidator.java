package com.blacksw.basicsample;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.regex.Pattern;

/**
 * TextWatcher는 Editable의 리스너에 등록되어 사용되며
 * Editable에서 텍스트가 변경될 때 마다 해당 객체의 메소드를 호출한다.
 */
public class EmailValidator implements TextWatcher {

    /**
     * 이메일 정규표현식 패턴
     * [] : 사용 가능한 문자의 범위와 특수 문자를 입력한다.
     * {} : 문자의 범위를 의미한다.
     * \\ : 문자를 이스케이프 처리하기 위해 사용한다.
     * () : 괄호 안에 있는 값을 하나의 문자로 취급한다.
     */
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
    );

    private boolean mIsValid = false;

    public boolean isValid() {
        return mIsValid;
    }

    /**
     * 입력받은 이메일 값이 유효한지를 검증한다.
     *
     * @param email     유효성 검증을 할 이메일
     * @return {@code true} 이메일 값이 유효할 경우 {@code false} 그렇지 않을 경우
     */
    public static boolean isValidEmail(CharSequence email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Editable의 텍스트가 변경될 때마다 메소드가 호출된다.
     */
    @Override
    public void afterTextChanged(Editable s) {
        mIsValid = isValidEmail(s);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        /* No-op */
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        /* No-op */
    }
}
