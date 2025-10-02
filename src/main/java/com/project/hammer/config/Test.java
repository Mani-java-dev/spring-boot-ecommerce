package com.project.hammer.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Test {

    @PostConstruct
    public String fireAndForget(){
        log.info("###############-WORKING-###############");
        log.info("#                                     #");
        log.info("#             ECOM WORKING            #");
        log.info("#                                     #");
        log.info("#######################################");
        return "";
    }

}
