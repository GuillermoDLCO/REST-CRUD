package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springboot.cruddemo.entity.Employee;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

	// Definir campo para entityManager

	private EntityManager entityManager;

	// Configurar constructor de inyeccion
	@Autowired
	public EmployeeDAOHibernateImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public List<Employee> findAll() {

		// Obtener la actual sesion de hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// Crear a query
		Query<Employee> query = currentSession.createQuery("from Employee", Employee.class);

		// Ejecutar el query y obtener result list
		List<Employee> employees = query.getResultList();

		// Retornar los resultados

		return employees;
	}

	@Override
	public Employee findById(int id) {
		
		// Obtener la actual sesion de hibernate
		Session currentSession = entityManager.unwrap(Session.class);

		// Obtener employe
		Employee employee =
				currentSession.get(Employee.class, id);
		
		// Retornar los resultados

		return employee;
	}

	@Override
	public void save(Employee employee) {
		// Obtener la actual sesion de hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		
		// Guardar employee
		currentSession.saveOrUpdate(employee);
		

	}

	@Override
	public void deleteById(int id) {
		
		// Obtener la actual sesion de hibernate
		Session currentSession = entityManager.unwrap(Session.class);
		
		// Borrar objeto con primary key
		Query query = 
				currentSession.createQuery("delete from Employee where id=:employeeId");
		
		query.setParameter("employeeId", id);
		
		query.executeUpdate();
	}

}
