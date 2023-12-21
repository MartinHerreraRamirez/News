package egg.news.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.news.models.Users;

@Repository
public interface IUsersRepository extends JpaRepository<Users, String>{

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    public Users searchByEmail(@Param("email") String email);    

    @Query("SELECT u FROM Users u WHERE u.phone = :phone")
    public Users searchByPhone(@Param("phone") String phone);
}
