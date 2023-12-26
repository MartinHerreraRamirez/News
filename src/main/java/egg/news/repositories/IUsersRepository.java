package egg.news.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import egg.news.enums.Rol;
import egg.news.models.Users;

@Repository
public interface IUsersRepository extends JpaRepository<Users, String>{

    @Query("SELECT u FROM Users u WHERE u.email = :email")
    public Users findUserByEmail(@Param("email") String email);    

    @Query("SELECT u FROM Users u WHERE u.phone = :phone")
    public Users findUserByPhone(@Param("phone") String phone);

    @Query("SELECT u FROM Users u WHERE u.rol = :rol")
    public List<Users> findUsersByRol(@Param("rol") Rol rol);
}
