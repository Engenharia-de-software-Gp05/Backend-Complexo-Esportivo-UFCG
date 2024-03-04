package com.ufcg.es5.BackendComplexoEsportivoUFCG.entity;

import com.ufcg.es5.BackendComplexoEsportivoUFCG.entity.basic.BasicEntity;
import com.ufcg.es5.BackendComplexoEsportivoUFCG.dto.user.enums.UserRoleEnum;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = User.USER_TABLE)
public class User extends BasicEntity implements UserDetails {

    private static final String PHONE_NUMBER_COLUMN = "phone_number";
    private static final String PASSWORD_COLUMN = "password";
    private static final String NAME_COLUMN = "name";
    private static final String EMAIL_COLUMN = "email";
    private static final String ROLES_COLUMN = "roles";
    private static final String ROLE_TABLE = "role";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String STUDENT_ID_COLUMN = "student_id";
    private static final String USER_PROPERTY = "user";
    public static final String USER_TABLE = "users";

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

    @OneToMany(mappedBy = USER_PROPERTY, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    @ElementCollection
    @CollectionTable(name = ROLE_TABLE, joinColumns = @JoinColumn(name = USER_ID_COLUMN))
    @Column(name = ROLES_COLUMN, nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<UserRoleEnum> roleEnums;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleEnums.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }

    public User(
            String email,
            String name,
            String phoneNumber,
            String studentId,
            String password,
            Set<UserRoleEnum> roleEnums){
            this.email = email;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.studentId = studentId;
            this.password = password;
            this.roleEnums = roleEnums;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservas) {
        this.reservations = reservas;
    }

    public Set<UserRoleEnum> getRoleEnums() {
        return roleEnums;
    }

    public void setRoleEnums(Set<UserRoleEnum> roleEnums) {
        this.roleEnums = roleEnums;
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
