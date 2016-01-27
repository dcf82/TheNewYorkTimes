package com.nytimes.news.beans;

import com.nytimes.news.greendao.New;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erick Flores
 */
public class Wrapper {

    private long num_results = 0;
    private List<New> results = new ArrayList<>();

    public long getNumResults() {
        return num_results;
    }

    public void setNumResults(long num_results) {
        this.num_results = num_results;
    }

    public List<New> getResults() {
        return results;
    }

    public void setResults(List<New> results) {
        this.results = results;
    }

}
