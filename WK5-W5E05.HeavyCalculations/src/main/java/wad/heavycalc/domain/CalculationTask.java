package wad.heavycalc.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class CalculationTask {

    private String id;
    private String status;
    @NotEmpty
    private String data;
    private String result;

    public CalculationTask() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
