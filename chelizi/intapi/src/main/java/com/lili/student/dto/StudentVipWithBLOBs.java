package com.lili.student.dto;

import java.io.Serializable;

public class StudentVipWithBLOBs extends StudentVip implements Serializable {
    private String vipProtocol;

    private String vipPrivilege;

    private static final long serialVersionUID = 1L;

    public String getVipProtocol() {
        return vipProtocol;
    }

    public void setVipProtocol(String vipProtocol) {
        this.vipProtocol = vipProtocol == null ? null : vipProtocol.trim();
    }

    public String getVipPrivilege() {
        return vipPrivilege;
    }

    public void setVipPrivilege(String vipPrivilege) {
        this.vipPrivilege = vipPrivilege == null ? null : vipPrivilege.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", vipProtocol=").append(vipProtocol);
        sb.append(", vipPrivilege=").append(vipPrivilege);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}