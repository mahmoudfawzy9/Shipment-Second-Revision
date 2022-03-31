package com.company.star.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.company.star.datamanager.ShipmentDataManager;
import com.company.star.form.ShipmentForm;
import com.company.star.utils.ApiResponse;
import com.company.star.utils.GsonProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.star.datamanager.CarrierDataManager;
import com.company.star.db.model.Carrier;
import com.company.star.form.CarrierForm;

import javax.transaction.Transactional;

/**
 * Refactor:- //change setDeviceType 2nd condidition to check in a list of
 * possible mobile models change setDeviceType 3rd condition to put hwaui os
 * type
 *
 */

@Service
@Transactional
public class CarrierService {

	@Autowired
	GeneralValidation validation;
	@Autowired
	CarrierDataManager dataManager;

	@Autowired
	ShipmentDataManager shipmentDataManager;

	ModelMapper mapper = new ModelMapper();
	public String saveCarrier(CarrierForm obj) {

		validation.validateData(obj);
		BigDecimal width = obj.getWidth();
		BigDecimal length = obj.getLength();
		CarrierForm updatedObj = setPackageDetails("fedexAIR", obj);
		List<Carrier> carriers = dataManager.getRecordByWeight(updatedObj.getWeight());
		Carrier entity = new Carrier(updatedObj);
		Map<Integer, Integer> ids = new HashMap<>();

		if (carriers == null) {
			Carrier persistedObj = dataManager.saveCarrier(entity);
			return constructResponse(persistedObj, "new shipment is added successfully with weight: " +updatedObj.getWeight());
		} else {
			for (Carrier record : carriers) {
				ids.put(record.getCarrier(), record.getCarrierType(1));

				if (record.getCarrierId() == updatedObj.getCarrierId() || record.getCarrierType(1) == updatedObj.getCarrierType(1)) {
					if (record.getIsConfirmed()) {
						record.setCarrier(updatedObj.getCarrierServiceID());
						Carrier persistedObj = dataManager.saveCarrier(record);
						return constructResponse(persistedObj,
								"new shipment is added successfully for package with weight: " + updatedObj.getWeight());
					} else {// IsConfirmed is = false
						record.setIsConfirmed(true);
						record.setCarrier(updatedObj.getCarrierServiceID());
						Carrier persistedObj = dataManager.saveCarrier(record);
						return constructResponse(persistedObj,
								"shipment is confirmed successfully for package with weight: " + updatedObj.getWeight());
					}

				} else { // not confirmed package
					record.setIsConfirmed(false);
					dataManager.updateOrderIsConfirmed(record);
					Carrier persistedObj = dataManager.saveCarrier(entity);
					return constructResponse(persistedObj,
							"another record added for weight " + updatedObj.getWeight() + "to a new package");
				}
			}
			Carrier persistedObj = dataManager.saveCarrier(entity);
			return constructResponse(persistedObj, "new carrier added successfully to the system");
		}
	}

	public String saveShipment(ShipmentForm obj) {

		validation.validateData(obj);
		BigDecimal width = obj.getWidth();
		BigDecimal length = obj.getLength();
		ShipmentForm updatedObj = setPackage("UPSExpress", obj);
		List<Carrier> carriers = dataManager.getRecordByWeight(updatedObj.getWeight());
		Carrier entity = new Carrier(updatedObj);
		Map<Integer, Integer> ids = new HashMap<>();

		if (carriers == null) {
			Carrier persistedObj = dataManager.saveCarrier(entity);
			return constructResponse(persistedObj, "new shipment added successfully with weight: "+updatedObj.getWeight());
		} else {
			for (Carrier record : carriers) {
				ids.put(record.getCarrier(), record.getCarrierType(3));

				if (record.getCarrier() == updatedObj.getCarrierId() || record.getCarrierType(3) == updatedObj.getCarrierType(3)) {
					if (record.getIsConfirmed()) {
						record.setCarrier(updatedObj.getShipmentServiceID());
						Carrier persistedObj = dataManager.saveCarrier(record);
						return constructResponse(persistedObj,
								"new shipment is added successfully for package with weight: " + updatedObj.getWeight());
					} else {// IsConfirmed is = false then make it true #Update is used here
						record.setIsConfirmed(true);
						record.setCarrier(updatedObj.getShipmentServiceID());
						Carrier persistedObj = dataManager.saveCarrier(record);
						return constructResponse(persistedObj,
								"shipment is confirmed successfully for package with weight: " + updatedObj.getWeight());
					}

				} else { // not confirmed package
					record.setIsConfirmed(false);
					dataManager.updateOrderIsConfirmed(record);
					Carrier persistedObj = dataManager.saveCarrier(entity);
					return constructResponse(persistedObj,
							"another record added for weight " + updatedObj.getWeight() + "to a new package");
				}
			}
			Carrier persistedObj = dataManager.saveCarrier(entity);
			return constructResponse(persistedObj, "new carrier added successfully to the system");
		}

	}
	// --------------------Helper Methods--------------------//
//	public Carrier getFedexShipment(int id) {
//		// where condition >carrierType=1
//		return dataManager.getCarrierById(id);
//	}
	public boolean checkShipmentIsConfirmed(Carrier carrier){
		return dataManager.updateOrderIsConfirmed(carrier);
	}

	public Carrier getShipmentById(int id) {
		return dataManager.getShipmentById(id);
	}

	public List<Carrier> getAllShipments(){
		return dataManager.getAllShipments();
	}

	//	public int getUndeliveredShipmentsForFedex(int carrierId) {
//		//assume fedex carrierType = 1
//		List<Shipment> records = shipmentRepo.findUndeliveredByCarrierIdAndCarrierType(carrierId, 1);
//		if (records == null || records.isEmpty()) {
//			return 0;
//		} else {
//			return records.size();
//		}
//	}
	public List<Carrier> getRecordByWeight(BigDecimal weight){
		return dataManager.getRecordByWeight(weight);
	}

	public List<Carrier> getRecordByConfirmed(int carrierId, int carrierType){
		return dataManager.getConfirmedShipment(carrierId, carrierType);
	}

	public List<Carrier> getFedexShipment(String carrierServiceID) {
		// where condition >careerServiceID= "fedexAIR" || "fedexGroud"
		return dataManager.getShipmentFedexByCarrierServiceID(carrierServiceID);
	}

	public List<Carrier> getUPSShipment(String shipmentServiceID) {
		// where condition >careerServiceID="UPSExpress" || "UPS2DAY"
		return dataManager.getShipmentUPSByShipmentServiceID(shipmentServiceID);
	}

	public int getFedexUndeliveredShipment(int carrierType){
		// where carrierType = 1
		return dataManager.getUndeliveredShipmentsForFedex(carrierType);
	}
	public int getUPSUndeliveredShipment(int carrierType){
		// where carrierType = 3
		return dataManager.getUndeliveredShipmentsForUPS(carrierType);
	}

	public boolean markShipmentAsDelivered(int id) {
		Carrier carrier = dataManager.getShipmentById(id);
		carrier.setIsDelivered(true);
		dataManager.saveCarrier(carrier);
		return true;
	}

	public int getFedexDeliveredShipment(int carrierType){
		// where carrierType = 1
		return dataManager.getDeliveredShipmentFedex(carrierType);
	}
	public int getUPSDeliveredShipment(int carrierType){
		// where carrierType = 3
		return dataManager.getDeliveredShipmentUPS(carrierType);
	}
	private CarrierForm setPackageDetails(String carrierServiceID, CarrierForm obj) {
		if (carrierServiceID.contains("fedexAIR") || carrierServiceID.contains("fedexGroud")) {
			obj.setCarrierServiceID();
			return obj;
		}
		else {
			return null;
		}
	}

	private ShipmentForm setPackage(String shipmentServiceID, ShipmentForm obj) {
		if (shipmentServiceID.contains("UPSExpress") || shipmentServiceID.contains("UPS2DAY")) {
			obj.getShipmentServiceID();
			return obj;
		}
		else {
			return null;
		}
	}

	public boolean deleteShipment(int id) {
		Carrier carrier = dataManager.getShipmentById(id);
		carrier.setIsDeleted(true);
		dataManager.save(carrier);
		return true;
	}
	private String constructResponse(Object data, String message) {
		ApiResponse res = new ApiResponse();
		res.setData(data);
		res.setMessage(message);
		return GsonProvider.getGson().toJson(res);
	}

}
