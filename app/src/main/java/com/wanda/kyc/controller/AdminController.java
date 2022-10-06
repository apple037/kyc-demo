package com.wanda.kyc.controller;

import com.wanda.kyc.base.BaseController;
import com.wanda.kyc.constant.UserTypeConst;
import com.wanda.kyc.dto.LoginReqDto;
import com.wanda.kyc.dto.app.UserSearchDto;
import com.wanda.kyc.dto.user.AppUser;
import com.wanda.kyc.dto.user.User;
import com.wanda.kyc.response.ResponseModel;
import com.wanda.kyc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequestMapping("/admin")
@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseModel login(@RequestBody @Valid LoginReqDto reqDto, HttpServletRequest request) {
        return ResponseModel.success(userService.login(reqDto, UserTypeConst.SUPER));
    }

    @PostMapping("/logout")
    public ResponseModel logout(AppUser user) {
        userService.logout(user);
        return ResponseModel.success();
    }

    @GetMapping("/admin-type")
    public ResponseModel getAdminType(User user){
        return ResponseModel.success(userService.getAdminType(user));
    }
}
