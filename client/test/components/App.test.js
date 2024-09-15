import React from 'react';
import { render, screen } from '@testing-library/react';
import { beforeEach, describe, test } from '@jest/globals';
import { LOG } from '@utils/constants';
import App from '@components/App';

describe('App', () => {
    beforeEach(() => {
        fetch.resetMocks();
    });

    test('base: shows error snackbar if no server config', async () => {
        jest.spyOn(LOG, 'error').mockImplementation(() => {});
        fetch.mockReject(() => Promise.reject("API is down (expected)."));

        render(<App />);

        await screen.findByText(/failed/i);
    });
});

