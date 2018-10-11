package entity.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author tanglei
 * @date 18/10/11
 *
 * 登录记录
 */
@Entity
@Table(name = "login_record")
@Getter
@Setter
public class LoginRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_account")
    private UserAccount userAccount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "login_time")
    private Date loginTime;

    @Column(name = "ip")
    private String ip;
}
