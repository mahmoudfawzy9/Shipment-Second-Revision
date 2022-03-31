package com.company.star.datamanager;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.company.star.db.model.Carrier;
import com.company.star.db.model.CarrierStatus;
import com.company.star.db.model.Shipment;
import com.company.star.db.repository.CarrierRepo;

import com.company.star.db.repository.CarrierStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarrierDataManager extends DataManager {


	@Autowired
	CarrierRepo repository;

	@Autowired
	CarrierStatusRepo carrierStatusRepo;
	public Carrier saveCarrier(Carrier newCarrier) {
			Shipment shipment = new Shipment();
			newCarrier.addShipment(shipment);
			shipment.setCarrier(String.valueOf(newCarrier));
//            shipment.setCarrier2();
//			shipment.setIsConfirmed();
//			 Add shipment to db
//			shipmentService.addShipment(shipment);
		return repository.save(newCarrier);
	}

	public Carrier getCarrierById(int id) {
		try {
			Optional<Carrier> optional = repository.findById(id);

			if (optional.isPresent()) {
				return optional.get();
			}

			throw new EntityNotFoundException("Carrier with id: " + id + " wasn't found");
		} catch (NoSuchElementException e) {
			throw new EntityNotFoundException("Carrier with id: " + id + " wasn't found");
		}
	}
	public List<Carrier> getAllShipments() {
		return repository.findAll();
	}

	public Carrier getShipmentById(int id) {
		try {
			Optional<Carrier> optional = repository.findById(id);

			if (optional.isPresent()) {
				return optional.get();
			}

			throw new EntityNotFoundException("Fedex Shipment with id: " + id + " wasn't found");
		} catch (NoSuchElementException e) {
			throw new EntityNotFoundException("Fedex Shipment with id: " + id + " wasn't found");
		}
	}

	public int getUndeliveredShipmentsForUPS(int carrierId) {
		//assume fedex carrierType = 3
		List<Carrier> records = repository.findUndeliveredByCarrierType(3);
		if (records == null || records.isEmpty()) {
			return 0;
		} else {
			return records.size();
		}
	}
	public int getUndeliveredShipmentsForFedex(int carrierId) {
		//assume fedex carrierType = 1
		List<Carrier> records = repository.findUndeliveredByCarrierType(1);
		if (records == null || records.isEmpty()) {
			return 0;
		} else {
			return records.size();
		}
	}

	public Carrier getRecordForCarrierWeight(int weight) {
		return repository.getCarrierForWeight(weight);
	}

		public List<Carrier> getShipmentFedexByCarrierServiceID(String carrierServiceID) {

		List <Carrier> records =  repository.findByCarrierServiceID(carrierServiceID);

			if (records == null || records.isEmpty()) {
			throw new EntityNotFoundException("Shipment with id: " + carrierServiceID + " wasn't found");
		} else if(records.contains("fedex")) {
			return records;
			} else{
				return records;
			}
		}

	public List<Carrier> getShipmentUPSByShipmentServiceID(String shipmentServiceID) {

		List <Carrier> records =  repository.findByShipmentServiceID(shipmentServiceID);
		 if (records == null || records.isEmpty()) {
			 throw new EntityNotFoundException("Shipment with id: " + shipmentServiceID + " wasn't found");
		 } else if(records.contains("UPS")) {
			 return records;
		 }
		return records;
	}

	public boolean updateOrderIsConfirmed(Carrier record) {
		boolean isConfirmed = record.getIsConfirmed();
		int carrierId = record.getCarrierId();
		int carrierType = record.getCarrierType(2);
		int updated = repository.updateOrderIsConfirmed(isConfirmed, carrierId, carrierType,record.getWeight());
		if (updated != 0) {
			return true;
		} else {
			throw new EntityNotFoundException("Couldn't find matching record");
		}
	}
	public int getDeliveredShipmentUPS(int shipmentServiceID){
		List<Carrier> records = repository.findDeliveredByCarrierIdAndCarrierType(shipmentServiceID, 3);
		if (records == null || records.isEmpty()) {
			throw new EntityNotFoundException("Shipments for UPS with id: " + shipmentServiceID + " wasn't found");
		} else {
			return records.size();
		}
	}

	public int getDeliveredShipmentFedex(int carrierServiceID){
		List<Carrier> records = repository.findDeliveredByCarrierIdAndCarrierType(carrierServiceID, 1);
		if (records == null || records.isEmpty()) {
			throw new EntityNotFoundException("Shipments for UPS with id: " + carrierServiceID + " wasn't found");
		} else {
			return records.size();
		}
	}
	public CarrierStatus saveCarrierStatus(CarrierStatus entity) {
		return carrierStatusRepo.save(entity);
	}

	public List<Carrier> getRecordByWeight(BigDecimal weight) {
		List<Carrier> records = repository.getRecordByWeight(weight);
		if (records == null || records.isEmpty()||(records.size()==0)) {

			return null;
		} else {
//			throw new EntityNotFoundException("Couldn't find matching imei in the system");
			return records;
		}
	}

	public List<Carrier> getConfirmedShipment(int carrierId, int carrierType) {
		return repository.getRecordByIdsAndConfirmed(carrierId, carrierType);
	}

	public Carrier save(Carrier user) {
		return repository.save(user);
	}
	//	public boolean updateRecordCarrier(Carrier record) {
//		int carrier = record.getCarrier(1);
////		int carrierId = record.getCarrierId(1);
//		int carrierType = record.getCarrierType(2);
//		int updated = repository.updateRecordCarrier(carrier, carrierId, carrierType);
//		if (updated != 0) {
//		return true;
//		} else {
//			throw new EntityNotFoundException(
//					"Couldn't find matching record with carrierId " + carrierId + " and carrierType " + carrierType);
//		}
//	}
}
