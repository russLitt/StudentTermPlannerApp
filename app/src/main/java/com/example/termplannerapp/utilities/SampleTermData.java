package com.example.termplannerapp.utilities;

import com.example.termplannerapp.database.TermEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleTermData {

    private static final String SAMPLE_TERM_TEXT1 = "Some simple text for the term";
    private static final String SAMPLE_TERM_TEXT2 = "Some simple text \n for the term";
    private static final String SAMPLE_TERM_TEXT3 = "Some simple text for the term, Some simple text for the term," +
            "Some simple text for the term, Some simple text for the term, Some simple text for the term" +
            "Some simple text for the term, Some simple text for the term, Some simple text for the term" +
            "Some simple text for the term, Some simple text for the term, \n\n Some simple text for the term," +
            "Some simple text for the term, Some simple text for the term, Some simple text for the term";
    private static final String SAMPLE_DATE_1 = "2001-04-11";
    private static final String SAMPLE_DATE_2= "2021-07-01";
    private static final String SAMPLE_DATE_3 = "1994-12-31";
    private static final String SAMPLE_DATE_4 = "1931-10-22";


    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

//    public static List<TermEntity> getTerms() {
//        List<TermEntity> terms = new ArrayList<>();
//        Date date = new Date();
//        terms.add(new TermEntity(getDate(0), SAMPLE_TERM_TEXT1, SAMPLE_DATE_1, SAMPLE_DATE_2));
//        terms.add(new TermEntity(getDate(-1), SAMPLE_TERM_TEXT2, SAMPLE_DATE_2, SAMPLE_DATE_3));
//        terms.add(new TermEntity(getDate(-2), SAMPLE_TERM_TEXT3, SAMPLE_DATE_3, SAMPLE_DATE_4));
//        return terms;
//    }
}
