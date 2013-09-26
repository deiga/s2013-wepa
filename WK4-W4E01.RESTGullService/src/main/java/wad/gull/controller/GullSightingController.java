package wad.gull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.gull.domain.GullSighting;
import wad.gull.service.GullSightingService;

@Controller
public class GullSightingController {
    
    @Autowired
    GullSightingService gullSightingService;
    
    @RequestMapping(value = "gulls", method = RequestMethod.GET)
    @ResponseBody
    public Iterable<GullSighting> list() {
        return gullSightingService.list();
    }
    
    @RequestMapping(value = "gulls/{tunnus}", method = RequestMethod.GET)
    @ResponseBody
    public GullSighting read(@PathVariable Long tunnus) {
        return gullSightingService.read(tunnus);
    }
    
    @RequestMapping(value = "gulls", method = RequestMethod.POST)
    @ResponseBody
    public GullSighting create(@RequestBody GullSighting sighting) {
        return gullSightingService.create(sighting);
    }
    
    @RequestMapping(value = "gulls/{tunnus}", method = RequestMethod.DELETE)
    @ResponseBody
    public GullSighting delete(@PathVariable Long tunnus) {
        final GullSighting deleted = gullSightingService.read(tunnus);
        gullSightingService.delete(tunnus);
        
        return deleted;
    }

}