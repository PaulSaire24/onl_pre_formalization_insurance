package com.bbva.rbvd.dto.preformalization.event;

import com.bbva.apx.dto.AbstractDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(value = {"type"})
public class PlanCreatedInsrcEventDTO extends AbstractDTO {
    private String id;
    private TotalInstallmentDTO totalInstallment;
    private List<InstallmentPlansCreatedInsrcEvent> installmentPlans;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TotalInstallmentDTO getTotalInstallment() {
        return totalInstallment;
    }

    public void setTotalInstallment(TotalInstallmentDTO totalInstallment) {
        this.totalInstallment = totalInstallment;
    }

    public List<InstallmentPlansCreatedInsrcEvent> getInstallmentPlans() {
        return installmentPlans;
    }

    public void setInstallmentPlans(List<InstallmentPlansCreatedInsrcEvent> installmentPlans) {
        this.installmentPlans = installmentPlans;
    }

    @Override
    public String toString() {
        return "PlanCreatedInsrcEventDTO{" +
                "id='" + id + '\'' +
                ", totalInstallment=" + totalInstallment +
                ", installmentPlans=" + installmentPlans +
                '}';
    }
}
