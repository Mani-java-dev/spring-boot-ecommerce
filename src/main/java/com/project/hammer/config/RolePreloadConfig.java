package com.project.hammer.config;

import com.project.hammer.entity.Role;
import com.project.hammer.repository.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class RolePreloadConfig {

    @Autowired
    private RoleRepo roleRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void run(){
        List<Role> roles= roleRepo.getAllRoles();
        if(Objects.isNull(roles)){
                roles.addAll(Arrays.asList(Role.builder().role("user").build(),Role.builder().role("admin").build()));
                roleRepo.saveAll(roles);
                log.info("All roles saved successfully");
                return;
        }
        log.info("Roles are already preloaded !");
    }
}
