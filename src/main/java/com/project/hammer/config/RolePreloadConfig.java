package com.project.hammer.config;

import com.project.hammer.entity.Role;
import com.project.hammer.repository.RoleRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class RolePreloadConfig implements ApplicationRunner {

    @Autowired
    private RoleRepo roleRepo;

    //for testing purpose only :)
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Role> roles= roleRepo.getAllRoles();
        if(Objects.isNull(roles)){
                Role roleOne=new Role();
                roleOne.setRole("user");
                roles.add(roleOne);
                Role roleTwo=new Role();
                roleTwo.setRole("admin");
                roles.add(roleTwo);
                roleRepo.saveAll(roles);
                log.info("All roles saved successfully");
        }
        log.info("Roles are already preloaded !");
    }
}
