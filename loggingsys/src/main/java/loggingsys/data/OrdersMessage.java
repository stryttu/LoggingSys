package loggingsys.data;

import java.io.Serializable;

public class OrdersMessage implements Serializable {

    private String orderId;
    private String userId;
    private String amountPaid;
    private Object extraArgs;

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

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Object getExtraArgs() {
        return extraArgs;
    }

    public void setExtraArgs(Object extraArgs) {
        this.extraArgs = extraArgs;
    }





}