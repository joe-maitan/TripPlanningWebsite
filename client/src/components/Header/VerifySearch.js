import { sendAPIRequest } from '@utils/restfulAPI';
import { LOG } from '@utils/constants';
import { isFeatureImplemented } from '@utils/restfulAPI';

async function verifySearch(props) {
	if (!isFeatureImplemented(props.serverSettings, "find")){
	  props.setFoundPlaces([]);
	  return;
	}
	const requestBody = {
	  requestType: "find",
	  match: props.coordString,
	  limit: 5,
	};
	if(props.selectedCountry != 'All Countries'){
	  requestBody.where = [props.selectedCountry];
	}
	if(props.selectedType != 'All Types'){
	  requestBody.type = [props.selectedType];
	}
	const findResponse = await sendAPIRequest(requestBody, props.serverSettings.serverUrl);
	if(findResponse){
	  props.setFoundPlaces(findResponse.places);
	}
	else{
	  LOG.error(`Find request to ${props.serverSettings.serverUrl} failed. Check the log for more details.`, "error");
	}
  }

async function verifyNear(props, lat, lng){
	if(!isFeatureImplemented(props.serverSettings, "near")){
		props.setFoundPlaces([]);
		return;
	}
	const requestBody = {
		requestType: "near",
		place: {"latitude": lat + '', "longitude": lng + ''},
		earthRadius: 3959,
		distance: 50,
		limit: 7
	};
	const nearResponse = await sendAPIRequest(requestBody, props.serverSettings.serverUrl);
	if(nearResponse){
		props.setFoundPlaces(nearResponse.places);
		props.setDistances(nearResponse.distances);
	}
	else{
		props.setFoundPlaces([]);
		props.setDistances([]);
		LOG.error(`Near request to ${props.serverSettings.serverUrl} failed. Check the log for more details.`, "error");
	}
}
  export {verifySearch, verifyNear};