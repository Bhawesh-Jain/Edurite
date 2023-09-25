package com.arihant.edurite.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TermsModel {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("terms_condition")
    @Expose
    private TermsCondition termsCondition;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TermsCondition getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(TermsCondition termsCondition) {
        this.termsCondition = termsCondition;
    }
    public class TermsCondition {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("term_condition_text")
        @Expose
        private String termConditionText;
        @SerializedName("created_date")
        @Expose
        private String createdDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTermConditionText() {
            return termConditionText;
        }

        public void setTermConditionText(String termConditionText) {
            this.termConditionText = termConditionText;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

    }

}
