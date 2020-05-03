package main.bee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;

import main.bee.changelog.DatabaseChangeLog;

@Configuration
public class MongoBeeConfigTest {

    @Autowired
    private MongoClient mongo;

    @Bean
    public Mongobee mongobeeTest(Environment environment) {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName("library_db_tst");
        runner.setChangeLogsScanPackage(DatabaseChangeLog.class.getPackage().getName());
        runner.setSpringEnvironment(environment);
        return runner;
    }
}