package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	private EmployeeService employeeService;

	// Quick and dirty: inject employee dao usando constructor injection
	@Autowired
	public EmployeeRestController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// Expose "/employees" y retornar lista de employees
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

	// Add Mapping for GET /employees/{employeeId}

	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		Employee employee = employeeService.findById(employeeId);

		if (employee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		return employee;
	}

	// Agregar mapping para POST /employees - agragar nuevo employee

	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee employee) {
		// Solo en caso que pase un id in JSON, con el id en 0
		// este es forzado a guardar un nuevo item, en lugar de update

		employee.setId(0);

		employeeService.save(employee);

		return employee;
	}

	// Agregar mapping para PUT /employees - update employee
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee employee) {

		employeeService.save(employee);

		return employee;
	}

	// Agregar mapping para DELETE /employees/{employeeID} - delete employee
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {

		Employee tempEmployee = employeeService.findById(employeeId);
		
		// throw exception if null
		
		if(tempEmployee==null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "Deleted employee id - " + employeeId;

	}
}
