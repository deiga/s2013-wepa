package wad.strato.controller.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ObservationFormObject {

    @NotNull
    @Min(1)
    private Long observationPointId;
    @NotNull
    @Min(-120)
    @Max(120)
    private Integer celsius;

    public Long getObservationPointId() {
        return observationPointId;
    }

    public void setObservationPointId(Long observationPointId) {
        this.observationPointId = observationPointId;
    }

    public Integer getCelsius() {
        return celsius;
    }

    public void setCelsius(Integer celsius) {
        this.celsius = celsius;
    }
}
