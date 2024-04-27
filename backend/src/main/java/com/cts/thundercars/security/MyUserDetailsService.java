package com.cts.thundercars.security;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.thundercars.entity.CarDealer;
import com.cts.thundercars.entity.User;
import com.cts.thundercars.repository.CarDealerRepository;
import com.cts.thundercars.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository usersDao;

	@Autowired
	private CarDealerRepository carDealersDao;

//	
//
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<User> user = usersDao.findByEmail(username);
//		List<SimpleGrantedAuthority> authorities = Collections
//				.singletonList(new SimpleGrantedAuthority(user.get().getRole().getRoleName()));
//		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
//	}
		Optional<User> user = usersDao.findByEmail(username);
		Optional<CarDealer> carDealer = carDealersDao.findByEmail(username); // Assuming CarDealer has an email field

		if (user.isPresent()) {
			List<SimpleGrantedAuthority> authorities = Collections
					.singletonList(new SimpleGrantedAuthority(user.get().getRole().getRoleName()));
			return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
					user.get().getPassword(), authorities);
		} else if (carDealer.isPresent()) {
			System.out.println(".................................dsfeae" + carDealer.get().getRole().getRoleName());
			List<SimpleGrantedAuthority> authorities = Collections
					.singletonList(new SimpleGrantedAuthority(carDealer.get().getRole().getRoleName())); // Assuming
																											// CarDealer
																											// has a
																											// role
																											// field
			return new org.springframework.security.core.userdetails.User(carDealer.get().getEmail(),
					carDealer.get().getPassword(), authorities); // Assuming CarDealer has a password field
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
