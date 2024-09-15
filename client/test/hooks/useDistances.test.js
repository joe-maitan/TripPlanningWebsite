import '@testing-library/jest-dom';
import { act, renderHook} from '@testing-library/react-hooks';
import { beforeEach, describe, expect, test } from '@jest/globals';
import { useDistances } from '@hooks/useDistances';
import { usePlaces } from '@hooks/usePlaces';
import { REVERSE_GEOCODE_RESPONSE } from '../sharedMocks';
describe('useDistances', () => {
    const mock_places_0 = [];
  
    const mock_places_1 = [
        {"name":"Place 1", "latitude": "0", "longitude": "0"}
    ]
    const mock_places_2 = [
        {"name":"Place 1", "latitude": "0", "longitude": "0"}, 
        {"name":"Place 2", "latitude": "77", "longitude": "98"}
    ]
    
    const earthRadius = 1234.5;
    const serverUrl = "serverURLExample.com";
    const serverConfig = ['config','distances']
    const serverSettings = [serverUrl, serverConfig]

    const VALID_DISTANCES_RESPONSE_0 = JSON.stringify(
        {
            "earthRadius": earthRadius,
            "places": [],
            "distances": [],
            "requestType": "distances"
        }
    );
  
    const VALID_DISTANCES_RESPONSE_1 = JSON.stringify(
        {
            "earthRadius": earthRadius,
            "places": mock_places_1,
            "distances": [0],
            "requestType": "distances"
        }
    );
  
    const VALID_DISTANCES_RESPONSE_2 = JSON.stringify(
        {
            "earthRadius": earthRadius,
            "places": mock_places_2,
            "distances": [1234, 4567],
            "requestType": "distances"
        }
    );
  
    let hook;
  
    beforeEach(() => {
        jest.clearAllMocks();
        fetch.resetMocks();
    });
    
    test('base: useDistances', async () => {
        fetch.mockResponseOnce(VALID_DISTANCES_RESPONSE_0);
      
        await act(async () => {
            const { result } = renderHook(() => useDistances(mock_places_0, earthRadius, serverSettings));
            hook = result;
        });
        
        expect(hook.current.distances).toEqual({leg:[],cumulative:[],total:0});
        // Use this to test API once server side is completed
        // expect(hook.current.distances).toEqual({leg:[],cumulative:[],total:0});

        
    });
  
    test('base: distances updates when places update with out functioning API', async () => {
        fetch.mockResponseOnce(VALID_DISTANCES_RESPONSE_0);
        await act(async () => {
            const { result } = renderHook(() => {
                const { places, selectedIndex, placeActions } = usePlaces();
                const {distances} = useDistances(places, earthRadius, serverSettings) 
                return {places, placeActions, distances}
            });
            hook = result;
        });
      
        fetch.mockResponseOnce(REVERSE_GEOCODE_RESPONSE);
        fetch.mockResponseOnce(VALID_DISTANCES_RESPONSE_1);
        await act(async () => hook.current.placeActions.append(mock_places_2[0]));
      
        expect(hook.current.places).toHaveLength(1);
        expect(hook.current.distances).toEqual({leg:[0],cumulative:[0],total:0});
        // Use this to test API once server side is completed
        //expect(hook.current.distances).toEqual({leg:[0],cumulative:[0],total:0});


      
        fetch.mockResponseOnce(REVERSE_GEOCODE_RESPONSE);
        fetch.mockResponseOnce(VALID_DISTANCES_RESPONSE_2);
        await act(async () => hook.current.placeActions.append(mock_places_2[1]));
      
        expect(hook.current.places).toHaveLength(2);
        expect(hook.current.distances).toEqual({leg:[0,0], cumulative:[0,0], total:0});
        // Use this to test API once server side is completed
        // expect(hook.current.distances).toEqual({leg:[1234,4567], cumulative:[0,1234], total:5801});

    });
    
});