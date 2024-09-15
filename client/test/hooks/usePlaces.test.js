import '@testing-library/jest-dom';
import {act, renderHook} from '@testing-library/react-hooks';
import { beforeEach, describe, expect, test } from '@jest/globals';
import { REVERSE_GEOCODE_RESPONSE, MOCK_PLACE_RESPONSE } from '../sharedMocks';
import { LOG } from '@utils/constants';
import { usePlaces } from '@hooks/usePlaces';

describe('usePlaces', () => {
    const mockLatLng = { lat: 40.570, lng: -105.085 };
    
    let hook;

    beforeEach(() => {
        jest.clearAllMocks();
        fetch.resetMocks();
        const { result } = renderHook(() => usePlaces());
        hook = result;
    });

    test('base: appends a place', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);
        expect(hook.current.places).toEqual([]);

        await act(async () => {
            hook.current.placeActions.append(mockLatLng);
        });

        expect(hook.current.places).toEqual([MOCK_PLACE_RESPONSE]);
    });

    test('base: selects a place at an index', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        await act(async () => {
            hook.current.placeActions.append(mockLatLng);
        });
        expect(hook.current.selectedIndex).toEqual(0);

        await act(async () => {
            hook.current.placeActions.append(mockLatLng);
        });
        expect(hook.current.selectedIndex).toEqual(1);
        expect(hook.current.places.length).toEqual(2);

        act(() => {
            hook.current.placeActions.selectIndex(0);
        });
        expect(hook.current.selectedIndex).toEqual(0);
    });

    test('base: sets index to -1 if selecting invalid index', () => {
        jest.spyOn(LOG, 'error').mockImplementation(() => {});

        act(() => {
            hook.current.placeActions.selectIndex(99);
        });
        expect(hook.current.selectedIndex).toEqual(-1);
        expect(LOG.error.mock.calls.length).toEqual(1);
    });

    test('base: removes a place at an index', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        await act(async () => {
            hook.current.placeActions.append(mockLatLng);
        });
        expect(hook.current.places.length).toEqual(1);

        act(() => {
            hook.current.placeActions.removeAtIndex(0);
        });
        expect(hook.current.places).toEqual([]);
        expect(hook.current.selectedIndex).toEqual(-1);
    });

    test('base: sets preceding place as selected when removing an index', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        for (let i = 0; i < 5; i++) {
            await act(async () => {
                hook.current.placeActions.append(mockLatLng);
            });
        }

        act(() => {
            hook.current.placeActions.removeAtIndex(4);
        });
        expect(hook.current.places.length).toEqual(4);
        expect(hook.current.selectedIndex).toEqual(3);
    });

    test('base: keeps selected index at 0 if index 0 is selected and removed', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        for (let i = 0; i < 3; i++) {
            await act(async () => {
                hook.current.placeActions.append(mockLatLng);
            });
        }

        act(() => {
            hook.current.placeActions.selectIndex(0);
        });
        act(() => {
            hook.current.placeActions.removeAtIndex(0);
        });
        expect(hook.current.places.length).toEqual(2);
        expect(hook.current.selectedIndex).toEqual(0);
    });

    test('base: denies removing a place at an invalid index', () => {
        jest.spyOn(LOG, 'error').mockImplementation(() => {});

        act(() => {
            hook.current.placeActions.removeAtIndex(42);
        });
        expect(LOG.error.mock.calls.length).toEqual(1);
    });

    test('base: removes all places', async () => {
        fetch.mockResponse(REVERSE_GEOCODE_RESPONSE);

        for (let i = 0; i < 5; i++) {
            await act(async () => {
                hook.current.placeActions.append(mockLatLng);
            });
        }
        expect(hook.current.places.length).toEqual(5);

        act(() => {
            hook.current.placeActions.removeAll();
        });
        expect(hook.current.places).toEqual([]);
    });
});