package loggingsys.data;

import org.springframework.data.repository.CrudRepository;
import message.Msg;
import java.util.List;

public interface MsgRepository extends CrudRepository<Msg, String> {
    List<Msg> findAll();
    List<Msg> findAllByChiave(String chiave);

    //LOG DI QUALSIASI SERVIZIO IN UN RANGE DI TIMESTAMP
    List<Msg> findByChiaveAndTimestampBetween(String chiave, Long fromTimestamp, Long endTimestamp);

    //LOG DI HTTP ERROR RELATIVO AD UN DATO SERVIZIO IN UN RANGE DI TIMESTAMP
    List<Msg> findByChiaveAndValueContainingAndTimestampBetween(String chiave, String servizio, Long fromTimestamp, Long endTimestamp);

    //LOG DI SERVICE DOWN RELATIVO A SERVICE IN UN RANGE DI TIMESTAMP
    List<Msg> findByChiaveAndValueContaining(String chiave, String value);


}
