package com.geosemantica.articleservice.dal.repositories;

import com.geosemantica.articleservice.dal.model.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("""
            from Article a
            where a.id = :id
            and a.isRemoved = false
            """)
    Optional<Article> findById(Long id);

    @Query("""
            from Article a
            where a.isRemoved = false
            order by a.createdAt desc
            """)
    List<Article> findAllSorted(Pageable pageable);
}
