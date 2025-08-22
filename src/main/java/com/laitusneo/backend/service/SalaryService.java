package com.laitusneo.backend.service;

import com.laitusneo.backend.entity.Salary;
import com.laitusneo.backend.repository.SalaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService {

    private final SalaryRepository salaryRepository;

    public SalaryService(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    public Salary createSalary(Salary salary) {
        // calculate netSalary manually before saving
//        salary.setNetSalary(salary.getBaseSalary() + salary.getAllowances() - salary.getDeductions());
        return salaryRepository.save(salary);
    }

    public List<Salary> getAllSalaries() {
        return salaryRepository.findAll();
    }

    public Salary getSalaryById(Long id) {
        return salaryRepository.findById(id).orElse(null);
    }

    public List<Salary> getSalariesByEmployee(Long employeeId) {
        return salaryRepository.findByEmployeeId(employeeId);
    }

    public List<Salary> getActiveSalaries() {
        return salaryRepository.findByIsActiveTrue();
    }

    public Salary updateSalary(Salary salary) {
        salary.setNetSalary(salary.getBaseSalary() + salary.getAllowances() - salary.getDeductions());
        return salaryRepository.save(salary);
    }

    public void deleteSalary(Long id) {
        salaryRepository.deleteById(id);
    }
}
