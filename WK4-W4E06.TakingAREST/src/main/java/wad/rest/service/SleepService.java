package wad.rest.service;

import java.util.List;
import wad.rest.domain.Sleep;

public interface SleepService {

    Sleep create(Sleep sleep);
    Sleep read(Long id);
    void delete(Long id);
    List<Sleep> list();
}