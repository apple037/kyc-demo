package com.wanda.kyc.controller;

import com.wanda.kyc.dto.app.UserSearchDto;
import com.wanda.kyc.dto.kyc.KycAuditReq;
import com.wanda.kyc.dto.kyc.KycIdAuditReq;
import com.wanda.kyc.dto.kyc.KycRiskAuditReq;
import com.wanda.kyc.dto.user.User;
import com.wanda.kyc.response.ResponseModel;
import com.wanda.kyc.service.KycService;
import com.wanda.kyc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/admin_kyc")
@RestController
public class AdminKycController {
    @Autowired
    private UserService userService;
    @Autowired
    private KycService kycService;

    @GetMapping("/all")
    public ResponseModel getApplyList(UserSearchDto dto){
        return ResponseModel.success(userService.getApplyList(dto));
    }

    @GetMapping("/table")
    public ResponseModel getApplyTable(Long id){
        return ResponseModel.success(kycService.getTable(id));
    }

    @PostMapping("/id-audit")
    public ResponseModel identityAudit(@RequestBody KycIdAuditReq req, User user){
        kycService.idAudit(req, user);
        return ResponseModel.success();
    }

    @PostMapping("/name-audit")
    public ResponseModel UpdateApplyTable(@RequestBody List<KycAuditReq> reqs, User user){
        kycService.updateTable(reqs,false, user);
        return ResponseModel.success();
    }

    @GetMapping("/reason")
    public ResponseModel getReasonList(String type){
        return ResponseModel.success(kycService.getReasonList(type));
    }

    @PostMapping("/risk-audit")
    public ResponseModel ScoreRisk(@RequestBody List<KycRiskAuditReq> reqList, User user){
        kycService.insertReasonList(reqList,user);
        return ResponseModel.success();
    }

    @GetMapping("/risk-result")
    public ResponseModel getScoreRisk(Long memberId){
        return ResponseModel.success(kycService.getRiskList(memberId));
    }

    @GetMapping("/legal-table")
    public ResponseModel getByLegal(Long memberId){
        return ResponseModel.success(kycService.getTable(memberId));
    }

    /**
     * 修改審核資料備註
     * @param reqs
     * @return
     */
    @PostMapping("/legal-data")
    public ResponseModel UpdateLegalTable(@RequestBody List<KycAuditReq> reqs, User user){
        kycService.updateTable(reqs,true, user);
        return ResponseModel.success();
    }

    /**
     * 送出審核結果
     * @param req
     * @return
     */
    @PostMapping("/legal-audit")
    public ResponseModel legalAudit(@RequestBody KycRiskAuditReq req, User user){
        kycService.insertLegalReasonList(req,user);
        return ResponseModel.success();
    }

    /**
     * 查詢審核結果
     * @param memberId
     * @return
     */
    @GetMapping("/legal-result")
    public ResponseModel legalAuditResult(Long memberId){
            return ResponseModel.success(kycService.getRiskList(memberId));
    }


    /**
     *
     * @param status
     * @return
     */
    @GetMapping("/count")
    public ResponseModel getStatusCount(String status){
        return ResponseModel.success(kycService.getTypeCount(status));
    }
}
