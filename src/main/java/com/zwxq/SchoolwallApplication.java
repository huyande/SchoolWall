package com.zwxq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication() //exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class}
@MapperScan(basePackages= {"com.zwxq.dao"})
public class SchoolwallApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolwallApplication.class, args);
	}
}

