package egg.news.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egg.news.models.Journalist;

@Repository
public interface IJournalistRepository extends JpaRepository<Journalist, String>{ 


    
} 
    

