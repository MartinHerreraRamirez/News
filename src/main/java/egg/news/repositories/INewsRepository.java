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
    public News findByTitle(@Param("title") String title);

    @Query("SELECT n FROM News n WHERE n.Journalist.name = :name")
    public List<News> findByJournalist(@Param("name") String name);    
} 
