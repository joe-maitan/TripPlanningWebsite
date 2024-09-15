import React from 'react';
import { describe, expect, test, jest } from "@jest/globals";
import LoadFile from '@components/Header/LoadFile';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

describe('LoadFile' , () =>{
    const props = {
        toggleLoadFile: jest.fn(),
        setDisallowLoad: jest.fn(),
        placeActions: {
            setPlaces: jest.fn()
        },
        setTripName: jest.fn()
    }

    beforeEach(() => {
        render(<LoadFile 
            showLoadFile={true} 
            toggleLoadFile={props.toggleLoadFile}
            placeActions={props.placeActions}
            setDisallowLoad={props.setDisallowLoad}
            showMessage={props.showMessage}
            setTripName={props.setTripName}
            />
        );
	});

    test('base: renders when toggles', ()=>{
        screen.getByText("Load a Trip");
    })
    
    test('base: takes a JSON file as input', async ()=>{
        const inputButton = screen.getByTestId('input-file-button');
        const fileString = '{"places": [{"name": "Ronald Reagan Washington National Airport","latitude": "38.852100","longitude": "-77.037697"}]}'
        const testFile = new File([fileString], "test.json", {type: "json"});
 
        await waitFor(() => {
            fireEvent.change(inputButton, {target: {files: [testFile]}});
        })
        const loadButton = screen.getByTestId('confirm-load-button');
        await waitFor(() => screen.getByTestId('load-validity-message'))
 
        userEvent.click(loadButton);
        expect(props.setTripName).toBeCalledWith('test');
    })
 
    test('base: takes a JSON file without extension as input', async ()=>{
        const inputButton = screen.getByTestId('input-file-button');
        const fileString = '{"places": [{"name": "Ronald Reagan Washington National Airport","latitude": "38.852100","longitude": "-77.037697"}]}'
        const testFile = new File([fileString], "test", {type: "json"});
 
        await waitFor(() => {
            fireEvent.change(inputButton, {target: {files: [testFile]}});
        })
        const loadButton = screen.getByTestId('confirm-load-button');
        await waitFor(() => screen.getByTestId('load-validity-message'))
 
        userEvent.click(loadButton);
        expect(props.setTripName).toBeCalledWith('test');
    })


    test('base: closes when button is pressed', async ()=>{
        const closeButton = screen.getByTestId('close-load');
        userEvent.click(closeButton);
        expect(props.toggleLoadFile).toBeCalled;
    })
});