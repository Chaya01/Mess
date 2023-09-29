/* package ppp.mess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDb {

    private static final Logger log = LoggerFactory.getLogger(LoadDb.class);

    @Bean
    CommandLineRunner initDatabase(RestaurantRepo restaurantRepo, MesasRepo mesasRepo){
        return args -> {
            log.info("Preloading" + restaurantRepo.save((new Restaurant("Chaya","chaya@chaya.com","123456","19073896", "asdf","Chia"))));
            log.info("Preloading2" + restaurantRepo.save((new Restaurant("netox","netox@netox.com","987654","1900123", "qwerty","neet0s"))));
            log.info("Preloading3" + mesasRepo.save((new Mesas("Mesa 1","4",true,false))));
        };
    }
}
*/
