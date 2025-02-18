//package progetto.springProject.model;
//
//import java.util.Date;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name= "orders")
//public class Orders {
//
//	@Id
//	@GeneratedValue(strategy= GenerationType.IDENTITY)
//	private Long id;
//	
//	@ManyToOne
//	@JoinColumn(name= "user_id")
//	private User user;
//	
//	@Column(nullable= true)
//	private Date order_date;
//	
//	@Column(nullable= true)
//	private Double total; //totale costo
//	
//	public Orders() {}
//	
//	public Orders(Long id, User user, Date order_date, Double total) {
//		this.id=id;
//		this.user=user;
//		this.order_date=order_date;
//		this.total=total;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	public Date getOrder_date() {
//		return order_date;
//	}
//
//	public void setOrder_date(Date order_date) {
//		this.order_date = order_date;
//	}
//
//	public Double getTotal() {
//		return total;
//	}
//
//	public void setTotal(Double total) {
//		this.total = total;
//	}
//	
//}
