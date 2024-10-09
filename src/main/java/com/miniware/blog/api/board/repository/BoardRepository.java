package com.miniware.blog.api.board.repository;

import com.miniware.blog.api.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long boardId);
}
