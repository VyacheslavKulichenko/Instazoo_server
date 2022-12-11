package ua.kulichenko.instazoo3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kulichenko.instazoo3.entity.ImageModel;

import java.util.Optional;

@Repository
public interface ImageRepository  extends JpaRepository<ImageModel, Long> {

    Optional<ImageModel> findByUserId(Long userId); //вернуть фото принадлежащую юзеру
    Optional<ImageModel> findByPostId(Long postId); //найдём фото для поста
}
