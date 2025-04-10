package com.cgv.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgv.app.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
