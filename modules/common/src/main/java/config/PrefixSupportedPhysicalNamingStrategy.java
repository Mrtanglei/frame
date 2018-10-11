package config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import repository.user.UserAccountRepository;

/**
 * @author tanglei
 * @date 18/10/11
 *
 * 实体与表命名策略
 */
public class PrefixSupportedPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {

    private static String prefix = "other_";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        Identifier fullTableName = new Identifier(prefix + name.getText(), name.isQuoted());
        return super.toPhysicalTableName(fullTableName, jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return super.getIdentifier(name.getText(), name.isQuoted(), jdbcEnvironment);
    }
}
