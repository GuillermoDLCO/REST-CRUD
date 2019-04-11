package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO{

	private EntityManager entityManager;
	
	@Autowired
	public EmployeeDAOJpaImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public List<Employee> findAll() {
		
		//Crear query
		Query query = entityManager.createQuery("from Employee");
		
		// Ejecutar query y obtener result list
		List<Employee> employees = query.getResultList();
		
		// Retornar resultados
		return employees;
	}

	@Override
	public Employee findById(int id) {
		// Obtener employee
		Employee employee=
				entityManager.find(Employee.class, id);
		
		// Retornar employee
		return employee;
	}

	@Override
	public void save(Employee employee) {

		// save or update el employee
		Employee dbEmployee = entityManager.merge(employee);
		
		// update with id desde db.. para cuando obtenemos un id generado por save / update
		employee.setId(dbEmployee.getId());
	}

	@Override
	public void deleteById(int id) {
		
		//Borrar objeto con pk
		Query query = entityManager.createQuery(
				"delete from Employee where id=:employeeId");
		query.setParameter("employeeId", id);
		
		query.executeUpdate();
		
	}

}
