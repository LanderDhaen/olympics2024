package service;

import domain.Spectator;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repository.SpectatorRepository;

import java.util.Collection;
import java.util.Collections;

import util.Role;

@Service
@NoArgsConstructor
public class SpectatorDetailsService implements UserDetailsService {

    @Autowired
    private SpectatorRepository spectatorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Spectator spectator = spectatorRepository.findByUsername(username);

        if (spectator == null) {
            throw new UsernameNotFoundException(username);
        }

        else return new User(spectator.getUsername(), spectator.getPassword(), convertAuthorities(spectator.getRole()));
    }

    private Collection<? extends GrantedAuthority> convertAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toString()));
    }
}
