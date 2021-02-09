package loggingsys.data;

import java.io.Serializable;

public class ProductsMessage implements Serializable {

    private Long timestamp;
    private String status;
    private String orderId;
    private Object extraArgs;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Object getExtraArgs() {
        return extraArgs;
    }

    public void setExtraArgs(Object extraArgs) {
        this.extraArgs = extraArgs;
    }





}