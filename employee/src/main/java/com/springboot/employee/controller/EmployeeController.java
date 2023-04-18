package com.springboot.employee.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.employee.exception.ResourceNotFoundException;
import com.springboot.employee.model.Employee;
import com.springboot.employee.service.EmployeeService;

@CrossOrigin("*")
@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService empService;
	
	@GetMapping("/")
	@ResponseBody
	public String sayHello() {
		return "Welcome User!...";
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/employees")
	public List<Employee> getAllEmployees() {
		return empService.getAllEmployees();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/employees/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Integer id)  throws ResourceNotFoundException{
		 Employee employee = empService.getEmployee(id)
	    .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
	    return ResponseEntity.ok().body(employee);

	}
	
	@RequestMapping(method=RequestMethod.POST,value="/employees")
	public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		empService.addEmployee(employee);
		map.put("status", 1);
		map.put("message", "Record Saved Successfully!");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/employees/{id}")
	public void updateEmployee(@RequestBody Employee employee,@PathVariable  Integer id)  throws ResourceNotFoundException {
		empService.updateEmployee(id,employee);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/employees/{id}") 
	public void deleteEmployee(@PathVariable  Integer id){
			empService.deleteEmployee(id);

	}
	
	
}

