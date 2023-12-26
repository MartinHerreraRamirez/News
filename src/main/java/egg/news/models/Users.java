package egg.news.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import egg.news.enums.Rol;
import lombok.Data;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private Boolean isActive;

    @Temporal(TemporalType.DATE)
    private Date highDate;

    @OneToOne
    private Image image;

    @Enumerated(EnumType.STRING)
    private Rol rol; 
    
}
