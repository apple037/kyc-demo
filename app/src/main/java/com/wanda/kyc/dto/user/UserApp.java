package com.wanda.kyc.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName user_app
 */
@TableName(value ="member")
@Data
public class UserApp implements Serializable {
    /**
     * 會員ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 帳戶類型
     */
    private String memberId;

    /**
     * 
     */
    private String username;

    /**
     * 密碼
     */
    private String password;

    /**
     * 鹽巴
     */
    private String salt;

    /**
     * 註冊時間
     */
    private String realName;

    /**
     * 註冊時間
     */
    private String identityCode;

    /**
     * 註冊時間
     */
    private String registerAt;

    /**
     * 狀態
     */
    private String status;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        UserApp other = (UserApp) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
                && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
                && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getRegisterAt() == null ? other.getRegisterAt() == null : this.getRegisterAt().equals(other.getRegisterAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getRegisterAt() == null) ? 0 : getRegisterAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", status=").append(status);
        sb.append(", registerAt=").append(registerAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}