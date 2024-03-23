package or.grisu.springwebsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class ControladorPersonal {


    @GetMapping("/index")
    public String index(){
        return "hola caracola";
    }

    @GetMapping("/index2")
    public String index2(){
        return "Hola caracola sin seguridad";
    }
}
