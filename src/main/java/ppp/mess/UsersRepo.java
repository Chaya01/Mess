package ppp.mess;

import org.springframework.data.jpa.repository.JpaRepository;

interface UsersRepo extends JpaRepository<Users,Long>{
    //custom functions aca
}
interface MesasRepo extends JpaRepository<Mesas, Long>{

}