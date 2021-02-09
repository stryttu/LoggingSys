package loggingsys.data;

import java.io.Serializable;

public class ShippingMessage implements Serializable {


    
    private Long timestamp;
    private String orderId;
    private String userId;
    private float amountPaid;
    private Object extraArgs;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Object getExtraArgs() {
        return extraArgs;
    }

    public void setExtraArgs(Object extraArgs) {
        this.extraArgs = extraArgs;
    }






}