package com.bbva.rbvd.dto.preformalization.header;

import com.bbva.apx.dto.AbstractDTO;

public class HeaderDTO extends AbstractDTO {
    private EventDTO event;
    private FlagDTO flag;
    private OriginDTO origin;
    private ResultDTO result;
    private TraceDTO traces;
    private String version;
    public HeaderDTO(EventDTO event, FlagDTO flag, OriginDTO origin, ResultDTO result, TraceDTO traces, String version) {
        this.event = event;
        this.flag = flag;
        this.origin = origin;
        this.result = result;
        this.traces = traces;
        this.version = version;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public FlagDTO getFlag() {
        return flag;
    }

    public void setFlag(FlagDTO flag) {
        this.flag = flag;
    }

    public OriginDTO getOrigin() {
        return origin;
    }

    public void setOrigin(OriginDTO origin) {
        this.origin = origin;
    }

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public TraceDTO getTraces() {
        return traces;
    }

    public void setTraces(TraceDTO traces) {
        this.traces = traces;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "HeaderDTO{" +
                "event=" + event +
                ", flag=" + flag +
                ", origin=" + origin +
                ", result=" + result +
                ", traces=" + traces +
                ", version='" + version + '\'' +
                '}';
    }
}
