package com.lili.exam.dto;

import java.io.Serializable;
import java.util.Date;

public class ExamPlaceFavor extends ExamPlaceFavorKey implements Serializable {
    private Integer duration;

    private Integer favorIn;

    private Integer favorOut;

    private Integer favorOut2;

    private Date ctime;

    private Date mtime;

    private String extra;

    private static final long serialVersionUID = 1L;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getFavorIn() {
        return favorIn;
    }

    public void setFavorIn(Integer favorIn) {
        this.favorIn = favorIn;
    }

    public Integer getFavorOut() {
        return favorOut;
    }

    public void setFavorOut(Integer favorOut) {
        this.favorOut = favorOut;
    }

    public Integer getFavorOut2() {
        return favorOut2;
    }

    public void setFavorOut2(Integer favorOut2) {
        this.favorOut2 = favorOut2;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", duration=").append(duration);
        sb.append(", favorIn=").append(favorIn);
        sb.append(", favorOut=").append(favorOut);
        sb.append(", favorOut2=").append(favorOut2);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}