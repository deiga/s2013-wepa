package wad.flight;

import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.data.repository.CrudRepository;

// tulipahan tehtyä.
public class RepositoryTest {

    private final String AIRCRAFT_REPOSITORY_CLASSNAME = "wad.flight.repository.AircraftRepository";
    private final String AIRPORT_REPOSITORY_CLASSNAME = "wad.flight.repository.AirportRepository";
    private final String AIRCRAFT_CLASSNAME = "wad.flight.domain.Aircraft";
    private final String AIRPORT_CLASSNAME = "wad.flight.domain.Airport";

    @Test
    public void exists() {
        Reflex.reflect(AIRCRAFT_REPOSITORY_CLASSNAME);
        Reflex.reflect(AIRPORT_REPOSITORY_CLASSNAME);
    }

    @Test
    public void isAnInterface() {
        exists();
        assertTrue("Class " + AIRCRAFT_REPOSITORY_CLASSNAME + " should be an interface.", Modifier.isInterface(Reflex.reflect(AIRCRAFT_REPOSITORY_CLASSNAME).cls().getModifiers()));
        assertTrue("Class " + AIRPORT_REPOSITORY_CLASSNAME + " should be an interface.", Modifier.isInterface(Reflex.reflect(AIRPORT_REPOSITORY_CLASSNAME).cls().getModifiers()));
    }

    @Test
    public void extendsCrudRepository() {
        isAnInterface();
        assertTrue("Class " + AIRCRAFT_REPOSITORY_CLASSNAME + " should extend interface " + CrudRepository.class.getName() + ".", CrudRepository.class.isAssignableFrom(Reflex.reflect(AIRCRAFT_REPOSITORY_CLASSNAME).cls()));
        assertTrue("Class " + AIRPORT_REPOSITORY_CLASSNAME + " should extend interface " + CrudRepository.class.getName() + ".", CrudRepository.class.isAssignableFrom(Reflex.reflect(AIRPORT_REPOSITORY_CLASSNAME).cls()));
    }

    @Test
    public void extendsCrudRepositoryWithCorrectTypeParameters() {
        extendsCrudRepository();

        extendsCrudRepositoryWithCorrectTypeParameters(AIRCRAFT_REPOSITORY_CLASSNAME, AIRCRAFT_CLASSNAME);
        extendsCrudRepositoryWithCorrectTypeParameters(AIRPORT_REPOSITORY_CLASSNAME, AIRPORT_CLASSNAME);
    }

//    @Test
//    public void aircraftRepositoryHasMethodForFindAircraftsWithNoAirport() {
//        extendsCrudRepository();
//        try {
//            Method method = Reflex.reflect(AIRCRAFT_REPOSITORY_CLASSNAME).method("findByAirportIsNull").returning(List.class).takingNoParams().getMethod();
//        } catch (Throwable t) {
//            fail("Interface " + AIRCRAFT_REPOSITORY_CLASSNAME + " must contain a method declaration \"List<Aircraft> findByAirportIsNull()\".");
//        }
//    }

    private void extendsCrudRepositoryWithCorrectTypeParameters(String clazzName, String typeOneClass) {
        Class clazz = Reflex.reflect(clazzName).cls();
        Type[] typeArguments = null;
        try {
            Type[] types = clazz.getGenericInterfaces();
            ParameterizedType type = (ParameterizedType) types[0];
            typeArguments = type.getActualTypeArguments();
        } catch (Exception e) {
            fail("When class " + clazzName + " extends CrudRepository, it should provide classes " + typeOneClass + " and " + Long.class.getName() + " as type parameters.");
        }

        assertTrue("When class " + clazzName + " extends CrudRepository, it should provide classes " + typeOneClass + " and " + Long.class.getName() + " as type parameters.", typeArguments[0].equals(Reflex.reflect(typeOneClass).cls()));
        assertTrue("When class " + clazzName + " extends CrudRepository, it should provide classes " + typeOneClass + " and " + Long.class.getName() + " as type parameters.", typeArguments[1].equals(Long.class));
    }
}
