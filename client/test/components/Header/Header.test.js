import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { CLIENT_TEAM_NAME } from '@utils/constants';
import { VALID_CONFIG_RESPONSE } from '../../sharedMocks';
import { beforeEach, describe, expect, test, jest } from '@jest/globals';
import Header from '@components/Header/Header';

describe('Header', () => {
	const headerProps = {
		toggleAbout: jest.fn(),
		placeActions: {
			append: jest.fn(),
			removeAll: jest.fn(),
		},
		disableRemoveAll: true,
		serverSettings: {
			serverConfig: { requestType: 'config', serverName: 't25' },
			serverUrl: 'http://localhost:8000',
		},
		processServerConfigSuccess: jest.fn(),
	};

	beforeEach(() => {
		fetch.resetMocks();
		fetch.mockResponse(VALID_CONFIG_RESPONSE);
		render(<Header {...headerProps} />);
	});

	test('base: renders the team name in header', async () => {
		await waitFor(() => {
			const headings = screen.getAllByRole('heading', { name: /T[0-9][0-9]/i });
			expect(headings[0].textContent).toEqual(CLIENT_TEAM_NAME);
		});
	});
});
