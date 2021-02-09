package loggingsys.service;

import com.google.gson.Gson;
import loggingsys.data.HeartBeatMsg;
import loggingsys.data.HttpError;
import loggingsys.data.MsgRepository;
import message.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

@Configuration
@EnableScheduling
public class HBService {
    @Autowired
    MsgRepository repo;

    @Value("http://localhost:8080/keys/http_errors?stasasart=20202020312131&end=20202020312135")
    private String url;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    //invio su kafka
    public void sendMessage(String topic,String key ,String msg) {
        kafkaTemplate.send(topic,key,msg);
    }

    @Scheduled(fixedDelay = 10000)
    public void heartbeat() throws UnknownHostException {

        RestTemplate restTemplate = new RestTemplate();
        HeartBeatMsg msg = new HeartBeatMsg();
        msg.setService("loggingsys");
        msg.setServiceStatus("up");

        //Check connessione al db
        if (check()){
            msg.setDbStatus("up");
        }
        else{
            msg.setDbStatus("down");
        }
        System.out.println(new Gson().toJson(msg));

        
        try {
            String answer = restTemplate.postForObject(url, msg, String.class);
            System.out.println(answer);
        }
        catch (HttpStatusCodeException ex) {
            //gestisco le eccezioni dell'errore 5xx
            if (ex.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
                //invio su kafka le informazioni relative all'errore della richiesta http
                error500(ex,"ServerHost");
                //gestisco le eccezioni dell'errore di tipo 4xx
            } else if (ex.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
                //invio su kafka le informazioni relative all'errore della richiesta http
                error400(ex,"ClientHost");
            }
        }
    }

    private void error500(Exception ex,String host) throws UnknownHostException {
        HttpError errorLog = new HttpError();
        errorLog.setTimestamp(System.currentTimeMillis() / 1000L);

        errorLog.setService("loggingsys");
        errorLog.setRequest(host);
        //codifico stacktrace in una stringa
        StackTraceElement[] stack = ex.getStackTrace();
        String exception = "";
        for (StackTraceElement s : stack) {
            exception = exception + s.toString() + "\n\t\t";
        }
        errorLog.setError(exception);
        try {
            //provo ad ottenere l'indirizzo ip di chi sta richiedendo il servizio
            errorLog.setSourceIp(Inet4Address.getLocalHost().getHostAddress());
        } finally {
            errorLog.setSourceIp("Unknown");
            System.out.println("Invio messaggio 500");
            System.out.println(errorLog.toString());
            sendMessage("loggingsys-topic", "http_errors", errorLog.toString());

        }

    }

    private void error400(HttpStatusCodeException ex,String host) throws UnknownHostException {
        HttpError errorLog = new HttpError();
        errorLog.setTimestamp(System.currentTimeMillis() / 1000L);
        errorLog.setService("loggingsys");
        errorLog.setRequest(host);
        errorLog.setError(ex.getStatusCode().toString());
        try {
            errorLog.setSourceIp(Inet4Address.getLocalHost().getHostAddress());
        } finally {
            errorLog.setSourceIp("Unknown");
            System.out.println("Invio messaggio 400");
            System.out.println(errorLog.toString());
            sendMessage("loggingsys-topic", "http_errors", errorLog.toString());
        }

    }

    private boolean check(){
        List<Msg> trovato = repo.findAll();
        if (trovato.size()!=0) {
            return true;
        }
        else return false;


    }
}
