package com.bbva.rbvd.dto.preformalization.header;

import com.bbva.apx.dto.AbstractDTO;

public class TraceDTO extends AbstractDTO {

    private String followsSpanId;
    private String parentSpanId;
    private String traceId;

    public TraceDTO(String followsSpanId, String parentSpanId, String traceId) {
        this.followsSpanId = followsSpanId;
        this.parentSpanId = parentSpanId;
        this.traceId = traceId;
    }

    public String getFollowsSpanId() {
        return followsSpanId;
    }

    public void setFollowsSpanId(String followsSpanId) {
        this.followsSpanId = followsSpanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    @Override
    public String toString() {
        return "TraceDTO{" +
                "followsSpanId='" + followsSpanId + '\'' +
                ", parentSpanId='" + parentSpanId + '\'' +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}
