package net.ruixin.qb;

import com.itfsw.query.builder.SqlQueryBuilderFactory;
import com.itfsw.query.builder.support.builder.SqlBuilder;
import com.itfsw.query.builder.support.model.result.SqlQueryResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryBuilderApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testQueryBuilder() throws IOException {
        String json = "{\"condition\":\"OR\",\"rules\":[{\"id\":\"name\",\"field\":\"username\",\"type\":\"string\",\"input\":\"text\",\"operator\":\"equal\",\"value\":\"Mistic\"}],\"not\":false,\"valid\":true}";
        System.out.println(json);

        // ----------------------------------------- SQL -----------------------------------------
        // get SqlBuilder
        SqlQueryBuilderFactory sqlQueryBuilderFactory = new SqlQueryBuilderFactory();
        SqlBuilder sqlBuilder = sqlQueryBuilderFactory.builder();

        // build query
        SqlQueryResult sqlQueryResult = sqlBuilder.build(json);

        // execute
        jdbcTemplate.query(new StringBuffer("SELECT * FROM MY_USER WHERE ").append(sqlQueryResult.getQuery()).toString(), sqlQueryResult.getParams().toArray(), rs -> {
            System.out.println(rs.getString("username"));
        });
    }

}
