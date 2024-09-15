import { beforeEach, describe, expect, test } from "@jest/globals";
import { VALID_CONFIG_RESPONSE, INVALID_REQUEST } from '../sharedMocks';
import { LOG } from '@utils/constants';
import { getOriginalServerUrl, sendAPIRequest } from '@utils/restfulAPI';

describe('restfulAPI', () => {
    beforeEach(() => {
        jest.clearAllMocks();
        fetch.resetMocks();
        jest.spyOn(LOG, 'error').mockImplementation(() => {});
    });

    test('base: sendAPIRequest handles valid server response', async () => {
        fetch.mockResponse(VALID_CONFIG_RESPONSE);
        const response = await sendAPIRequest(JSON.parse(VALID_CONFIG_RESPONSE), 'http://localhost:31400');
        expect(response).toEqual(JSON.parse(VALID_CONFIG_RESPONSE));
    });

    test('base: sendAPIRequest response is not ok', async () => {
        fetch.mockResponse(INVALID_REQUEST, { status: 404, ok: false, statusText: 'This is what we expect' });
        const response = await sendAPIRequest(JSON.parse(VALID_CONFIG_RESPONSE), 'http://localhost:31400');
        expect(response).toBeNull();
        expect(LOG.error.mock.calls.length).toBeGreaterThanOrEqual(1);
    });

    test('base: sendAPIRequest response is rejected', async () => {
        fetch.mockReject(new Error('Expected rejection'));
        const response = await sendAPIRequest(JSON.parse(VALID_CONFIG_RESPONSE), 'http://localhost:31400');
        expect(response).toBeNull();
        expect(LOG.error.mock.calls.length).toBeGreaterThanOrEqual(1);
    });

    test('base: sendAPIRequest schema not implemented', async () => {
        fetch.mockResponse(VALID_CONFIG_RESPONSE);
        expect(sendAPIRequest({ requestType: 'notValid' })).rejects.toThrow();
    });

    test('base: sendAPIRequest gives null response for invalid request', async () => {
        fetch.mockResponse(INVALID_REQUEST);
        const response = await sendAPIRequest(JSON.parse(VALID_CONFIG_RESPONSE), 'http://localhost:31400');
        expect(response).toBeNull();
        expect(LOG.error.mock.calls.length).toBeGreaterThanOrEqual(1);
    });

    test('base: getOriginalServerUrl', async () => {
        process.env.SERVER_PORT = '3113';
        expect(getOriginalServerUrl()).toEqual('http://localhost:3113');
    });
});