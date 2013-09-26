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
    public void register(Registration registration) {
        registrations.add(registration);
    }

    @Override
    public boolean hasRegistration(Registration registration) {
        return this.registrations.contains(registration);
    }
}
