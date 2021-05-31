package com.offcn.service.impl;

import com.offcn.service.PersonService;
import org.apache.dubbo.config.annotation.Service;

/**
 * @author 13320
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Override
    public String getPerson() {
        return "yes";
    }
}
