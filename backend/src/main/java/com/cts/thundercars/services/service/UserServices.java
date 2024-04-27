package com.cts.thundercars.services.service;

import com.cts.thundercars.entity.Bookings;
import com.cts.thundercars.entity.User;
import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.NoUserFoundException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.exceptions.RoleIdNotFoundException;

import java.util.List;
import org.springframework.http.ResponseEntity;

public interface UserServices {
	
    List<User> getUsers();
    User getUserById(Integer id) throws NotFoundException;
    User saveUser(User user) throws AlreadyExistsException, NotFoundException;
    ResponseEntity<String> deleteUser(Integer id) throws NotFoundException;
    User getUserByEmail(String email) throws NotFoundException;
    List<User> getUsersByRoleId(Integer role) throws NoUserFoundException;
	User updateUser(Integer id, User userUpdates)throws NotFoundException, RoleIdNotFoundException;
	List<Bookings> getBookingsByUserId(Integer id) throws NotFoundException;

}
