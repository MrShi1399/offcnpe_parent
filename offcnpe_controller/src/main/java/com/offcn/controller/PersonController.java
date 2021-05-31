package com.offcn.controller;

import com.offcn.service.PersonService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 仅作环境测试
 * @author 13320
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    @Reference
    private PersonService personService;

    @RequestMapping("/get")
    public String getPerson(){
        String person = personService.getPerson();
        return person;
    }

}
