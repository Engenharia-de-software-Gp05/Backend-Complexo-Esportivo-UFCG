package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = SignUpConfirmationCode.SIGN_UP_CONFIRMATION_CODE_TABLE)
public class SignUpConfirmationCode extends BasicEntity {

    public static final String SIGN_UP_CONFIRMATION_CODE_TABLE = "sign_up_confirmation_code";
    private static final String SACE_USER_ID_COLUMN = "sace_user_id";
    private static final String CONFIRMATION_CODE_COLUMN = "confirmationCode";

    @OneToOne
    @JoinColumn(name = SACE_USER_ID_COLUMN, nullable = false)
    private SaceUser user;

    @Column(name = CONFIRMATION_CODE_COLUMN, nullable = false)
    private String confirmationCode;

    public SignUpConfirmationCode(){
    }
    public SignUpConfirmationCode(SaceUser saceUser, String codigoString) {
        this.user = saceUser;
        this.confirmationCode = codigoString;
    }

    public SaceUser getUser() {
        return user;
    }

    public void setUser(SaceUser user) {
        this.user = user;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        if (!super.equals(o)) return false;
        SignUpConfirmationCode that = (SignUpConfirmationCode) o;
        return Objects.equals(user, that.user) && Objects.equals(confirmationCode, that.confirmationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, confirmationCode);
    }

    @Override
    public String toString() {
        return "SignUpConfirmationCode{" +
                "saceUser=" + user +
                ", codigoString='" + confirmationCode + '\'' +
                '}';
    }
}
