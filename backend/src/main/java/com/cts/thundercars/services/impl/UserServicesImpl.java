package com.cts.thundercars.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.thundercars.entity.Bookings;
import com.cts.thundercars.entity.Roles;
import com.cts.thundercars.entity.User;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NoUserFoundException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.exceptions.RoleIdNotFoundException;
import com.cts.thundercars.repository.BookingsRepository;
import com.cts.thundercars.repository.RolesRepository;
import com.cts.thundercars.repository.UserRepository;
import com.cts.thundercars.services.service.UserServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServicesImpl implements UserServices {
    
	private final UserRepository userRepository;
	private final RolesRepository rolesRepository;
	private final BookingsRepository bookingsRepository;

    @Autowired
    public UserServicesImpl(UserRepository userRepository, RolesRepository rolesRepository, BookingsRepository bookingsRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.bookingsRepository = bookingsRepository;
    }


    @Override
    public List<User> getUsers()  {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) throws NotFoundException {
        log.info("Fetching user with id: {}", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException("No such user exist");
    }

//    @Override
//    public User saveUser(User user) throws AlreadyExistsException {
//    	log.info("Checking if user with email: {} exists or not", user.getEmail());
//        Boolean isExists = userRepository.existsByEmail(user.getEmail());
//        if(isExists) {
//        	log.error("Error occured while saving user: {}", user);
//        	throw new AlreadyExistsException("User already exists with email: " + user.getEmail());
//        }
//        log.info("Saving user: {}", user);
//        Optional<Roles> optionalRole = rolesRepository.findById(user.getRole().getId());
//        user.setRole(optionalRole.get());
//        User savedUser = userRepository.save(user);
//        return savedUser;
//    }
    @Override
    public User saveUser(User user) throws AlreadyExistsException, NotFoundException {
        log.info("Checking if user with email: {} exists or not", user.getEmail());
        Boolean isExists = userRepository.existsByEmail(user.getEmail());
        if(isExists) {
            log.error("Error occured while saving user: {}", user);
            throw new AlreadyExistsException("User already exists with email: " + user.getEmail());
        }
        log.info("Saving user: {}", user);
        if (user.getRole() != null) {
            Optional<Roles> optionalRole = rolesRepository.findById(user.getRole().getId());
            if (optionalRole.isPresent()) {
                user.setRole(optionalRole.get());
            } else {
                log.error("Role not found with id: {}", user.getRole().getId());
                throw new NotFoundException("Role not found with id: " + user.getRole().getId());
            }
        }
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    
    @Transactional
    @Override
    public ResponseEntity<String> deleteUser(Integer id) throws NotFoundException {
        Boolean isExists = userRepository.existsById(id);
        if(isExists) {
            Optional<User> getUser = userRepository.findById(id);
            User user= getUser.get();
            user.setRole(null);
            userRepository.save(user);
            userRepository.delete(user);
            return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
        }
        throw new NotFoundException("User does not exist, so it can't be deleted");
    }




	@Override
	public User getUserByEmail(String email) throws NotFoundException {
		
		log.info("Fetching user with email: {}", email);
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) {
			log.info("User fetched successfully with email: ", email);
			return user.get();
		}
	    throw new NotFoundException("User does not exists with this email");
	}
	
	@Override
	public List<User> getUsersByRoleId(Integer role) throws NoUserFoundException {
	    log.info("Fetching users with role: {}", role);
	    List<User> users = userRepository.findByRoleId(role);
	    if(users.isEmpty()) {
	        throw new NoUserFoundException("Users found = None");
	    }
	    return users;
	}


	@Transactional
	@Override
	public User updateUser(Integer id, User userUpdates) throws NotFoundException, RoleIdNotFoundException {

		log.info("Updating user: {}", userUpdates);
		Optional<User> optionalUser = userRepository.findById(id);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			if(userUpdates.getEmail() != null) {
				user.setEmail(userUpdates.getEmail());
			}
			if(userUpdates.getPhone() != null) {
				user.setPhone(userUpdates.getPhone());
			}
			if(userUpdates.getCity() != null) {
				user.setCity(userUpdates.getCity());
			}
			if(userUpdates.getCreatedBy() != null) {
				user.setCreatedBy(userUpdates.getCreatedBy());
			}
			if(userUpdates.getUpdatedBy() != null) {
				user.setUpdatedBy(userUpdates.getUpdatedBy());
			}
			if(userUpdates.getAvatar() != null) {
				user.setAvatar(userUpdates.getAvatar());
			}
			if(userUpdates.getBio() != null) {
				user.setBio(userUpdates.getBio());
			}
			if(userUpdates.getBlacklisted() != null) {
				user.setBlacklisted(userUpdates.getBlacklisted());
			}
			if(userUpdates.getDateOfBirth() != null) {
				user.setDateOfBirth(userUpdates.getDateOfBirth());
			}
			if(userUpdates.getEnableEmailNotification() != null) {
				user.setEnableEmailNotification(userUpdates.getEnableEmailNotification());
			}
			if(userUpdates.getFullname() != null) {
				user.setFullname(userUpdates.getFullname());
			}
			if(userUpdates.getPassword() != null) {
				user.setPassword(userUpdates.getPassword());
			}
			if(userUpdates.getPayLater() != null) {
				user.setPayLater(userUpdates.getPayLater());
			}
			if(userUpdates.getRefreshToken() != null) {
				user.setRefreshToken(userUpdates.getRefreshToken());
			}
			if(userUpdates.getVerified() != null) {
				user.setVerified(userUpdates.getVerified());
			}
			if(userUpdates.getRole() != null) {
				Optional<Roles> optionalRole = rolesRepository.findById(userUpdates.getRole().getId());
				if(optionalRole.isPresent()) {
					Roles role = optionalRole.get();
					user.setRole(role);
					
				}
				else throw new RoleIdNotFoundException("Error : Role not found!!");
				
			}
			User updatedUser = userRepository.save(user);
			log.info("User updated successfully: {}", updatedUser);
			return updatedUser;
		
		}
		throw new NotFoundException("User not found!");
	}


	@Override
	public List<Bookings> getBookingsByUserId(Integer id) throws NotFoundException {
	    log.info("fetching bookings with user id : {}", id);
	    List<Bookings> bookings = bookingsRepository.findByUserId(id);
	    if(bookings.isEmpty()) {
	        throw new NotFoundException("No bookings found for user id: " + id);
	    }
	    log.info("bookings fetched");
	    return bookings;
	}





}
