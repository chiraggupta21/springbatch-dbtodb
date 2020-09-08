package com.example.demo.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demo.model.Employee;
import com.example.demo.model.User;

@Component
public class Processor implements ItemProcessor<User, Employee> {
	
	
	
	public Processor() {
	}

	@Override
	public Employee process(User item) throws Exception {
		System.out.println(item);
		Employee emp=new Employee();
		emp.setId(item.getId());
		emp.setName(item.getName());
		emp.setEmpDep(item.getDepartment());
		return emp;
	}
}
