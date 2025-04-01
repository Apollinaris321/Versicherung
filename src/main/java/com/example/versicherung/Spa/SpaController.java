package com.example.versicherung.Spa;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SpaController {
     @RequestMapping(value="/" , method= RequestMethod.GET)
     public String forwardRoot() {
         System.out.println(">>> SpaController handling root / <<<");
         return "index";
     }
}
