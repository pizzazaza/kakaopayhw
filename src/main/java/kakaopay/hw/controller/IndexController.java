package kakaopay.hw.controller;

import kakaopay.hw.domain.FileDomain;
import kakaopay.hw.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    FileService fileService;

    @Autowired
    IndexController(FileService fileService){
        this.fileService = fileService;
    }

    @GetMapping
    public ModelAndView indexPage(ModelAndView modelAndView){
        List<FileDomain> fileList = fileService.getFileList();

        modelAndView.addObject("fileList", fileList);
        modelAndView.setViewName("index");

        return modelAndView;
    }
}
