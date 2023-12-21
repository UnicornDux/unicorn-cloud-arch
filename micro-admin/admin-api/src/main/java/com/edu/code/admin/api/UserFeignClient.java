package com.edu.code.admin.api;

import com.edu.code.admin.dto.UserAuthDTO;
import com.edu.code.base.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "micro-admin")
public interface UserFeignClient {

    @GetMapping("/api/v1/users/{username}")
    Result<UserAuthDTO> getUserByUsername(@PathVariable String username);


}
