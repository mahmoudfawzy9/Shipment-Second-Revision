package com.company.star.db.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.company.star.db.model.Carrier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.star.db.model.Shipment;

import javax.transaction.Transactional;

@Repository
public interface ShipmentRepo extends JpaRepository<Shipment, Long> {

	@Query("SELECT n FROM Shipment n WHERE n.carrierId = :carrierId AND n.carrierType = :carrierType order by n.createdAt desc")
	List<Shipment> findByCarrierIdAndCarrierType(@Param("carrierId") int carrierId, @Param("carrierType") int carrierType);

	@Query("SELECT n FROM Shipment n WHERE n.carrierId = :carrierId AND n.carrierType = :carrierType and n.isDelivered = 0")
	List<Shipment> findUndeliveredByCarrierIdAndCarrierType(@Param("carrierId") int carrierId, @Param("carrierType") int carrierType);

	@Query("SELECT n from Shipment n where n.weight = :weight order by n.createdAt desc")
	List<Shipment> getRecordByWeight(@Param("weight") BigDecimal weight);

	@Query("SELECT n FROM Shipment n WHERE n.shipmentServiceID = :shipmentServiceID and n.isDelivered = 0")
	List<Shipment> findByShipmentServiceID(@Param("shipmentServiceID") String shipmentServiceID);

	@Query("SELECT n FROM Shipment n WHERE n.carrierId = :carrierId AND n.carrierType = :carrierType and n.isDelivered = 1")
	List<Shipment> findDeliveredByCarrierIdAndCarrierType(@Param("carrierId") int carrierId, @Param("carrierType") int carrierType);

	Page<Shipment> findByCarrierId(int carrierId, Pageable pageable);
	Optional<Shipment> findByIdAndCarrierId(Long id, Long carrierId);
	@Modifying
	@Transactional
	@Query("update Shipment n set n.isConfirmed = :isConfirmed where n.carrierId = :carrierId AND n.carrierType =:carrierType AND n.weight =:weight")
	int checkOrderIsConfirmed(@Param("isConfirmed") boolean isConfirmed, @Param("carrierId") int carrierId,
							  @Param("carrierType") int userType, @Param("weight") BigDecimal weight);


	@Modifying
	@Transactional
	@Query("update Shipment n set n.isConfirmed = :isConfirmed where n.carrierId = :carrierId AND n.carrierType =:carrierType AND n.weight =:weight")
	int updateOrderIsConfirmed(@Param("isConfirmed") boolean isConfirmed, @Param("carrierId") int carrierId,
							   @Param("carrierType") int carrierType, @Param("weight") BigDecimal weight);

//	Page<Shipment> findByCarrierId(Long carrierId, Pageable pageable);
//	Optional<Shipment> findByIdAndPostId(Long id, Long carrierId);
}
