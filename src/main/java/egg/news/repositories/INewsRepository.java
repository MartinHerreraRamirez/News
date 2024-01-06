package egg.news.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.news.models.News;

@Repository
public interface INewsRepository extends JpaRepository<News, String>{

    @Query("SELECT n FROM News n WHERE n.title = :title")
    public News findNewsByTitle(@Param("title") String title);

    @Query("SELECT n FROM News n WHERE n.journalist.name = :name")
    public List<News> findNewsByJournalist(@Param("name") String name); 
    
    @Query("SELECT n FROM News n WHERE n.journalist.id = :id")
    public List<News> findNewsByJournalistId(@Param("id") String id);
} 
