package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"type"})
public class ProductCreatedInsrcEventDTO extends AbstractDTO {
    private String id;
    private PlanCreatedInsrcEventDTO plan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlanCreatedInsrcEventDTO getPlan() {
        return plan;
    }

    public void setPlan(PlanCreatedInsrcEventDTO plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "ProductCreatedInsrcEventDTO{" +
                "id='" + id + '\'' +
                ", plan=" + plan +
                '}';
    }
}
