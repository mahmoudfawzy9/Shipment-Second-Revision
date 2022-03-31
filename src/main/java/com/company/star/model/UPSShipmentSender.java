package com.company.star.model;

import java.util.*;

import com.company.star.exception.FcmException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.company.star.db.model.Shipment;
import com.company.star.db.model.ShipmentStatus;

public class UPSShipmentSender extends ShipmentSender {
	private static final Logger l = LoggerFactory.getLogger(UPSShipmentSender.class);

	public UPSShipmentSender(Shipment shipment, ShipmentStatus shipmentStatus, String carrier2, Map<String, String> data) {
		super();
	}

	public UPSShipmentSender(){

	}


	@Override
	public void sendShipment(Shipment shipment, ShipmentStatus shipmentStatus, String carrier, Map<String, String> data) {

	}

	@Override
	public void sendShipmentUPS(Shipment shipment, ShipmentStatus shipmentStatus, String carrier,
							 Map<String, String> data) {
		ResponseEntity<String> response = getShipmentDataManager().sendShipmentUPS(shipment,
				shipmentStatus, carrier, data);

		if (response.getStatusCode() != HttpStatus.OK) {
			JSONObject json = new JSONObject(response.getBody());
			String status = json.getJSONObject("error").getString("status");
			String message = json.getJSONObject("error").getString("message");

			l.debug("Shipment Status: " + status);

			shipmentStatus.setStatus(status);

			getShipmentDataManager().saveShipment(shipment);

			throw new FcmException(response.getStatusCode(), message, null, shipmentStatus.getCarrierId());
		} else {
			// shipment is sent successfully
			shipmentStatus.setStatus("success");

			getShipmentDataManager().saveShipmentStatus(shipmentStatus);
		}

		l.debug("Done sending shipment");
	}

}
