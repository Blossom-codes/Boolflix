package com.core.boolflix.service.serviceImpl;

import com.core.boolflix.repositories.BoolUsersRepository;
import com.core.boolflix.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final BoolUsersRepository boolUsersRepository;

    @Override
    public UserDetails loadUserByUsername(String identity) throws UsernameNotFoundException {
        return boolUsersRepository.findByUsernameOrEmail(identity, identity).orElseThrow(() -> new UsernameNotFoundException("Sorry, "+ identity + " not found"));
    }
}
