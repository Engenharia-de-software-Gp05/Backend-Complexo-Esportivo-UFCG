package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.enums.RoleEnum;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = User.USER_TABLE)
public class User extends BasicEntity {

    private static final String PHONE_NUMBER_COLUMN = "phone_number";
    private static final String PASSWORD_COLUMN = "password";
    private static final String NAME_COLUMN = "name";
    private static final String EMAIL_COLUMN = "email";
    private static final String ROLES_COLUMN = "roles";
    private static final String ROLE_TABLE = "role";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String STUDENT_ID_COLUMN = "student_id";
    public static final String USER_TABLE = "user";

    @Column(name = NAME_COLUMN, nullable = false)
    private String name;

    @Column(name = EMAIL_COLUMN, nullable = false, unique = true)
    private String email;

    @Column(name = PHONE_NUMBER_COLUMN, nullable = false)
    private String phoneNumber;

    @Column(name = PASSWORD_COLUMN, nullable = false)
    private String password;

    @Column(name = STUDENT_ID_COLUMN, unique = true)
    private String studentId;

    @ElementCollection
    @CollectionTable(name = ROLE_TABLE, joinColumns = @JoinColumn(name = USER_ID_COLUMN))
    @Column(name = ROLES_COLUMN, nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<RoleEnum> roleEnums;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEnum> getRoleEnums() {
        return roleEnums;
    }

    public void setRoleEnums(Set<RoleEnum> roleEnums) {
        this.roleEnums = roleEnums;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(password, user.password) && Objects.equals(roleEnums, user.roleEnums) && Objects.equals(studentId, user.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, email, phoneNumber, password, roleEnums, studentId);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", roleEnums=" + roleEnums +
                ", studentId='" + studentId + '\'' +
                '}';
    }
}
