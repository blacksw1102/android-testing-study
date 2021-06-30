package com.blacksw.basicsample;

import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesHelperTest {

    private static final String TEST_NAME = "Test name";

    private static final String TEST_EMAIL = "test@email.com";

    private static final Calendar TEST_DATE_OF_BIRTH = Calendar.getInstance();

    static {
        TEST_DATE_OF_BIRTH.set(1980,1,1);
    }

    private SharedPreferenceEntry mSharedPreferencesEntry;

    private SharedPreferencesHelper mMockSharedPreferencesHelper;

    private SharedPreferencesHelper mMockBrokenSharedPreferencesHelper;

    @Mock
    SharedPreferences mMockSharedPreferences;

    @Mock
    SharedPreferences mMockBrokenSharedPreferences;

    @Mock
    SharedPreferences.Editor mMockEditor;

    @Mock
    SharedPreferences.Editor mMockBrokenEditor;

    @Before
    public void initMocks() {
        mSharedPreferencesEntry = new SharedPreferenceEntry(TEST_NAME, TEST_DATE_OF_BIRTH, TEST_EMAIL);

        // Mocked SharedPreferences를 생성한다.
        mMockSharedPreferencesHelper = createMockSharedPreferencesHelper();

        // 데이터 저장에 실패한 Mocked SharedPreferences를 생성한다.
        mMockBrokenSharedPreferencesHelper = createMockBrokenSharedPreferencesHelper();
    }

    @Test
    public void sharedPreferencesHelper_SavePersonalInformation() {
        // SharedPreferences에 SharedPreferenceEntry를 저장한다
        boolean success =
                mMockSharedPreferencesHelper.savePersonalInfo(mSharedPreferencesEntry);
        assertThat("SharedPreferences에 SharedPreferencesEntry를 성공적으로 저장했을 때 결과로 true를 받는지 확인한다",
                success, is(true));
    }

    @Test
    public void sharedPreferencesHelper_SavePersonalInformationFailed_ReturnsFalse() {
        // SharedPreferences에 SharedPreferenceEntry를 저장한다.
        boolean success =
                mMockBrokenSharedPreferencesHelper.savePersonalInfo(mSharedPreferencesEntry);
        assertThat("SharedPreferences에 SharedPreferencesEntry 저장을 실패했을 때 결과로 false 값을 받는지 확인한다",
                success, is(false));
    }

    @Test
    public void sharedPreferencesHelper_getPersonalInfo_ResultSuccess() {
        // SharedPreferences에서 SharedPreferencesEntry를 가져온다.
        SharedPreferenceEntry savedSharedPreferencesEntry =
                mMockSharedPreferencesHelper.getPersonalInfo();

        // 개인 정보 값이 일치하는지 확인한다
        // assertThat() 메소드에선 실제 액션 처리 후 결과 값과 예상 결과 값을 비교하여 테스트를 진행한다
        assertThat("SharedPreferences에 저장한 SharedPreferencesEntry.name 값이 제대로 저장되어 있는지 확인",
                savedSharedPreferencesEntry.getName(),
                is(mSharedPreferencesEntry.getName()));
        assertThat("SharedPreferences에 저장한 SharedPreferencesEntry.dayOfBirth 값이 제대로 저장되어 있는지 확인",
                savedSharedPreferencesEntry.getDateOfBirth(),
                is(mSharedPreferencesEntry.getDateOfBirth()));
        assertThat("SharedPreferences에 저장한 SharedPreferencesEntry.email 값이 제대로 저장되어 있는지 확인",
                savedSharedPreferencesEntry.getEmail(),
                is(mSharedPreferencesEntry.getEmail()));
    }

    /**
     * Mocked SharedPreferences를 생성한다.
     */
    private SharedPreferencesHelper createMockSharedPreferencesHelper() {
        // SharedPreferences에서 유저 값들을 성공적으로 읽어오는 결과를 모킹한다.
        // when()과 같은 메소드 호출이 발샐할 경우에 thenReturn() 값으로 결과를 대신 반환
        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_NAME), anyString()))
                .thenReturn(mSharedPreferencesEntry.getName());
        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_EMAIL), anyString()))
                .thenReturn(mSharedPreferencesEntry.getEmail());
        when(mMockSharedPreferences.getLong(eq(SharedPreferencesHelper.KEY_DOB), anyLong()))
                .thenReturn(mSharedPreferencesEntry.getDateOfBirth().getTimeInMillis());

        // 커밋 성공 결과를 모킹한다.
        when(mMockEditor.commit()).thenReturn(true);

        // SharedPreferences가 editor() 메소드 호출 시, MockEditor가 리턴되는 것을 모킹한다.
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);
        return new SharedPreferencesHelper(mMockSharedPreferences);
    }

    /**
     * 데이터 작성에 실패했을 때의 Mocked SharedPreferences를 생성한다.
     */
    private SharedPreferencesHelper createMockBrokenSharedPreferencesHelper() {
        // 커밋 실패 결과를 모킹한다.
        when(mMockBrokenEditor.commit()).thenReturn(false);
        
        // SharedPreferences가 editor() 메소드 호출 시, 데이터 저장에 실패한 MockEditor가 리턴되는 걸 모킹한다.
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);
        return new SharedPreferencesHelper(mMockBrokenSharedPreferences);
    }

}
