package egg.news.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
    private String lastName;
    private String email;
    private String password;
    private String phone;

    @OneToOne
    private Image image;

    @Enumerated(EnumType.STRING)
    private Rol rol; 
    
}
