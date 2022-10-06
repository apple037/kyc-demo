package com.wanda.kyc.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wanda.kyc.constant.AdminTypeConst;
import com.wanda.kyc.constant.AuditStatusConst;
import com.wanda.kyc.constant.NameAuditTypeEnum;
import com.wanda.kyc.dto.*;
import com.wanda.kyc.dto.admin.UserAdmin;
import com.wanda.kyc.dto.kyc.*;
import com.wanda.kyc.dto.user.AdminUser;
import com.wanda.kyc.dto.user.User;
import com.wanda.kyc.dto.user.UserApp;
import com.wanda.kyc.dto.user.UserInfoUpdateDto;
import com.wanda.kyc.exception.SystemRuntimeException;
import com.wanda.kyc.exception.enumeration.KycExceptionEnum;
import com.wanda.kyc.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class KycService {
    @Autowired
    private KycApplyMapper kycApplyMapper;
    @Autowired
    private KycAuditInfoMapper kycAuditInfoMapper;
    @Autowired
    private KycReasonTypeMapper kycReasonTypeMapper;
    @Autowired
    private KycReasonSubtypeMapper kycReasonSubtypeMapper;
    @Autowired
    private KycAuditReasonMapper kycAuditReasonMapper;
    @Autowired
    private KycAuditRiskMapper kycAuditRiskMapper;
    @Autowired
    private UserAppMapper userAppMapper;
    @Autowired
    private UtilImageMapper utilImageMapper;
    @Autowired
    private UserAdminMapper userAdminMapper;

    @Transactional
    public void updateUserInfo(UserInfoUpdateDto dto, User user){
        dto.setMemberId(Long.parseLong(user.getUserId()));
        KycApply apply = kycApplyMapper.findByMemberId2(dto.getMemberId());
        if(null == apply) {
            KycApply kycApply = KycApply.builder()
                    .realName(dto.getRealName())
                    .memberId(dto.getMemberId())
                    .identityCode(dto.getIdentityCode())
                    .birthdate(dto.getBirthdate())
                    .nationality(dto.getNationality())
                    .job(dto.getJob())
                    .title(dto.getTitle())
                    .isMultiNationality(dto.getIsMultiNationality())
                    .status(AuditStatusConst.NORMAL)
                    .phone(dto.getPhone())
                    .address(dto.getAddress())
                    .purpose(dto.getPurpose())
                    .build();
            if (dto.getIsMultiNationality()){
                kycApply.setMultiOther(dto.getMultiOther());
            }
            if (dto.getTitle().equals("其他")){
                kycApply.setTitleOther(dto.getTitleOther());
            }
            if (dto.getJob().equals("其他")){
                kycApply.setJobOther(dto.getJobOther());
            }
            if (dto.getPurpose().equals("其他")){
                kycApply.setPurposeOther(dto.getPurposeOther());
            }
            kycApplyMapper.insert(kycApply);
        }
        else{
            apply.setRealName(dto.getRealName());
            apply.setIdentityCode(dto.getIdentityCode());
            apply.setBirthdate(dto.getBirthdate());
            apply.setNationality(dto.getNationality());
            apply.setJob(dto.getJob());
            apply.setTitle(dto.getTitle());
            apply.setIsMultiNationality(dto.getIsMultiNationality());
            apply.setStatus(AuditStatusConst.NORMAL);
            apply.setPhone(dto.getPhone());
            apply.setAddress(dto.getAddress());
            apply.setPurpose(dto.getPurpose());
            if (dto.getIsMultiNationality()){
                apply.setMultiOther(dto.getMultiOther());
            }
            if (dto.getTitle().equals("其他")){
                apply.setTitleOther(dto.getTitleOther());
            }
            if (dto.getJob().equals("其他")){
                apply.setJobOther(dto.getJobOther());
            }
            if (dto.getPurpose().equals("其他")){
                apply.setPurposeOther(dto.getPurposeOther());
            }
            kycApplyMapper.updateById(apply);
        }
    }


    @Transactional
    public void apply(KycApplyDto dto,User user){
        dto.setMemberId(Long.parseLong(user.getUserId()));
        KycApplyDto apply = kycApplyMapper.findByMemberId(dto.getMemberId());
        KycApply kycApply = KycApply.builder()
                .realName(dto.getRealName())
                .memberId(dto.getMemberId())
                .identityCode(dto.getIdentityCode())
                .birthdate(dto.getBirthdate())
                .nationality(dto.getNationality())
                .job(dto.getJob())
                .title(dto.getTitle())
                .isMultiNationality(dto.getIsMultiNationality())
                .status(AuditStatusConst.id_audit)
                .idFront(dto.getIdFront())
                .idBack(dto.getIdBack())
                .idHand(dto.getIdHand())
                .secondId(dto.getSecondId())
                .bankAccount(dto.getBankAccount())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .purpose(dto.getPurpose())
                .build();
        if (dto.getIsMultiNationality()){
            kycApply.setMultiOther(dto.getMultiOther());
        }
        if (dto.getTitle().equals("其他")){
            kycApply.setTitleOther(dto.getTitleOther());
        }
        if (dto.getJob().equals("其他")){
            kycApply.setJobOther(dto.getJobOther());
        }
        if (dto.getPurpose().equals("其他")){
            kycApply.setPurposeOther(dto.getPurposeOther());
        }
        if(null != apply){
            if(apply.getStatus().equals(AuditStatusConst.audit_reject) || apply.getStatus().equals(AuditStatusConst.NORMAL)) {
                log.info("[審核再申請]");
                kycApply.setId(apply.getId());
                kycApplyMapper.updateById(kycApply);
            }
        }
        else if(null == apply){
            log.info("[審核初次申請]");
            kycApplyMapper.insert(kycApply);
            Long applyId = kycApplyMapper.findByMemberId(dto.getMemberId()).getId();
            for (NameAuditTypeEnum type : NameAuditTypeEnum.values()) {
                KycAuditInfo info = new KycAuditInfo();
                info.setApplyId(applyId);
                info.setType(type.getType());
                kycAuditInfoMapper.insert(info);
            }
        }
        else{
            throw new SystemRuntimeException(KycExceptionEnum.KYC_APPLY_DUPLICATE_ERROR);
        }
    }
    public KycApplyDto getApply(User user){
        UserApp member = userAppMapper.selectById(Long.parseLong(user.getUserId()));
        return kycApplyMapper.findByMemberId(member.getId());
    }
    @Transactional
    public void idAudit(KycIdAuditReq req, User user){
        UserAdmin admin = userAdminMapper.selectById(Long.parseLong(user.getUserId()));
        if(!admin.getType().equals(AdminTypeConst.CUSTOMER)){
            throw new SystemRuntimeException(KycExceptionEnum.KYC_ADMIN_PERMISSION_REJECT);
        }
        KycApplyDto apply = kycApplyMapper.findByMemberId(req.getMemberId());
        if(!apply.getStatus().equals(AuditStatusConst.id_audit)){
            throw new SystemRuntimeException(KycExceptionEnum.KYC_AUDIT_FLOW_ERROR);
        }
        UserApp member = userAppMapper.selectById(req.getMemberId());
        member.setRealName(apply.getRealName());
        member.setIdentityCode(apply.getIdentityCode());
        userAppMapper.updateById(member);
        String status = req.getIsPass() ? AuditStatusConst.name_audit : AuditStatusConst.audit_reject;
        KycApply kycApply = KycApply.builder()
                .id(apply.getId())
                .realName(apply.getRealName())
                .memberId(apply.getMemberId())
                .identityCode(apply.getIdentityCode())
                .birthdate(apply.getBirthdate())
                .nationality(apply.getNationality())
                .job(apply.getJob())
                .jobOther(apply.getJobOther())
                .title(apply.getTitle())
                .titleOther(apply.getTitleOther())
                .isMultiNationality(apply.getIsMultiNationality())
                .multiOther(apply.getMultiOther())
                .status(status)
                .idFront(apply.getIdFront())
                .idBack(apply.getIdBack())
                .idHand(apply.getIdHand())
                .secondId(apply.getSecondId())
                .bankAccount(apply.getBankAccount())
                .phone(apply.getPhone())
                .address(apply.getAddress())
                .purpose(apply.getPurpose())
                .purposeOther(apply.getPurposeOther())
                .build();
        kycApplyMapper.updateById(kycApply);
    }
    public Object getTable(Long id) {
        KycApplyDto dto = kycApplyMapper.findByMemberId(id);
        try {
            List<KycAuditInfo> list = kycAuditInfoMapper.getList(dto.getId());
            return JSONObject.toJSON(list);
        }catch (Exception e){
            return null;
        }
    }
    @Transactional
    public void updateTable(List<KycAuditReq> reqs, Boolean isLegal, User user) {
        UserAdmin admin = userAdminMapper.selectById(Long.parseLong(user.getUserId()));
        if(!isLegal && !admin.getType().equals(AdminTypeConst.CUSTOMER)){
            throw new SystemRuntimeException(KycExceptionEnum.KYC_ADMIN_PERMISSION_REJECT);
        }
        else if(isLegal && !admin.getType().equals(AdminTypeConst.LEGAL)){
            throw new SystemRuntimeException(KycExceptionEnum.KYC_ADMIN_PERMISSION_REJECT);
        }
        reqs.forEach(req ->{
            try {
                KycAuditInfo info = kycAuditInfoMapper.getOne(req.getApplyId(), req.getType(), req.getId());
                if(isLegal){
                    info.setLegalMemo(req.getLegalMemo());
                }
                else {
                    info.setCustomerMemo(req.getCustomerMemo());
                }
                info.setImageIds(req.getImageIds());
                kycAuditInfoMapper.updateById(info);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        if(!isLegal) {
            KycApply apply = kycApplyMapper.selectById(reqs.get(0).getApplyId());
            apply.setStatus(AuditStatusConst.risk_audit);
            kycApplyMapper.updateById(apply);
        }
    }

    public List<KycReasonResDto> getReasonList(String type){
        List<KycReasonResDto> list = new ArrayList<>();
        KycAuditReasonType mainType = kycReasonTypeMapper.getType(type);
        String type_desc = mainType.getDescription();
        String type_code = mainType.getCode();
        List<KycAuditReasonSubtype> subtypeList = kycReasonSubtypeMapper.getList(mainType.getId());
        subtypeList.forEach(subtype -> {
            String subtype_desc = subtype.getDescription();
            String subtype_code = subtype.getCode();
            List<KycAuditReason> reasons = kycAuditReasonMapper.getReasons(subtype.getId());
            reasons.forEach(reason ->{
                String doc_code = type_code + subtype_code + reason.getDocId();
                KycReasonResDto dto = KycReasonResDto.builder()
                        .id(reason.getId())
                        .code(doc_code)
                        .type(type_desc)
                        .subtype(subtype_desc)
                        .description(reason.getDescription())
                        .point(reason.getPoint())
                        .build();
                list.add(dto);
            });
        });
        return list;
    }
    @Transactional
    public void insertReasonList(List<KycRiskAuditReq> reqList, User user){
        UserAdmin admin = userAdminMapper.selectById(Long.parseLong(user.getUserId()));
        if(!admin.getType().equals(AdminTypeConst.RISK)){
            throw new SystemRuntimeException(KycExceptionEnum.KYC_ADMIN_PERMISSION_REJECT);
        }
        List<KycAuditRisk> riskList = kycAuditRiskMapper.getAllByMember(reqList.get(0).getMemberId());
        if(!(riskList.size() == 0)){
            kycAuditRiskMapper.deleteByMemberIdOnlyRisk(reqList.get(0).getMemberId());
        }

        AtomicReference<Integer> containResult = new AtomicReference<>(0);
        AtomicReference<Long> docId = new AtomicReference<>(0L);
        reqList.forEach(req -> {
            KycAuditReason reason = kycAuditReasonMapper.selectById(req.getReasonId());
            if(null != reason) {
                if (reason.getType().compareTo(18L) == 0) {
                    containResult.set(reason.getDocId().intValue());
                }
                KycAuditRisk risk = KycAuditRisk.builder()
                        .memberId(req.getMemberId())
                        .fact(req.getReasonId())
                        .point(reason.getPoint())
                        .build();
                kycAuditRiskMapper.insert(risk);
            }
            else{
                docId.set(req.getDocId());
            }
        });
        if(containResult.get() == 0){
            throw new SystemRuntimeException(KycExceptionEnum.KYC_AUDIT_RESULT_MISSING_ERROR);
        }
        KycApply apply = kycApplyMapper.GetApplyByMemberId(reqList.get(0).getMemberId());
        // 更新文件檔id
        try {
            UtilImage image = utilImageMapper.selectById(docId.get());
            image.setApplyId(apply.getId());
            utilImageMapper.updateById(image);
        }catch (Exception e){
            e.printStackTrace();
        }
        String status = "";
        switch (containResult.get()){
            case 1:
                status = AuditStatusConst.audit_reject;
                break;
            case 2:
                status = AuditStatusConst.audit_pass;
                break;
            case 3:
                status = AuditStatusConst.legal_audit;
                break;
            default:
                throw new SystemRuntimeException(KycExceptionEnum.KYC_AUDIT_RESULT_TYPE_ERROR);
        }
        apply.setStatus(status);
        kycApplyMapper.updateById(apply);
    }

    public JSONArray getRiskList(Long memberId){
        try {
            List<KycAuditRisk> risk = kycAuditRiskMapper.getAllByMember(memberId);
            List<KycReasonResDto> list = new ArrayList<>();
            risk.forEach(record -> {
                KycAuditReason reason = kycAuditReasonMapper.selectById(record.getFact());
                KycAuditReasonSubtype subtype = kycReasonSubtypeMapper.selectById(reason.getType());
                KycAuditReasonType mainType = kycReasonTypeMapper.selectById(subtype.getMainType());
                String doc_code = mainType.getCode() + subtype.getCode() + reason.getDocId();
                KycReasonResDto dto = KycReasonResDto.builder()
                        .id(reason.getId())
                        .code(doc_code)
                        .type(mainType.getDescription())
                        .subtype(subtype.getDescription())
                        .description(reason.getDescription())
                        .point(reason.getPoint())
                        .build();
                list.add(dto);
            });
            KycApply apply = kycApplyMapper.GetApplyByMemberId(memberId);
            String url = utilImageMapper.findUrlByApplyId(apply.getId());
            JSONArray array = (JSONArray) JSON.toJSON(list);
            JSONObject obj = new JSONObject();
            obj.put("docUrl",url);
            obj.put("code","doc");
            obj.put("subtype","加強審核問卷");
            obj.put("description","加強審核問卷");
            obj.put("type","加強審核問卷");
            obj.put("point",0);
            obj.put("id",0);
            array.add(obj);
            return array;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void insertLegalReasonList(KycRiskAuditReq req,User user){
        List<KycAuditRisk> riskList = kycAuditRiskMapper.getAllByMember(req.getMemberId());
        if(!(riskList.size() == 0)){
            kycAuditRiskMapper.deleteByMemberIdOnlyLegal(req.getMemberId());
        }
        AtomicReference<Integer> containResult = new AtomicReference<>(0);
        KycAuditReason reason = kycAuditReasonMapper.selectById(req.getReasonId());
        if(reason.getType().compareTo(19L) == 0){
            containResult.set(reason.getDocId().intValue());
        }
        KycAuditRisk risk = KycAuditRisk.builder()
                .memberId(req.getMemberId())
                .fact(req.getReasonId())
                .point(reason.getPoint())
                .build();
        kycAuditRiskMapper.insert(risk);
        if(containResult.get() == 0){
            throw new SystemRuntimeException(KycExceptionEnum.KYC_AUDIT_RESULT_MISSING_ERROR);
        }
        KycApply apply = kycApplyMapper.GetApplyByMemberId(req.getMemberId());
        String status = "";
        switch (containResult.get()){
            case 1:
                status = AuditStatusConst.audit_pass;
                break;
            case 2:
                status = AuditStatusConst.audit_reject;
                break;
            default:
                throw new SystemRuntimeException(KycExceptionEnum.KYC_AUDIT_RESULT_TYPE_ERROR);
        }
        apply.setStatus(status);
        kycApplyMapper.updateById(apply);
    }

    public Object getImageUrl(String imgString){
        List<UtilImage> utilImages = new ArrayList<>();
        if(imgString.contains(",")){
            List<String> images = Arrays.asList(imgString.split(","));
            List<Long> list = new ArrayList<>();
            images.forEach(image -> {
                list.add(Long.parseLong(image));
            });
            utilImages = utilImageMapper.selectBatchIds(list);
        }
        else{
            List<Long> list = new ArrayList<>();
            list.add(Long.parseLong(imgString));
            utilImages = utilImageMapper.selectBatchIds(list);
        }
        return utilImages;
    }

    public Integer getTypeCount(String status){
        return kycApplyMapper.getStatusCount(status);
    }
}
