package egg.news.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egg.news.models.Author;

@Repository
public interface IAuthorRepository extends JpaRepository<Author, String>{ 


    
} 
    

