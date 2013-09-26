package wad.beers;

import java.util.List;

public interface BeerService {
    Beer create(Beer beer);
    Beer read(Long identifier);
    Beer update(Long identifier, Beer beer);
    void delete(Long identifier);

    List<Beer> list();
}