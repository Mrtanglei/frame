package repository.user;

import entity.user.LoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tanglei
 * @date 18/10/11
 */
@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecord,Long> {
}
