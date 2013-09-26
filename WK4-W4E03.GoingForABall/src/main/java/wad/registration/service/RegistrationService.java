package wad.registration.service;

public interface RegistrationService {

    void register(String name, String address, String email);
    boolean hasRegistration(String name, String address, String email);
}
