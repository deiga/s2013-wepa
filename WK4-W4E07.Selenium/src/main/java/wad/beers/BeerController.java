package wad.beers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BeerController {

    @Autowired
    private BeerService beerService;

    @RequestMapping(value = "beers", method = RequestMethod.GET)
    public String getBeers(Model model) {
        model.addAttribute("beers", beerService.list());
        return "beers";
    }

    @RequestMapping(value = "beers", method = RequestMethod.POST)
    public String postBeer(RedirectAttributes redirectAttributes, @ModelAttribute Beer beer) {
        beer = beerService.create(beer);
        redirectAttributes.addAttribute("beerId", beer.getId());
        return "redirect:/app/beers/{beerId}";
    }

    @RequestMapping(value = "beers/{beerId}", method = RequestMethod.GET)
    public String getBeer(Model model, @PathVariable Long beerId) {
        model.addAttribute("beer", beerService.read(beerId));
        return "beers";
    }

    @RequestMapping(value = "beers/{beerId}", method = RequestMethod.DELETE)
    public String deleteBeer(@PathVariable Long beerId) {
        beerService.delete(beerId);
        return "redirect:/app/beers";
    }
}
