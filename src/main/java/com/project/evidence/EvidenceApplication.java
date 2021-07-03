package com.project.evidence;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.project.evidence.evidenceModule.database.mapper"
        ,"com.project.evidence.loginModule.database.mapper"
        ,"com.project.evidence.organizationModule.database.mapper"
        ,"com.project.evidence.userModule.database.mapper"
        ,"com.project.evidence.identifyModule.database.mapper"
        ,"com.project.evidence.instrumentModule.database.mapper"
        })
public class EvidenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvidenceApplication.class, args);
    }

}
