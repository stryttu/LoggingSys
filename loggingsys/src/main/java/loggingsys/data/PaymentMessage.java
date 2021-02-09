package loggingsys.data;

import java.io.Serializable;

public class PaymentMessage implements Serializable {

    private Long timestamp;
    private Object body;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }







}