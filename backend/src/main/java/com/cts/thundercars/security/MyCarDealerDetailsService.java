//package com.cts.thundercars.security;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.cts.thundercars.entity.CarDealer;
//import com.cts.thundercars.repository.CarDealerRepository;
//
//@Service
//public class MyCarDealerDetailsService implements UserDetailsService{
//
//	@Autowired
//	private CarDealerRepository carDealersDao;
//	
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<CarDealer> user = carDealersDao.findByEmail(username);
//		List<SimpleGrantedAuthority> authorities = Collections
//				.singletonList(new SimpleGrantedAuthority(user.get().getRole().getRoleName()));
//		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
//	}
//}
