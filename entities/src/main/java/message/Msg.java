package message;

import javax.persistence.*;
@Entity
public class Msg{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String chiave;
    private String value;
    private Long timestamp;




    public void setChiave(String chiave) {
        this.chiave = chiave;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return //"Log: {" +
                //"id=" + id +
                //"chiave=" + chiave +
                ", value ='" + value + "";
                //", timestamp='" + timestamp + '\'' +
                //'}';
    }
}