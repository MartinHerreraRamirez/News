package egg.news.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.news.enums.Role;
import egg.news.models.Journalist;

@Repository
public interface IJournalistRepository extends JpaRepository<Journalist, String>{ 

    @Query("SELECT u FROM Users u WHERE u.role = :role")
    public List<Journalist> findUsersByRol(@Param("role") Role role);

    @Query("SELECT j FROM Journalist j JOIN FETCH j.myNews WHERE j.id = :id")
    public Journalist findJournalistWithNews(@Param("id") String id);

    
    
} 
    

