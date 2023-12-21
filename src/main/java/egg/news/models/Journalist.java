package egg.news.models;

import java.util.ArrayList;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true) 
public class Journalist extends Users{

    private ArrayList<News> myNews;        
    private Integer salary;

}
