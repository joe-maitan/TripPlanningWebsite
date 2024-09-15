import React from 'react';
import { render, screen, cleanup } from '@testing-library/react';
import '@testing-library/jest-dom';
import user from '@testing-library/user-event';
import { beforeEach, describe, expect, test } from '@jest/globals';
import { MOCK_PLACES } from '../../sharedMocks';
import Itinerary from '@components/Trip/Itinerary/Itinerary.js';
import { isFeatureImplemented, sendAPIRequest } from '@utils/restfulAPI';
import { OptimizeButtons } from '@components/Trip/Itinerary/Itinerary.js';
import { convertObjectToPlace } from '@components/Trip/Itinerary/Itinerary';
import { Place } from '@models/place.model';
import { optimizeTrip } from '@components/Trip/Itinerary/Itinerary';

jest.mock('@utils/restfulAPI', () => ({
    isFeatureImplemented: jest.fn(),
	sendAPIRequest: jest.fn()
}));

describe('Itinerary', () => {
	const placeActions = { append: jest.fn(), selectIndex: jest.fn() };
	const props = {
		places: ['place1', 'place2'], // Sample places data
		serverSettings: {
		  serverUrl: 'http://example.com', // Sample server URL
		},
		placeActions: {
		  setPlaces: jest.fn(), // Mocking the setPlaces function
		},
	  };

	beforeEach(() => {
		render(
			<Itinerary
				places={MOCK_PLACES}
				placeActions={placeActions}
				selectedIndex={0}
			/>
		);
	});

	test('base: renders the name attribute', () => {
		screen.getByRole('cell', { name: /Place A/i });
	});

	test('base: sets new index when clicked.', () => {
		const row = screen.getByTestId('place-row-0');
		expect(placeActions.selectIndex).toBeCalledTimes(0);

		user.click(row);
		expect(placeActions.selectIndex).toBeCalledTimes(1);
	});

	test('base: expands a place row when clicked.', () => {
		const row = screen.getByTestId('place-row-2');
		expect(screen.getByText(/123 Test/i)).toBeTruthy();

		user.click(row);
		expect(screen.getByText(/expanded test/i)).toBeTruthy();
	});

	test('base: expands a place row when button is clicked.', () => {
		const toggle = screen.getByTestId('place-row-toggle-2');
		expect(screen.getByText(/123 Test/i)).toBeTruthy();

		user.click(toggle);
		expect(screen.getByText(/expanded test/i)).toBeTruthy();
	});

	test('base: renders leg, distance, and cumulative', () => {
		const leg = screen.getByTestId('leg-header');
		const cumulative = screen.getByTestId('cumulative-header');
		const total = screen.getByTestId('total-distance');
		expect(leg).toBeTruthy();
		expect(cumulative).toBeTruthy();
		expect(total).toBeTruthy();
	})

	test('base: renders optimize button', () =>{
		cleanup();
		isFeatureImplemented.mockReturnValueOnce(true);
		render(<OptimizeButtons
			places={MOCK_PLACES}
			placeActions={placeActions}
			selectedIndex={0}
		/>)
		const optimize = screen.getByTestId('optimize-button');
		expect(optimize).toBeTruthy();
	})

	test('base: renders nothing', () =>{
		cleanup();
		isFeatureImplemented.mockReturnValueOnce(false);
		render(<OptimizeButtons
			places={MOCK_PLACES}
			placeActions={placeActions}
			selectedIndex={0}
		/>)
		const optimize = screen.queryByTestId('optimize-button');
		expect(optimize).not.toBeInTheDocument();
	})

	test('should convert object to place', () => {
		const inputObject = {
		  name: 'Place 1',
		  latitude: 123,
		  longitude: 456,
		  municipality: 'Municipality 1',
		  country: 'Country 1',
		};
	
		const expectedPlace = new Place({
		  name: 'Place 1',
		  latitude: 123,
		  longitude: 456,
		  municipality: 'Municipality 1',
		  country: 'Country 1',
		});
	
		const result = convertObjectToPlace(inputObject);
		expect(result).toEqual(expectedPlace);
	  });

  test('should call setPlaces with converted places data if response is received', async () => {
    const mockResponse = {
      places: [{ name: 'place1' }, { name: 'place2' }]
    };
    sendAPIRequest.mockResolvedValueOnce(mockResponse);

    await optimizeTrip(props);

    expect(props.placeActions.setPlaces).toHaveBeenCalledTimes(1);
  });
});
