package com.lyacoin.api.seed;


import com.lyacoin.api.exception.AppException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class EnglishWordListTest {

    @Test(expected = AppException.class)
    public void getWord_getLessZero_throwException() {
        EnglishWordList.INSTANCE.getWord(-1);
    }

    @Test(expected = AppException.class)
    public void getWord_getMoreResult_throwException() {
        EnglishWordList.INSTANCE.getWord(100000);
    }

    @Test
    public void getWord_getCorrectResult_returnCorrectResult() {
        String result = EnglishWordList.INSTANCE.getWord(0);
        assertNotNull(result);
    }
}
