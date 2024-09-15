import { describe, expect, test } from "@jest/globals";
import { setLogLevelIfDefault } from '@utils/constants'
import { ulog } from 'ulog/ulog';

describe('Constants', () => {
    test('base: log level is correctly set', () => {
        setLogLevelIfDefault();
        expect(ulog.level).toEqual(ulog.ERROR);
    });

    test('base: log level gets set to info with env var', () => {
        process.env.CLIENT_LOG_LEVEL = 'INFO';
        setLogLevelIfDefault();
        expect(ulog.level).toEqual(ulog.INFO);
    });
});