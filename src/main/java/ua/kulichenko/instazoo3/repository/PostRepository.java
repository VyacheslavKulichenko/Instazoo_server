package ua.kulichenko.instazoo3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kulichenko.instazoo3.entity.Post;
import ua.kulichenko.instazoo3.entity.Userinsta;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // SELECT * FROM POST as p WHERE User = 'user' SORT DESC
    List<Post> findAllByUserinstaOrderByCreatedDateDesc(Userinsta userinsta); //передаём юзера, найти все посты по юзеру,
    // сортировать по тому когда они былии созданы, Desc - сверху вниз, самый последний пост будет на верху


    List<Post> findAllByOrderByCreatedDateDesc(); //метод возвращающий все посты для всех юзеров из бд

    Optional<Post> findPostByIdAndUserinsta(Long id, Userinsta userinsta);
}
