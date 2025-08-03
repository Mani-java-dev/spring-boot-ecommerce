package com.project.hammer;

import com.project.hammer.model.LoginModel;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class HammerApplication {

	public static void main(String[] args) {

		SpringApplication.run(HammerApplication.class, args);

	}

}
