import * as tripFileSchema from '../../schemas/TripFile.json';
import { isJsonResponseValid } from './restfulAPI';
import { Place } from '@models/place.model';

export async function LoadPlaces(props, foundTrip) {
    if (isValidJsonFile(foundTrip)) {
        LoadJsonFile(props, foundTrip);
    } else {
        TripLoadErrorMessage(props);
    }
}

function isValidJsonFile(tripString){
    try{
        const tripObject = JSON.parse(tripString);
        return isJsonResponseValid(tripObject, tripFileSchema);
    } catch {
        return false;
    }
}

function LoadJsonFile(props, tripString){
    const tripObject = JSON.parse(tripString);
    const places = makeJsonPlacesList(tripObject);
    TripLoadValidMessage({places}, props);
}

function makeJsonPlacesList(tripObject){
    var places = [];
    for(let index in tripObject.places){
        var place = tripObject.places[index];
        places.push(new Place(place));
    }

    return places;
}

function TripLoadErrorMessage(props){
    props.setDisallowLoad(true);
    props.setShowValidityIcon(true);
    props.setFileValidity(false);
}

function TripLoadValidMessage({places}, props){
    props.setLoadedPlace(places);
    props.setShowValidityIcon(true);
    props.setFileValidity(true);
    props.setDisallowLoad(false);
}