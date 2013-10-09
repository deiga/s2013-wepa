package wad.tiitinenlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ListController {

    @Autowired
    private TiitinenListService tiitinenList;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String getContent(Model model) {
        model.addAttribute("list", tiitinenList.list());
        return "page";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String postContent(@RequestParam String content) {
        tiitinenList.create(content);
        return "redirect:/app/list";
    }

    @RequestMapping(value = "list/{id}", method = RequestMethod.DELETE)
    public String deleteContent(@PathVariable Long id) {
        tiitinenList.remove(id);
        return "redirect:/app/list";
    }
}
