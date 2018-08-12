package com.outlier.server.data;

import java.util.ArrayList;
import java.util.List;

public class EndToEndResponse {

    private String message;

    private List<Double> outliers = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Double> getOutliers() {
        return outliers;
    }

    public void setOutliers(List<Double> outliers) {
        this.outliers = outliers;
    }
}
