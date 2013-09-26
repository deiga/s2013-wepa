package wad.rest.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import wad.rest.domain.Sleep;

public interface SleepController {

    String create(Model model, Sleep sleep, BindingResult result);
    String read(Model model, Long id);
    String delete(Long id);
    String list(Model model);
}