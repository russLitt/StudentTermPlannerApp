package com.example.termplannerapp.utilities;

import com.example.termplannerapp.model.TermEntity;

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

    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }

    public static List<TermEntity> getTerms() {
        List<TermEntity> terms = new ArrayList<>();
        terms.add(new TermEntity(1, getDate(0), SAMPLE_TERM_TEXT1));
        terms.add(new TermEntity(2, getDate(-1), SAMPLE_TERM_TEXT2));
        terms.add(new TermEntity(3, getDate(-2), SAMPLE_TERM_TEXT3));
        return terms;
    }
}
