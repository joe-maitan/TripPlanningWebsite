import { describe, expect, test, jest } from "@jest/globals";
import { LoadPlaces } from '@utils/loadTrip';

describe('LoadFile' , () =>{
    const ExampleJSONObject = '{"places": [{"name": "Jargalant","latitude": "47.20","longitude": "99.16"},{"name": "Milsons Arm Road","latitude": "-32.99","longitude": "151.19"}]}'

    test('base: takes a JSON', async() => {
        const props = {
            placeActions: {
                setPlaces: jest.fn()
            },
            setShowValidityIcon: jest.fn(),
            setFileValidity: jest.fn(),
            setLoadedPlace: jest.fn(),
            setDisallowLoad: jest.fn()
        }
        await LoadPlaces(props, ExampleJSONObject);
        expect(props.setFileValidity.mock.calls[0][0]).toBe(true);
    });

    test('base: does not take invalid files', async() => {
        const props = {
            foundTrip: 12345,
            placeActions: {
                setPlaces: jest.fn()
            },
            setShowValidityIcon: jest.fn(),
            setFileValidity: jest.fn(),
            setDisallowLoad: jest.fn()
        }
        await LoadPlaces(props);
        expect(props.setFileValidity.mock.calls[0][0]).toBe(false);
    });
})