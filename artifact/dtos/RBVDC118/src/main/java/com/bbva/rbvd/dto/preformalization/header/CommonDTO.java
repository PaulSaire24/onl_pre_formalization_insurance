package com.bbva.rbvd.dto.preformalization.header;

import com.bbva.apx.dto.AbstractDTO;

public class CommonDTO extends AbstractDTO {
    private String uid;
    private String timestamp;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CommonDTO{" +
                "uid='" + uid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
