package wad.beers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaBeerService implements BeerService {

    @Autowired
    private BeerRepository beerRepository;

    @Override
    @Transactional(readOnly = false)
    public Beer create(Beer beer) {
        beer = beerRepository.save(beer);
        beerRepository.flush();
        return beer;
    }

    @Override
    @Transactional(readOnly = true)
    public Beer read(Long identifier) {
        return beerRepository.findOne(identifier);
    }

    @Override
    @Transactional(readOnly = false)
    public Beer update(Long identifier, Beer beer) {
        delete(identifier);
        beer.setId(identifier);
        return create(beer);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        beerRepository.delete(id);
        beerRepository.flush();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Beer> list() {
        return (List<Beer>) beerRepository.findAll();
    }
}