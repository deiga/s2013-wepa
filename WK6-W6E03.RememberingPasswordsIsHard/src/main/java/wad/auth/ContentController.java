package wad.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @RequestMapping(value = "secret", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAnyRole('user','superuser')")
    public String getContent() {
        return "topsiikrt1";
    }
}
