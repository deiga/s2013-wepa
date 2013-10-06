package wad.heavycalc.controller;

import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.heavycalc.domain.CalculationTask;
import wad.heavycalc.service.DataProcessingService;

@Controller
public class DataProcessingController {

    private DataProcessingService processingService;

    @Autowired
    public void setDataProcessingService(DataProcessingService processingService) {
        this.processingService = processingService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public String submitForProcessing(
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute("task") CalculationTask task,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        String taskId = UUID.randomUUID().toString();
        task.setId(taskId);

        processingService.sendForProcessing(task);

        redirectAttributes.addAttribute("taskId", taskId);
        redirectAttributes.addFlashAttribute("message", " sent for processing!");

        return "redirect:/app/{taskId}";
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String showForm(@ModelAttribute("task") CalculationTask task) {
        return "form";
    }

    @RequestMapping(value = "{taskId}", method = RequestMethod.GET)
    public String showStatus(Model model, @PathVariable String taskId) {
        model.addAttribute("task", processingService.getCalculationTask(taskId));
        return "viewtask";
    }
}