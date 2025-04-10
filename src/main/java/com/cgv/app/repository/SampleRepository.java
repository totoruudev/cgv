package com.cgv.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgv.app.entity.Sample;

public interface SampleRepository extends JpaRepository<Sample, Long>{

}
