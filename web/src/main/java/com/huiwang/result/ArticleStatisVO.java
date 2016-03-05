package com.huiwang.result;

import com.huiwang.vo.ArticleStatis;

public class ArticleStatisVO {

    private ArticleStatis statisData;
    private boolean       praised;
    private boolean       cared;

    public ArticleStatis getStatisData() {
        return statisData;
    }

    public void setStatisData(ArticleStatis statisData) {
        this.statisData = statisData;
    }

    public boolean isPraised() {
        return praised;
    }

    public void setPraised(boolean praised) {
        this.praised = praised;
    }

    public boolean isCared() {
        return cared;
    }

    public void setCared(boolean cared) {
        this.cared = cared;
    }

}
