package loggingsys.service;

import com.google.gson.Gson;
import loggingsys.data.Fault_DetectorMessage;
import loggingsys.data.MsgRepository;
import loggingsys.data.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import message.Msg;
import java.util.List;


@Service
public class LogService {
    @Autowired
    MsgRepository repo;




    //GET 1
    public List<Msg> getAllByKeyTimestamp(String key, String fromTimestamp, String endTimestamp){
        Long from = Long.valueOf(fromTimestamp);
        Long end = Long.valueOf(endTimestamp);
        System.out.println(" da logservice: "+key+" - "+fromTimestamp+" - "+endTimestamp);
        return repo.findByChiaveAndTimestampBetween(key,from,end);

    }
    //GET 2
    public List<Msg> getAllHttperrByService(String service, String fromTimestamp, String endTimestamp){
        Long from = Long.valueOf(fromTimestamp);
        Long end = Long.valueOf(endTimestamp);
        System.out.println(" da logservice: "+service+" - "+fromTimestamp+" - "+endTimestamp);
        return repo.findByChiaveAndValueContainingAndTimestampBetween("http_errors", service,from,end);
    }
    //GET 3
    public ServiceStatus getServiceStatus(String service, String fromTimestamp, String endTimestamp){
        Long from = Long.valueOf(fromTimestamp);
        Long end = Long.valueOf(endTimestamp);
        System.out.println(" da logservice: "+service+" - "+fromTimestamp+" - "+endTimestamp);
        List <Msg> m = repo.findByChiaveAndValueContaining("service_down", service);
        return serviceCount(m, from, end);
    }






    @Transactional
    public ServiceStatus serviceCount(List<Msg> lista, Long from, Long end){
        double serviceDown = 0.0;
        double dbStatus = 0.0;
        double serverUnavailable = 0.0;
        double serviceAvailability;
        double serverAvailability;
        double dbAvailability;
        for (Msg mex : lista) {
            System.out.println("frommm " + from + " enddd " + end);
            Fault_DetectorMessage fdm = new Gson().fromJson(mex.getValue(), Fault_DetectorMessage.class);
            if (fdm.getTime() <= end && fdm.getTime() >= from) {
                switch (fdm.getStatus()) {
                    case "serviceDown":
                        serviceDown++;
                        System.out.println("Service down ne abbiamo? " + serviceDown);
                        break;
                    case "dbStatus":
                        dbStatus++;
                        System.out.println("dbstatus ne abbiamo? " + dbStatus);
                        break;
                    case "serverUnavailable":
                        serverUnavailable++;
                        System.out.println("serverUnavailable ne abbiamo? " + serverUnavailable);
                        break;
                }
            }
        }


        serviceAvailability = (1.0-serviceDown*30.0/86400.0);
        serverAvailability = (1.0-serverUnavailable*30.0/86400.0);
        dbAvailability = (1.0-dbStatus*30.0/86400.0);
        return new ServiceStatus(serviceAvailability,serverAvailability,dbAvailability);
    }


}