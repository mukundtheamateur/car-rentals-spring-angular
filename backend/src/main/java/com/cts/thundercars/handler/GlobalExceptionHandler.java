package com.cts.thundercars.handler;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cts.thundercars.exceptions.AlreadyExistsException;
import com.cts.thundercars.exceptions.BadRequestException;
import com.cts.thundercars.exceptions.DataAccessException;
import com.cts.thundercars.exceptions.NoUserFoundException;
import com.cts.thundercars.exceptions.NotFoundException;
import com.cts.thundercars.exceptions.RoleIdNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
    	NotFoundException.class,
    	RoleIdNotFoundException.class, 
    	NoUserFoundException.class, 
    	DataAccessException.class
    	})
    public ResponseEntity<Map<String,Object>> handleNotFoundException(Exception e){
    	Map<String, Object> response = new HashMap<>();
    	response.put("statusCode", HttpStatus.NOT_FOUND.value());
    	response.put("message", e.getMessage());
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handleAlreadyExistsException(Exception e){
    	Map<String, Object> response = new HashMap<>();
    	response.put("statusCode", HttpStatus.CONFLICT.value());
    	response.put("message", e.getMessage());
    	return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String,Object>> handleBadRequestException(Exception e){
    	Map<String, Object> response = new HashMap<>();
    	response.put("statusCode", HttpStatus.BAD_REQUEST.value());
    	response.put("message", e.getMessage());
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    
}
