import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import user from '@testing-library/user-event';
import { beforeEach, describe, expect, test, jest } from '@jest/globals';
import PlaceActions from '@components/Trip/Itinerary/PlaceActions';

describe('PlaceActions', () => {
	const props = {
		index: jest.fn(),
		placeActions: {
			removeAtIndex: jest.fn(),
		},
	};

	beforeEach(() => {
		render(<PlaceActions {...props} />);
	});

	test('base: calls remove at index on click', () => {
		const removePlaceButton = screen.getByTestId('remove-place-button');
		expect(props.placeActions.removeAtIndex).toBeCalledTimes(0);
		user.click(removePlaceButton);
		expect(props.placeActions.removeAtIndex).toBeCalledTimes(1);
	});
});
