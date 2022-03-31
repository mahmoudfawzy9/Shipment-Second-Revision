package com.company.star.controller;

import java.math.BigDecimal;
import java.util.List;

import com.company.star.db.model.Carrier;
import com.company.star.db.model.Shipment;
import com.company.star.form.ShipmentForm;
import com.company.star.service.CarrierService;
import com.company.star.service.ShipmentService;
//import com.company.star.service.CarrierService;
import com.company.star.utils.ApiResponse;
import com.company.star.utils.GsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.company.star.form.CarrierForm;

@RestController
@RequestMapping(path = "/api/json/shipments")
public class ShipmentController {

	@Autowired
	CarrierService carrierService;
	@Autowired
    ShipmentService shipmentService;

	private static final String SUCCESS = "success";

	// ------------------------POST--------------------------

	//FEDEX
	@PostMapping(path = "/carrier", produces = "application/json")
	public String getCarrier(@RequestBody CarrierForm newCarrier) {
		return carrierService.saveCarrier(newCarrier);
	}
	//UPS
	@PostMapping(path = "/shipment",produces = "application/json")
	public String postShipmentUPSSent(@Validated @RequestBody ShipmentForm carrier) {
		return carrierService.saveShipment(carrier);
	}
	//ups shipment delivered
	@PostMapping(path = "/{shipmentId}/is-delivered", produces = "application/json")
	public String markUPSShipmentAsDelivered(@PathVariable int shipmentId) {
		boolean done = carrierService.markShipmentAsDelivered(shipmentId);
		return constructResponse(done, SUCCESS);
	}

	//fedex shipment marked as delivered
	@PostMapping(path = "shipments/{carrierId}/is-delivered", produces = "application/json")
	public String markFedexShipmentAsDelivered(@PathVariable int carrierId) {
		boolean done = carrierService.markShipmentAsDelivered(carrierId);
		return constructResponse(done, SUCCESS);
	}
	// --------------------------------GET----------------------------------------------
	@GetMapping(path = "/", produces = "application/json")
	public String getAllShipments() {
		List<Carrier> carriers = carrierService.getAllShipments();
		return constructResponse(carriers, SUCCESS);
	}

	@GetMapping(path = "/shipments/{shipmentServiceID}", produces = "application/json")
	public String getUPSShipment(@PathVariable String shipmentServiceID) {
		List<Carrier> upsShipment = carrierService.getUPSShipment(shipmentServiceID);
		return constructResponse(upsShipment, SUCCESS);
	}
	@GetMapping(path = "/{carrierServiceID}", produces = "application/json")
	public String getFedexShipment(@PathVariable String carrierServiceID) {
		List<Carrier> upsShipment = carrierService.getFedexShipment(carrierServiceID);
		return constructResponse(upsShipment, SUCCESS);
	}

	@GetMapping(path = "/ups-shipment-object/{shipmentServiceID}", produces = "application/json")
	public Carrier getUPSShipmentByID(@PathVariable int shipmentServiceID){
		Carrier ups = carrierService.getShipmentById(shipmentServiceID);
		return ups;
	}

	@GetMapping(path = "/fedex-shipment-object/{carrierServiceID}", produces = "application/json")
	public Carrier getFedexShipmentByID(@PathVariable int carrierServiceID){
		Carrier fedex = carrierService.getShipmentById(carrierServiceID);
		return fedex;
	}


	@GetMapping(path = "/shipment-by-weight/{weight}", produces = "application/json")
	 String getUPSShipmentByWeight(@PathVariable BigDecimal weight){
		List<Carrier> ups = carrierService.getRecordByWeight(weight);
		return constructResponse(ups, SUCCESS);
	}

	@GetMapping(path = "shipment/shipment-by-weight/{weight}", produces = "application/json")
	String getFedexShipmentByWeight(@PathVariable BigDecimal weight){
		List<Carrier> fedex = carrierService.getRecordByWeight(weight);
		return constructResponse(fedex, SUCCESS);
	}

	@GetMapping(path = "/{shipmentServiceID}/count", produces = "application/json")
	public String getFedexUndeliveredShipmentCount(@PathVariable int shipmentServiceID) {
		int count = carrierService.getFedexUndeliveredShipment(shipmentServiceID);
		return constructResponse(count, SUCCESS);
	}

	@GetMapping(path = "/shipments/{carrierServiceId}/count", produces = "application/json")
	public String getUPSUndeliveredShipmentCount(@PathVariable int carrierServiceId) {
		int count = carrierService.getUPSUndeliveredShipment(carrierServiceId);
		return constructResponse(count, SUCCESS);
	}
	@GetMapping(path = "/delivered/{shipmentServiceID}/count", produces = "application/json")
	public String getFedexDeliveredShipmentCount(@PathVariable int shipmentServiceID) {
		int count = carrierService.getFedexDeliveredShipment(shipmentServiceID);
		return constructResponse(count, SUCCESS);
	}

	@GetMapping(path = "/shipments/delivered/{carrierServiceId}/count", produces = "application/json")
	public String getUPSDeliveredShipmentCount(@PathVariable int carrierServiceId) {
		int count = carrierService.getUPSDeliveredShipment(carrierServiceId);
		return constructResponse(count, SUCCESS);
	}


	@GetMapping(path = "/check-confirmed/{shipmentId}",produces = "application/json")
	boolean checkConfirmed(@PathVariable Carrier shipmentId){
		return carrierService.checkShipmentIsConfirmed(shipmentId);
	}

	@DeleteMapping(path = "/{id}", produces = "application/json")
	@ResponseBody
	public String deleteCarrier(@PathVariable int id) {
		boolean isDeleted = carrierService.deleteShipment(id);
		String message = "SHIPMENT_WITH_ID" + id + " was deleted successfully";
		return constructResponse(isDeleted, message);
	}

	//		@GetMapping(path = "/{shipmentId}/check-confirmed",produces = "application/json")
//		List<Carrier> checkConfirmed(@PathVariable int shipmentId, int carrierType){
//		return carrierService.getRecordByConfirmed(shipmentId, carrierType);
//	}

//	@GetMapping(path = "/{shipmentId}", produces = "application/json")
//	public String getShipmentById(@PathVariable int shipmentId) {
//		Shipment shipment = shipmentService.getShipmentById(shipmentId);
//		return constructResponse(shipment, SUCCESS);
//	}


//	public Carrier addCarrier(@Validated @RequestBody Carrier carrier){
//		return carrierService.addCarrier(message);
//	}

//	@PostMapping(path = "/carrier", produces = "application/json")
//	public String getCarrier(@RequestBody ShipmentForm newCarrier) {
//		return shipmentService.sendShipment(newCarrier);
//	}

//UPS
//	@PostMapping(produces = "application/json")
//	public String confirmShipmentSent(@RequestBody CarrierForm shipment) {
//		return carrierService.saveCarrier(shipment);
//	}

//	@PostMapping(path = "/confirm", produces = "application/json")
//	public String logout(@RequestBody PackageDetails dto) {
//		return shipmentService.sendShipment((ShipmentForm) dto);
//	}

	// ---------------------------------------------------
	private String constructResponse(Object data, String message) {
		ApiResponse res = new ApiResponse();
		res.setData(data);
		res.setMessage(message);
		return GsonProvider.getGson().toJson(res);
	}


//	private String constructResponse(boolean success, String message) {
//		ApiResponse res = new ApiResponse();
//		res.setSuccess(success);
//		res.setMessage(message);
//		return GsonProvider.getGson().toJson(res);
//	}
}
