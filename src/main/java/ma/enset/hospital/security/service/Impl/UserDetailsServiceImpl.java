package ma.enset.hospital.security.service.Impl;

import lombok.AllArgsConstructor;
import ma.enset.hospital.security.entities.AppRole;
import ma.enset.hospital.security.service.AccountService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var appUser = accountService.loadUserByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return User.
                withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(
                        appUser.getRoles().stream().map(
                                AppRole::getRole
                        ).toArray(String[]::new)
                )
                .build();
    }
}
