package com.demo.backend.services;

import java.util.List;
import java.util.Map;

import com.demo.backend.dto.TestDTO;

public interface ITestService {
    List<TestDTO> all();

    TestDTO get(Long id);

    List<TestDTO> search(String value);

    TestDTO add(TestDTO dto);

    TestDTO update(TestDTO dto);

    TestDTO patch(Long id, Map<String, Object> fields);

    void delete(Long id);
}
