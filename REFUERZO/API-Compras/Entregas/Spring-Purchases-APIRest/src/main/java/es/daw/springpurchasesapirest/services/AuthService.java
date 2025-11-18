package es.daw.springpurchasesapirest.services;


import es.daw.springpurchasesapirest.dtos.AuthRequest;
import es.daw.springpurchasesapirest.dtos.RegisterRequest;
import es.daw.springpurchasesapirest.entities.Role;
import es.daw.springpurchasesapirest.entities.User;
import es.daw.springpurchasesapirest.exceptions.UserAlreadyExistsException;
import es.daw.springpurchasesapirest.repositories.RoleRepository;
import es.daw.springpurchasesapirest.repositories.UserRepository;
import es.daw.springpurchasesapirest.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.daw.springpurchasesapirest.exceptions.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public Optional<String> logIn(AuthRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );


        return Optional.of(jwtService.generateToken((UserDetails) authentication.getPrincipal()));
    }


    public Boolean usernameAlreadyExists(RegisterRequest request) {
        return userRepository.findByUsername(request.getUsername()).isPresent();
    }


    // tried it because it seemed logical but a bit overcomplicated, and it
    // works!!
    // TODO duplicate roles logic
    public List<String> parseRoles(RegisterRequest request) {
        return Optional.ofNullable(request.getRoles())
                .map(list -> list.stream()
                        .map(String::toUpperCase)
                        .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                        .toList())
                .orElse(List.of("ROLE_USER"));

    }

    public Boolean rolesExists(RegisterRequest request ) {

        return parseRoles(request).stream().allMatch(role -> roleRepository.findByName(role).isPresent());
    }

    public Optional<User> createUser(RegisterRequest request) throws UserAlreadyExistsException {
        if (usernameAlreadyExists(request)) {
            throw new UserAlreadyExistsException((request.getUsername() + " " +
                    "already exists "));
        }

        if(!rolesExists(request)){
            throw new RoleNotFoundException("Roles not found");
        }

        List<String> roleNames = parseRoles(request);
        List<Role> roles = roleNames.stream()
                .map(rolename -> roleRepository.findByName(rolename)
                        .orElseThrow(() -> new RoleNotFoundException("role " +
                                "not found: " + rolename)))
                .toList();


        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        roles.forEach(newUser::addRole);
        //newUser.getRoles().add(role); // vía getter

        return Optional.of(userRepository.save(newUser));
    }
}
