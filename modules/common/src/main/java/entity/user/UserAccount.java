package entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author tanglei
 * @date 18/10/11
 *
 * 账户
 */
@Entity
@Table(name = "user_account")
@Getter
@Setter
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account")
    private String account;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "is_system")
    private Boolean isSystem;

    @Column(name = "password")
    private String password;

    @Column(name = "password_salty")
    private String passwordSalty;
}
