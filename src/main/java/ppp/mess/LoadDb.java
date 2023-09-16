package ppp.mess;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDb {

    private static final Logger log = LoggerFactory.getLogger(LoadDb.class);

    @Bean
    CommandLineRunner initDatabase(UsersRepo usersRepo, MesasRepo mesasRepo){
        return args -> {
            log.info("Preloading" + usersRepo.save((new Users("Chaya","chaya@chaya.com"))));
            log.info("Preloading2" + usersRepo.save((new Users("netox","netox@netox.com"))));
            log.info("Preloading3" + mesasRepo.save((new Mesas("Mesa 1","Platos"))));
        };
    }
}
