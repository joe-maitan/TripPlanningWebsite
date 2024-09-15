import Ajv from 'ajv';
import * as distancesSchema from '../../schemas/DistancesResponse';
import * as configSchema from '../../schemas/ConfigResponse';
import * as findSchema from '../../schemas/FindResponse';
import * as tourSchema from '../../schemas/TourResponse.json';
import * as nearSchema from '../../schemas/NearResponse.json';
import { LOG } from './constants';

const SCHEMAS = {
    config: configSchema,
    distances: distancesSchema,
    find: findSchema,
    tour: tourSchema,
    near: nearSchema
}

export async function sendAPIRequest(requestBody, serverUrl) {
    const response = await sendRequest(requestBody, serverUrl);
        
    if (isRequestNotSupported(requestBody)) {
        throw new Error(`sendAPIRequest() does not have support for type: ${requestBody.requestType}. Please add the schema to 'SCHEMAS'.`);
    }
    if (isJsonResponseValid(response, SCHEMAS[requestBody.requestType])) {
        return response;
    }
    LOG.error(`Server ${requestBody.requestType} response json is invalid. Check the Server.`);
    return null;
}

export function isRequestNotSupported(requestBody){
    return (!Object.keys(SCHEMAS).includes(requestBody.requestType));
}

async function sendRequest(requestBody, serverUrl) {
    const fetchOptions = {
        method: "POST",
        body: JSON.stringify(requestBody)
    };

    try {
        const response = await fetch(`${serverUrl}/api/${requestBody.requestType}`, fetchOptions);
        if (response.ok) {
            return response.json();
        } else {
            LOG.error(`Request to server ${serverUrl} failed: ${response.status}: ${response.statusText}`);
        }

    } catch (err) {
        LOG.error(`Request to server failed : ${err}`);
    }

    return null;
}

export function getOriginalServerUrl() {
    const serverProtocol = location.protocol;
    const serverHost = location.hostname;
    const serverPort = location.port;
    const alternatePort = process.env.SERVER_PORT;
    return `${serverProtocol}\/\/${serverHost}:${(!alternatePort ? serverPort : alternatePort)}`;
}

export function isJsonResponseValid(object, schema) {
    if (object && schema) {
        const anotherJsonValidator = new Ajv();
        const validate = anotherJsonValidator.compile(schema);
        return validate(object);
    }
    LOG.error(`bad arguments - isJsonResponseValid(object: ${object}, schema: ${schema.title})`);
    return false;
}

export function isFeatureImplemented(serverSettings,featureToCheck){
    if(serverSettings == null) {
        return false;
    }
    if(serverSettings.serverUrl != null && serverSettings.serverConfig != null && serverSettings.serverConfig.features.includes(featureToCheck)){
        return true;
    }
    else {
      return false;
    }
  }
