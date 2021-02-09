package loggingsys.data;


import java.io.Serializable;

public class  Fault_DetectorMessage implements Serializable {


    
    private Long time;
    private String status;
    private String service;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }







}