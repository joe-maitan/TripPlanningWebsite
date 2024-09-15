import React, { useState } from "react";
import { Button, Modal, ModalHeader, ModalFooter, ModalBody } from "reactstrap";
import { LoadPlaces } from "@utils/loadTrip";
import {usePlaces} from "@hooks/usePlaces"
import { FaCheck, FaTimes } from 'react-icons/fa';

export default function LoadFile(props) {
    const [disallowLoad, setDisallowLoad] = useState(true);
    const [showValidityIcon, setShowValidityIcon] = useState(false);

    function clear(){
        props.toggleLoadFile();
        setShowValidityIcon(false);
        setDisallowLoad(true);
    }
    
    const bodyProps = {
        placeActions : props.placeActions,
        setTripName: props.setTripName,
        disallowLoad, setDisallowLoad,
        showValidityIcon, setShowValidityIcon,
        clear  
    }
    return (
            <Modal isOpen={props.showLoadFile} toggle={clear}>
                <LoadFileHeader 
                    toggleLoadFile={clear}
                />
                <LoadFileBody {...bodyProps}/>
            </Modal>
    );
}

function LoadFileHeader(props) {
    return (
        <ModalHeader className="ml-2" toggle={props.toggleLoadFile}>
            Load a Trip
        </ModalHeader>
    );
}

function LoadFileBody(props) {
    const {places: loadedPlaces, selectedIndex: loadedSelectedIndex, placeActions: loadedPlaceActions} = usePlaces();
    const [fileValidity, setFileValidity] = useState(false);
    const [uploadedFileName, setUploadedFileName] = useState(null);

    const context = {
        fileValidity, 
        setFileValidity,
        uploadedFileName,
        setUploadedFileName,
        loadedPlaces, 
        setLoadedPlace: loadedPlaceActions.setPlaces,
    }
    return(
        <div>
            <ChooseFile 
                {...props} 
                {...context}
            />
            <LoadFileFooter
                {...props}
                {...context}
            />
        </div>
    );
}

function ChooseFile(props) {
    return (
        <ModalBody>
            <input type="file" onChange={(e) => onUpload(e, props)} data-testid="input-file-button"/>
            { props.showValidityIcon ? <ValidityMessage fileValidity={props.fileValidity}/> : null }
        </ModalBody>
    );
}

function ValidityMessage(props){
    return(
        <span align='right' data-testid='load-validity-message'>
            {props.fileValidity ? 
                <span>
                    <FaCheck color="green"/>Confirm Below
                </span> :
                <span>
                    <FaTimes color="red"/>Invalid File
                </span>}
        </span>

    )
}
function onUpload(e, props) {
    let reader = new FileReader();
    props.setUploadedFileName(e.target.files[0].name);

    reader.onload = async function (e) {
        props.setDisallowLoad(true);
        const tripString = e.target.result;
        await LoadPlaces(props, tripString);
    }
    reader.readAsText(e.target.files[0]);
}

function LoadFileFooter(props) {
  return (
    <ModalFooter>
        <ConfirmLoadButton
            {...props}
            setPlaces={props.placeActions.setPlaces}
        />
        <CancelLoadButton
            setLoadedPlace={props.setLoadedPlace}
            clear={props.clear}
        />
    </ModalFooter>
  );
}

function ConfirmLoadButton(props) {
    return (
        <Button color="primary"
            disabled={props.disallowLoad}
            onClick={() => {
                props.setPlaces(props.loadedPlaces);
                props.clear();
                props.setLoadedPlace([]);
                props.setTripName(RemoveFileExtension(props.uploadedFileName));
            }} 
            data-testid='confirm-load-button'
        >
            Load Selected File 
        </Button>
    )
}

function RemoveFileExtension(fileName) {
    if (!fileName.includes('.')) {
        return fileName;
    } else {
        let finalDotIndex = fileName.lastIndexOf('.');
        return fileName.substring(0, finalDotIndex);
    }
}

function CancelLoadButton(props){
    return(
        <Button color="secondary" 
            onClick={() =>{
                props.setLoadedPlace([]);
                props.clear();
                }
            }
            data-testid='close-load'
        >
            Cancel 
        </Button>
    )
}