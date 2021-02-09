package loggingsys.api;


import loggingsys.data.ServiceStatus;
import loggingsys.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


import java.util.*;
import message.Msg;



@Controller
public class LoggingsysController {




    @Autowired
    LogService ls;





    //GET 1 /keys/{key}?from=unixTimestampStart&end=unixTimestampEnd
    @RequestMapping(value = "keys/{key}")
    public @ResponseBody List<Msg> getAllByKeyTime(@PathVariable String key, @RequestParam (name="from")String unixTimestampStart, @RequestParam (name="end")String unixTimestampEnd) {
                System.out.println(" da loggingsyscontroller: " + key + " - " + unixTimestampStart + " - " + unixTimestampEnd);
                return ls.getAllByKeyTimestamp(key, unixTimestampStart, unixTimestampEnd);


    }

    //GET 2 /http_errors/services/{service}?from=unixTimestampStart&end=unixTimestampEnd
    @RequestMapping(value = "http_errors/services/{service}")
    public @ResponseBody List<Msg> getErrorByService(@PathVariable String service, @RequestParam (name="from")String unixTimestampStart, @RequestParam (name="end")String unixTimestampEnd) {
        System.out.println(" da loggingsyscontroller: "+service+" - "+unixTimestampStart+" - "+unixTimestampEnd);
        return ls.getAllHttperrByService(service,unixTimestampStart,unixTimestampEnd);
    }

    // GET 3 /uptime/services/{service}?from=unixTimeStampStart&end=unixTimestampEnd
    // risponde con informazioni sull'availability del service {service}. Ovvero,ricerca tutti i messaggi service_down associati al service {service}
    // li considera validi per 30 secondi, li conta, raggruppati per status  e ritorna un json con le availability: (specific_status_count * 30 / deltaTime)
    @RequestMapping(value = "/uptime/services/{service}")
    public @ResponseBody String getHeartService(@PathVariable String service, @RequestParam (name="from")String unixTimestampStart, @RequestParam (name="end")String unixTimestampEnd) {
        System.out.println(" da loggingsyscontroller: "+service+" - "+unixTimestampStart+" - "+unixTimestampEnd);
        ServiceStatus s = ls.getServiceStatus(service, unixTimestampStart, unixTimestampEnd);
        return s.toString();
    }





}
