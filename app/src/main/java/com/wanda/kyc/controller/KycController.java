package com.wanda.kyc.controller;

import com.wanda.kyc.dto.kyc.KycApplyDto;
import com.wanda.kyc.dto.user.User;
import com.wanda.kyc.dto.user.UserInfoUpdateDto;
import com.wanda.kyc.response.ResponseModel;
import com.wanda.kyc.service.KycService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/kyc")
@RestController
public class KycController {
    @Autowired
    private KycService kycService;

    @PostMapping("/apply")
    public ResponseModel apply(@RequestBody KycApplyDto dto, User user){
        kycService.apply(dto,user);
        return ResponseModel.success();
    }
    @PostMapping("/update")
    public ResponseModel updateUserInfo(@RequestBody UserInfoUpdateDto dto, User user){
        kycService.updateUserInfo(dto,user);
        return ResponseModel.success();
    }


    @GetMapping("/find")
    public ResponseModel getApply(User user){
        return ResponseModel.success(kycService.getApply(user));
    }
}
