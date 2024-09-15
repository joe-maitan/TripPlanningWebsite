import { useState, useEffect } from 'react';
import { sendAPIRequest, isFeatureImplemented, isRequestNotSupported } from '@utils/restfulAPI';
import { EARTH_RADIUS_UNITS_DEFAULT } from '@utils/constants';

export function useDistances(places, earthRadius, serverSettings) {
    const [leg, setLeg] = useState([]);
    const [cumulative, setCumulative] = useState([]);
    const [total, setTotal] = useState(0);

    const distances = {
      leg: leg,
      cumulative: cumulative,
      total: total
    }
    
    const distanceActions = {
      setLeg: setLeg,
      setCumulative: setCumulative,
      setTotal: setTotal
    }
    
    useEffect(() => {makeDistancesRequest(places, earthRadius, serverSettings, distanceActions);},
              [places,earthRadius, serverSettings])
  
    return {distances};
}

async function makeDistancesRequest(places, earthRadius, serverSettings, distanceActions) {  

  const {setLeg, setCumulative, setTotal} = distanceActions;

  const requestBody = { requestType: "distances", places: places, earthRadius:earthRadius };
  if(isFeatureImplemented(serverSettings,"distances")){
    const distancesResponse = await sendAPIRequest(requestBody, serverSettings.serverUrl);
    setLeg(distancesResponse.distances);
    setCumulative(calcCumulative(distancesResponse.distances));
    setTotal(calcTotal(distancesResponse.distances));
  }

  else{ 
    let list_of_zero = places.map((place) => (0))
    setLeg(list_of_zero);
    setCumulative(list_of_zero)
    setTotal(0)  
  }
}

function calcCumulative(distances){
  let sum = 0;
  return distances.map((sum = 0, n => sum += n));
}

function calcTotal(distances){
  let currentValue = 0
  return distances.reduce((accumulator, currentValue)=>accumulator + currentValue, currentValue);
}

