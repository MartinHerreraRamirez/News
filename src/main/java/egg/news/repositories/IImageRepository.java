package egg.news.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import egg.news.models.Image;

@Repository
public interface IImageRepository extends JpaRepository<Image, String>{
    
}
