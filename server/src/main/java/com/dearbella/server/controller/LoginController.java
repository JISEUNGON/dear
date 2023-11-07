package com.dearbella.server.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/login")
@Api(tags = {"로그인 API"})
@RequiredArgsConstructor
public class LoginController {

}
