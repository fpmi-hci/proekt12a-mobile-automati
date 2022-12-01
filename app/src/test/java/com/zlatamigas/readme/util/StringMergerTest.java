package com.zlatamigas.readme.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class StringMergerTest {

    @Test
    public void mergeStrings() {

        String expected = "1, 2, 3, 4";

        List<String> strs = Arrays.asList("1", "2", "3", "4");
        String actual = StringMerger.mergeStrings(strs);

        assertEquals(expected, actual);
    }

    @Test
    public void testMergeStrings() {

        String expected = "1.2.3.4";

        List<String> strs = Arrays.asList("1", "2", "3", "4");
        String actual = StringMerger.mergeStrings(strs, ".");

        assertEquals(expected, actual);
    }
}