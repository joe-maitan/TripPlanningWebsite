import React from 'react';
import { SnackbarProvider, useSnackbar } from 'notistack';
import Page from './Page';

export default function App() {
    return (
        <SnackbarProvider maxSnack={3} preventDuplicate>
            <HookCaller />
        </SnackbarProvider>
    );
}

export const HookCaller = () => {
    const { enqueueSnackbar } = useSnackbar();

    function showMessage(message, variant = "info") {
        enqueueSnackbar(message, { variant: variant })
    }

    return <Page showMessage={showMessage} />;
};
