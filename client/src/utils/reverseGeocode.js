import { Place } from "@models/place.model";
import {latLngToPlace} from "./transformers";

const GEOCODE_URL = "https://nominatim.openstreetmap.org/reverse?format=jsonv2";

async function reverseGeocode(latLng) {
    // Here the coordinates are in latLng format - example: {lat: 0, lng: 0}
    const API_URL = GEOCODE_URL + `&lat=${latLng.lat ?? latLng.latitude}&lon=${latLng.lng ?? latLng.longitude}`;
    const data = await fetch(API_URL);
    const dataJSON = await data.json();
    
    const formattedLatLng = latLngToPlace(latLng);
    if (dataJSON.display_name) {
        const newPlace = new Place({...formattedLatLng, ...dataJSON.address});
        return newPlace;
    } else {
        const unknownPlace = new Place({name: 'Unkown', ...formattedLatLng});
        return unknownPlace;
    }
}

export {reverseGeocode};