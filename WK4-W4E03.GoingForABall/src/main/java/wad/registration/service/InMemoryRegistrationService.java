package wad.registration.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Service;
import wad.registration.domain.Registration;

@Service
public class InMemoryRegistrationService implements RegistrationService {

    private Set<Registration> registrations;

    public InMemoryRegistrationService() {
        this.registrations = new HashSet<Registration>();
    }

    @Override
    public void register(String name, String address, String email) {
        registrations.add(new Registration(name, address, email));
    }

    @Override
    public boolean hasRegistration(String name, String address, String email) {
        return registrations.contains(new Registration(name, address, email));
    }
}
