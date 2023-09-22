package ppp.mess;

import org.springframework.data.jpa.repository.JpaRepository;

interface RestaurantRepo extends JpaRepository<Restaurant,Long>{
    //custom functions aca
}
interface MesasRepo extends JpaRepository<Mesas, Long>{

}
interface OrderRepo extends JpaRepository<Order, Long>{

}
interface BranchesRepo extends JpaRepository<Branches, Long>{

}
interface DishesRepo extends JpaRepository<Dishes, Long>{

}
interface PhotoRepo extends JpaRepository<Photo, Long>{

}
interface StatusRepo extends JpaRepository<Status, Long>{

}
interface WaiterRepo extends JpaRepository<Waiter, Long>{

}