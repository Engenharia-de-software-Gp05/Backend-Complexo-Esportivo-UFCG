package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = SignUpConfirmationCode.SIGN_UP_CONFIRMATION_CODE_TABLE)
public class SignUpConfirmationCode extends BasicEntity {

    public static final String SIGN_UP_CONFIRMATION_CODE_TABLE = "sign_up_confirmation_code";
    private static final String SACE_USER_ID_COLUMN = "sace_user_id";
    private static final String CONFIRMATION_CODE_COLUMN = "confirmationCode";
    private static final String EXPIRATES_AT_COLUMN = "expirates_at";

    @OneToOne
    @JoinColumn(name = SACE_USER_ID_COLUMN, nullable = false)
    private SaceUser user;

    @Column(name = CONFIRMATION_CODE_COLUMN, nullable = false)
    private String confirmationCode;

    @Column(name = EXPIRATES_AT_COLUMN, nullable = false)
    private LocalDateTime expiresAt;

    public SignUpConfirmationCode() {
    }

    public SignUpConfirmationCode(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public SignUpConfirmationCode(SaceUser saceUser, String codigoString, LocalDateTime expiresAt) {
        this.user = saceUser;
        this.confirmationCode = codigoString;
        this.expiresAt = expiresAt;
    }

    public SaceUser getUser() {
        return user;
    }

    public void setUser(SaceUser user) {
        this.user = user;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SignUpConfirmationCode that = (SignUpConfirmationCode) o;
        return Objects.equals(user, that.user) && Objects.equals(confirmationCode, that.confirmationCode) && Objects.equals(expiresAt, that.expiresAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, confirmationCode, expiresAt);
    }

    @Override
    public String toString() {
        return "SignUpConfirmationCode{" +
                "user=" + user +
                ", confirmationCode='" + confirmationCode + '\'' +
                ", expiratesAt=" + expiresAt +
                '}';
    }
}
