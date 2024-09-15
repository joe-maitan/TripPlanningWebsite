import React from 'react';
import { beforeEach, describe, expect, test } from '@jest/globals';
import user from '@testing-library/user-event';
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import AddPlace from '@components/Header/AddPlace';
import { DEFAULT_STARTING_POSITION } from '@utils/constants';
import { sendAPIRequest, isFeatureImplemented } from '@utils/restfulAPI';
import { TypeMenu } from '@components/Header/AddPlace';
import { WhereMenu } from '@components/Header/AddPlace';
import { getWhereOptions } from '@components/Header/AddPlace';
import {
	REVERSE_GEOCODE_RESPONSE,
	MOCK_PLACE_RESPONSE,
} from '../../sharedMocks';

jest.mock('@utils/restfulAPI', () => ({
    isFeatureImplemented: jest.fn(),
	sendAPIRequest: jest.fn()
}));

describe('AddPlace', () => {
	const placeObj = {
		latLng: '40.57, -105.085',
		name: 'Colorado State University, South College Avenue, Fort Collins, Larimer County, Colorado, 80525-1725, United States',
	};

	const props = {
		toggleAddPlace: jest.fn(),
		placeActions: {
			append: jest.fn(),
		},
		showAddPlace: true,
		setSelectedType: jest.fn(),
		selectedType: "heliport",
		countries: ["United States"],
		setCountries: jest.fn(),
		setSelectedCountry: jest.fn(),
		selectedCountry: "Albania",
		serverSettings: {serverUrl: "example.com"}
	};

	beforeEach(() => {
		render(
			<AddPlace
				placeActions={props.placeActions}
				showAddPlace={props.showAddPlace}
				toggleAddPlace={props.toggleAddPlace}
			/>
		);
	});

	test('base: validates input', async () => {
		const coordInput = screen.getByTestId('coord-input');
		user.type(coordInput, placeObj.latLng);

		await waitFor(() => {
			expect(coordInput.value).toEqual(placeObj.latLng);
		});
	});

	test('base: adds home', async () => {
		const homeButton = screen.getByTestId('home-button');
		user.click(homeButton);

		await waitFor(() => {
			expect(props.placeActions.append).toHaveBeenCalledWith(DEFAULT_STARTING_POSITION);;
		});
	});

	test('base: handles invalid input', async () => {
		const coordInput = screen.getByTestId('coord-input');
		user.paste(coordInput, '1');

		await waitFor(() => {
			expect(coordInput.value).toEqual('1');
		});

		const addButton = screen.getByTestId('add-place-button');
		expect(addButton.classList.contains('disabled')).toBe(true);
	});

	test('base: Adds place', async () => {
		fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);
		const coordInput = screen.getByTestId('coord-input');
		user.type(coordInput, placeObj.latLng);

		await waitFor(() => {
			expect(coordInput.value).toEqual(placeObj.latLng);
		});

		const addButton = screen.getByTestId('add-place-button');
		expect(addButton.classList.contains('disabled')).toBe(false);
		await waitFor(() => {
			user.click(addButton);
		});
		expect(props.placeActions.append).toHaveBeenCalledWith(MOCK_PLACE_RESPONSE);
		expect(coordInput.value).toEqual('');
	});

	test('base: renders typeMenu', () => {
		cleanup();
		isFeatureImplemented.mockResolvedValueOnce(true);
		render(<TypeMenu {...props}/>);
		const dropdown = screen.getByTestId('type-dropdown');
		expect(dropdown).toBeTruthy();
		user.click(dropdown);
		expect(screen.getByText(/Airport/i)).toBeTruthy();
		user.click(screen.getByText(/Airport/i));
		expect(props.setSelectedType).toBeCalledTimes(1);
	});

	test('base: renders whereMenu', () => {
		cleanup();
		isFeatureImplemented.mockResolvedValueOnce(true);
		render(<WhereMenu {...props}/>);
		const dropdown = screen.getByTestId('where-dropdown');
		expect(dropdown).toBeTruthy();
		user.click(dropdown);
		expect(screen.getByText(/United States/i)).toBeTruthy();
	})

	test('base: whereOptions', async() => {
		cleanup();
		sendAPIRequest.mockResolvedValueOnce({where: ["United States"]});
		await getWhereOptions(props);
		expect(props.setCountries).toBeCalledWith(["United States"]);
	})
});
