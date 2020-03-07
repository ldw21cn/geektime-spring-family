package geektime.spring.data.declarativetransactiondemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FooServiceImpl implements FooService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FooService fooService;

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
        throw new RollbackException();
    }

    @Override
    // @Transactional(rollbackFor = RollbackException.class)
    // 如果此处不加注解，则是使用隐式的事务方式，即没有事务
    // 如果此处加了注解，则使用事务方式执行
    // 也可以使用它的服务调用它的方法
    public void invokeInsertThenRollback() throws RollbackException {
        // insertThenRollback();
        fooService.insertThenRollback();

    }
}
