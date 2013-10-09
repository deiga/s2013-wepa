package wad.passage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PassageController {

    @RequestMapping("*")
    public String getContent() {
        return "page";
    }
}
