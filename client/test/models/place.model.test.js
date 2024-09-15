import { describe, expect, test } from '@jest/globals';
import { Place } from '@models/place.model';

describe('place model', () => {
	test('base: handles postcode', () => {
		const postcodeTest = new Place({
			latitude: '0.0',
			longitude: '0.0',
			postcode: '12345',
		});
		expect(postcodeTest.formatPlace()).toEqual('12345');
	});

	test('base: handles street address', () => {
		const placeWithName = new Place({
			name: 'Test',
			suburb: 'Test Suburb',
		});
		expect(placeWithName.formatPlace()).toEqual('Test, Test Suburb');

		const placeNoName = new Place({
			suburb: 'Test Suburb',
		});
		expect(placeNoName.formatPlace()).toEqual('Test Suburb');
	});

	test('base: handles municipality', () => {
		const placeWithAddy = new Place({
			suburb: 'Test Suburb',
			city: 'Fort Collins',
		});

		expect(placeWithAddy.formatPlace()).toEqual('Test Suburb, Fort Collins');

		const placeNoAddy = new Place({
			city: 'Fort Collins',
		});
		expect(placeNoAddy.formatPlace()).toEqual('Fort Collins');
	});

	test('base: handles region', () => {
		const placeWithCity = new Place({
			city: 'Fort Collins',
			state: 'Colorado',
		});
		expect(placeWithCity.formatPlace()).toEqual('Fort Collins, Colorado');

		const placeNoCity = new Place({
			state: 'Colorado',
		});
		expect(placeNoCity.formatPlace()).toEqual('Colorado');
	});

	test('base: handles country', () => {
		const placeWithRegion = new Place({
			state: 'Test State',
			country: 'Test Country',
		});
		expect(placeWithRegion.formatPlace()).toEqual('Test State, Test Country');

		const placeNoRegion = new Place({
			country: 'Test Country',
		});
		expect(placeNoRegion.formatPlace()).toEqual('Test Country');
	});
});
