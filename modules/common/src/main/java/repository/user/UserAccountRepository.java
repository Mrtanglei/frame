package repository.user;

import entity.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tanglei
 * @date 18/10/11
 */
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

    UserAccount findUserAccountByAccount(String account);
}
