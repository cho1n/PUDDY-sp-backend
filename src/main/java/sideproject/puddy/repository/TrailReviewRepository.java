package sideproject.puddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.puddy.model.Trail;
import sideproject.puddy.model.TrailReview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface TrailReviewRepository extends JpaRepository<TrailReview, Long>{
    Page<TrailReview> findAllByTrail(Trail trail, Pageable pageable);
}
