package com.wanda.kyc.controller;

import com.wanda.kyc.base.BaseController;
import com.wanda.kyc.constant.UserTypeConst;
import com.wanda.kyc.dto.LoginReqDto;
import com.wanda.kyc.dto.RegisterDto;
import com.wanda.kyc.dto.user.AppUser;
import com.wanda.kyc.dto.user.CheckCodeDto;
import com.wanda.kyc.dto.user.User;
import com.wanda.kyc.response.ResponseModel;
import com.wanda.kyc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/*
 * 使用者相關
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseModel login(@RequestBody @Valid LoginReqDto reqDto, HttpServletRequest request) {
        return ResponseModel.success(userService.login(reqDto, UserTypeConst.NORMAL));
    }

    @PostMapping("/logout")
    public ResponseModel logout(AppUser user) {
        userService.logout(user);
        return ResponseModel.success();
    }

    @PostMapping("/register")
    public ResponseModel register(@RequestBody @Validated(RegisterDto.Register.class) RegisterDto reqDto) {
        userService.register(reqDto);
        return ResponseModel.success();
    }

    @GetMapping("/info")
    public ResponseModel info(User user) {
        return ResponseModel.success(userService.getUserInfo(user));
    }

    @PostMapping("/update")
    public ResponseModel update(@RequestBody RegisterDto reqDto, User user) {
        reqDto.setUserId(user.getUserId());
        userService.updateUserInfo(reqDto);
        return ResponseModel.success();
    }

    /**
     * Register response model.
     *
     * @param action  the action
     * @param account the account
     * @param lang    the lang
     * @return the response model
     */
    @GetMapping("/code")
    public ResponseModel getCode(
            @RequestParam @NotBlank String action,
            @RequestParam @NotBlank String account,
            @RequestParam @NotBlank String lang) {

        userService.sendCode(action,account, lang);
        return ResponseModel.success();
    }

    @PostMapping("/code")
    public ResponseModel checkVerifyCode(@RequestBody @Valid CheckCodeDto reqDto) {
        return ResponseModel.success(userService.checkVerifyCode(reqDto));
    }
}
