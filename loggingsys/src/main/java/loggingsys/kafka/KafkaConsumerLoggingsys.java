package loggingsys.kafka;

import com.google.gson.Gson;
import message.Msg;
import loggingsys.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerLoggingsys {

    @Autowired
    MsgRepository repository;


    @KafkaListener(topics = "${kafkaTopic}", groupId = "${kafkaGroup}")

    public void listenLoggingsysTopic(String message, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
        if (message != null) {
            Msg mex = new Msg();
            switch(key){
                case "http_errors":
                    HttpError allMsg = new Gson().fromJson(message, HttpError.class);
                    mex.setTimestamp(allMsg.getTimestamp());
                    break;
                case "order_validation":
                    ProductsMessage productsMsg = new Gson().fromJson(message, ProductsMessage.class);
                    mex.setTimestamp(productsMsg.getTimestamp());
                    break;
                case "order_paid_validation_failure":
                    OrdersMessage ordersMsg = new Gson().fromJson(message, OrdersMessage.class);
                    mex.setTimestamp(System.currentTimeMillis() / 1000L);
                    break;
                case "bad_ipn_error":
                case "received_wrong_business_paypal_payment":
                    PaymentMessage paymentMsg = new Gson().fromJson(message, PaymentMessage.class);
                    mex.setTimestamp(paymentMsg.getTimestamp());
                    break;
                case "invoice_unavailable":
                    InvoicingMessage invoicingMsg = new Gson().fromJson(message, InvoicingMessage.class);
                    mex.setTimestamp(invoicingMsg.getTimestamp());
                    break;
                case "shipping_unavailable":
                    ShippingMessage shippingMsg = new Gson().fromJson(message, ShippingMessage.class);
                    mex.setTimestamp(shippingMsg.getTimestamp());
                    break;
                case "service_down":
                    Fault_DetectorMessage fdMsg = new Gson().fromJson(message, Fault_DetectorMessage.class);
                    mex.setTimestamp(fdMsg.getTime());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + key);
            }
            mex.setChiave(key);
            mex.setValue(message);
            repository.save(mex);
            System.out.println("Salvato sul database il messaggio " + mex + " con chiave " + key);
        }

    }
}