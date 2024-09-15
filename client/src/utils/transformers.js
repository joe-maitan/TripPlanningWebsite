export function latLngToText(latLng, precision = 2) {
	const lat = latLng?.lat ?? latLng?.latitude;
	const lng = latLng?.lng ?? latLng?.longitude;
    return latLng && lat !== undefined && lng !== undefined ? `${lat.toFixed(precision)}°${evaluateLat(lat)}, ${lng.toFixed(precision)}°${evaluateLng(lng)}` : "";
}

export function placeToLatLng(place) {
    return place && place.latitude !== undefined && place.longitude !== undefined ? { lat: parseFloat(place.latitude), lng: parseFloat(place.longitude) } : place;
}

export function latLngToPlace(latLng) {
    return latLng && latLng.lat !== undefined && latLng.lng !== undefined ? { latitude: latLng.lat.toString(), longitude: latLng.lng.toString() } : latLng;
}

function evaluateLat(lat) {
	if (lat > 0) {
		return 'N';
	} else if (lat < 0) {
		return 'S';
	} else {
		return '';
	}
}

function evaluateLng(lng) {
	if (lng > 0) {
		return 'E';
	} else if (lng < 0) {
		return 'W';
	} else {
		return '';
	}
}