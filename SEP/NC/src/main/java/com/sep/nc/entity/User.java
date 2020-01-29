package com.sep.nc.entity;

import com.sep.nc.entity.enumeration.ScientificArea;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Table
@Entity
public class User implements Serializable, UserDetails {


    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private String city;

    @Column
    private String country;

    @ManyToMany
    private List<MagazinePurchase> magazinePurchases;

    @Column
    @Enumerated
    @ElementCollection(targetClass = ScientificArea.class)
    private List<ScientificArea> scientificAreasOfInterest;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;


    @Column
    private boolean enabled;

    public User() {
        magazinePurchases = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set privileges = new HashSet();

        for (Role role : this.roles) {
            Collection<Privilege> allPrivileges = role.getPrivileges();
            for (Privilege privilege : allPrivileges) {
                privileges.add(new SimpleGrantedAuthority(privilege.getName()));
            }
        }
        return privileges;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<MagazinePurchase> getMagazinePurchases() {
        return magazinePurchases;
    }

    public void setMagazinePurchases(List<MagazinePurchase> magazinePurchases) {
        this.magazinePurchases = magazinePurchases;
    }

    public List<ScientificArea> getScientificAreasOfInterest() {
        return scientificAreasOfInterest;
    }

    public void setScientificAreasOfInterest(List<ScientificArea> scientificAreasOfInterest) {
        this.scientificAreasOfInterest = scientificAreasOfInterest;
    }
}
