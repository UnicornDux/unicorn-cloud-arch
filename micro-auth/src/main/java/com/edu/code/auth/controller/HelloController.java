package com.edu.code.auth.controller;

import com.edu.code.base.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {


    @RequestMapping("")
    public Result<String> hello(@RequestBody Map<String, String> hello) {
        return Result.ok("hello "  + hello.get("name") + "!");
    }

}

