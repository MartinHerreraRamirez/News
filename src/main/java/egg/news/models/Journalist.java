package egg.news.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true) 
public class Journalist extends Users{

    @OneToMany(mappedBy = "journalist", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    private List<News> myNews;   
        
    private String salary;
}

