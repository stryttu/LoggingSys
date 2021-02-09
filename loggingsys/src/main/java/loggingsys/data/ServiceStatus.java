package loggingsys.data;

public class ServiceStatus {
    private double serviceAvailability;
    private double serverAvailability;
    private double dbAvailability;

    public ServiceStatus(double serviceStatus, double serverStatus, double dbStatus) {
        this.serviceAvailability = serviceStatus;
        this.serverAvailability = serverStatus;
        this.dbAvailability = dbStatus;
    }

    

    @Override
    public String toString() {
        return "ServiceStatus{" +
                "serviceAvailability=" + serviceAvailability +
                ", serverAvailability=" + serverAvailability +
                ", dbAvailability=" + dbAvailability +
                '}';
    }
}