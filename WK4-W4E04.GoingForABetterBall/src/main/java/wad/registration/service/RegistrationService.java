package wad.registration.service;

import wad.registration.domain.Registration;

public interface RegistrationService {

    void register(Registration registration);
    boolean hasRegistration(Registration registration);
}
