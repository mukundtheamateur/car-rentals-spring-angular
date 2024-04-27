package com.cts.thundercars.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.thundercars.entity.Roles;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.repository.RolesRepository;
import com.cts.thundercars.services.service.RolesServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RolesServicesImpl implements RolesServices{
	
	private final RolesRepository rolesRepository;
	
	@Autowired
	public RolesServicesImpl(RolesRepository rolesRepository) {
		this.rolesRepository = rolesRepository;
	}
	
	@Override
	public List<Roles> getAllRoles() throws NotFoundException {
		log.info("find all roles service started...");
		List<Roles> roles = rolesRepository.findAll();
		if(!roles.isEmpty()) {
			log.info("Fetched roles: {}", roles);
			return roles;
		}
		log.error("Roles can not be fetched");
		throw new NotFoundException("Roles can not be found!");	
	}

	@Override
	public Roles getRolesById(Integer id) throws NotFoundException {
		log.info("fetching role by role id: {}", id);
		Optional<Roles> role= rolesRepository.findById(id);
		if(role.isPresent()) {
			log.info("Role found! with id : {} -> {}", id, role.get().getRoleName());
			return role.get();
		}
		log.error("Role with this id does not exist");
		throw new NotFoundException("Role with this id does not exist");
	}

	@Override
	public Roles saveRole(Roles role) throws AlreadyExistsException{
		log.info("Check if this role exists or not");
		Boolean isExists = rolesRepository.existsById(role.getId());
		if(!isExists) {
			Roles createdRole = rolesRepository.save(role);
			log.info("Role is successfully created");
			return createdRole;
		}
		throw new AlreadyExistsException("this role already exists");
	}

	@Override
	public String deleteRole(Integer id) throws NotFoundException {
		log.info("fetching role by role id: {}", id);
		Optional<Roles> optionalRole= rolesRepository.findById(id);
		if(optionalRole.isPresent()) {
			Roles role = optionalRole.get();
			rolesRepository.delete(role);
			log.info("Role Deleted successfully");
			return "Role Deleted successfully";
		}
		throw new NotFoundException("Role not found");
	}
	
}
