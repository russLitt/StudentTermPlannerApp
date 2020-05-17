package com.example.termplannerapp.utilities;

import com.example.termplannerapp.database.TermEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleTermData {

    private static final String SAMPLE_TERM_TITLE1 = "Spring Term 2020";
    private static final String SAMPLE_TERM_TITLE2 = "Summer Term 2020";
    private static final String SAMPLE_TERM_TITLE3 = "Fall Term 2020";
    private static final String SAMPLE_DATE_1 = "1/15/2020";
    private static final String SAMPLE_DATE_2 = "5/15/2020";
    private static final String SAMPLE_DATE_3 = "6/1/2020";
    private static final String SAMPLE_DATE_4 = "8/1/2020";


    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public static List<TermEntity> getTerms() {
        List<TermEntity> terms = new ArrayList<>();
        Date date = new Date();
        terms.add(new TermEntity(getDate(0), SAMPLE_TERM_TITLE1, SAMPLE_DATE_1, SAMPLE_DATE_2));
        terms.add(new TermEntity(getDate(-1), SAMPLE_TERM_TITLE2, SAMPLE_DATE_2, SAMPLE_DATE_3));
        terms.add(new TermEntity(getDate(-2), SAMPLE_TERM_TITLE3, SAMPLE_DATE_3, SAMPLE_DATE_4));
        return terms;
    }
}
