package progetto.springProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progetto.springProject.model.Orders;
import progetto.springProject.model.User;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>{
	List<Orders> findByUser(User user);
	
}