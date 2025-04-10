package com.cgv.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cgv.app.entity.Sample;
import com.cgv.app.repository.SampleRepository;

@Service
public class SampleService {

	// UserRepository userRepository; << 필드 주입
	// @Autowired << 예전에 필드 주입할 때

	private final SampleRepository sampleRepository; // << 생성자 주입

	public SampleService(SampleRepository sampleRepository) {
		this.sampleRepository = sampleRepository;
	}
	// 기술 구현은 서비스로 구현 => 서비스 로직 구성
	
	// 목록 조회
	// getAllSample()
	// 예) select * from 'sample' => .findAll()
	public List<Sample> getAllSamples() {
		return sampleRepository.findAll(); // List(Sample) sampleList = new Array
	}

	// 샘플 건당 조회(no 이용)
	// getSampleById(Long no)
	// 예) select * from 'sample' where no=1 => .findById(no)
	public Optional<Sample> getSampleByID(Long no) {
		return sampleRepository.findById(no);

	}

	// 샘플 저장(sample 객체로 전달 받아서 저장)
	// saveSample(Sample sample)
	// insert into 'sample' values (default, "주인"); => .save(sample)
	public Sample saveSample(Sample sample) {
		return sampleRepository.save(sample);
	}

	// 샘플 삭제(no 이용)
	// deleteSample(Long no)
	// delete from 'sample' where no=1 => .deleteById(no);
	public void deleteSample(Long no) {
		sampleRepository.deleteById(no);

	}

	//RestApiController의 샘플 상세보기
	public Sample getSampleByNo(Long no) {
		return sampleRepository.findById(no).orElse(null);
	}
	
	// 샘플 수정
	//update sample set name="" where no=1;   => .save(sample)
	public Sample updateSample(Sample sample) {
		return sampleRepository.save(sample);
	}
}
