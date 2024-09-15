import React, { useEffect, useState } from 'react';
import {
	Button,
	Col,
	Modal,
	ModalBody,
	ModalHeader,
	Input,
	InputGroup,
	Collapse,
	ModalFooter,
	Dropdown, DropdownToggle, DropdownMenu, DropdownItem
} from 'reactstrap';
import { FaHome } from 'react-icons/fa';
import Coordinates from 'coordinate-parser';
import { DEFAULT_STARTING_POSITION } from '@utils/constants';
import { reverseGeocode } from '@utils/reverseGeocode';
import { sendAPIRequest } from '@utils/restfulAPI';
import { isFeatureImplemented } from '@utils/restfulAPI';
import { LOG } from '@utils/constants';
import { verifySearch, verifyNear } from './VerifySearch';

export default function AddPlace(props) {
	const [foundPlace, setFoundPlace] = useState();
	const [foundPlaces, setFoundPlaces] = useState([]);
	const [buttonClicked, setButtonClicked] = useState(Array(foundPlaces.length).fill(false));
	const [coordString, setCoordString] = useState('');
	const [selectedType, setSelectedType] = useState('All Types');
	const [selectedCountry, setSelectedCountry] = useState('All Countries');
	const [countries, setCountries] = useState([]);
	const [distances, setDistances] = useState([]);
	const addPlaceProps = {
		buttonClicked, setButtonClicked,
		foundPlace, setFoundPlace, coordString, setCoordString,
		append: props.placeActions.append, serverSettings: props.serverSettings,
		foundPlaces, setFoundPlaces,
		selectedType, setSelectedType,
		selectedCountry, setSelectedCountry,
		countries, setCountries, distances, setDistances
	}
	return (
		<Modal isOpen={props.showAddPlace} toggle={props.toggleAddPlace}>
			<AddPlaceHeader toggleAddPlace={props.toggleAddPlace} />
			<PlaceSearch {...addPlaceProps}/>
			<AddPlaceFooter {...addPlaceProps}/>
			<DisplayMap {...addPlaceProps} />
		</Modal>
	);
}

function AddPlaceHeader(props) {
	return (
		<ModalHeader className='ml-2' toggle={props.toggleAddPlace}>
			Add a Place
		</ModalHeader>
	);
}

function PlaceSearch(props) {
	useEffect(() => {
		verifyCoordinates(props);
	}, [props.coordString, props.selectedType, props.selectedCountry]);
	const findImplemented = isFeatureImplemented(props.serverSettings, "find");
	return (
		<ModalBody>
			<Col>
				<InputGroup>
					<Input
						onChange={(input) => props.setCoordString(input.target.value)}
						placeholder={findImplemented ? 'Coordinates or Search Place': 'Coordinates'}
						data-testid='coord-input'
						value={props.coordString}
					/>
					<Button data-testid='home-button' onClick={() => props.append(DEFAULT_STARTING_POSITION)}>
						<FaHome/>
					</Button>
				</InputGroup>
				<PlaceInfo foundPlace={props.foundPlace} />
			</Col>
		</ModalBody>
	);
}

function PlaceInfo(props) {
	return (
		<Collapse isOpen={!!props.foundPlace}>
			<br />
			<div style={{ fontSize: "14px" }}>
			{props.foundPlace?.formatPlace()}
			</div>
		</Collapse>
	);
}
function AddPlaceFooter(props) {
	return (
		<ModalBody>
			<Button
				color='primary'
				onClick={() => {
					props.append(props.foundPlace);
					props.setCoordString('');
					props.setFoundPlace(undefined);
				}}
				data-testid='add-place-button'
				disabled={!props.foundPlace}
				size='sm'
			>
				Add Place
			</Button>
		</ModalBody>
	);
}

function ButtonFooter(props){
	return(
		<ModalFooter>
			<TypeMenu {...props}/>
			<WhereMenu {...props}/>
		</ModalFooter>
	);
}

async function verifyCoordinates(props) {
    if (!isCoordinateText(props.coordString)) {
        await verifySearch(props);
        props.setButtonClicked(Array(props.foundPlaces.length).fill(false));
		props.setDistances([]);
        return;
    }
	let fullPlace;
    try {
        const latLngPlace = new Coordinates(props.coordString);
		const lat = latLngPlace.getLatitude();
		const lng = latLngPlace.getLongitude();
        if (isLatLngValid(lat, lng)){
			fullPlace = await reverseGeocode({ lat, lng });
			props.setFoundPlace(fullPlace);
			await verifyNear(props, lat, lng);
			props.setButtonClicked(Array(props.foundPlaces.length).fill(false));
		}
    } catch (error) {
        if(!fullPlace) props.setFoundPlace(undefined);
    }
}


function DisplayMap(props){
	return(
		<ModalBody><Col>
			{props.foundPlaces.map((place, index) => (
				<div style={{ fontSize: "14px" }} key={place.name}>
					{place.name}: {place.municipality}, {place.country} {"     "}
					{props.distances.length != 0 ? `${props.distances[index]} miles away` : ""}
					<td><Button color={props.buttonClicked[index] ? "secondary" : "primary"} size="sm"
						onClick={() => {
							props.append(place);
							props.setButtonClicked((prevState) => { const newState = [...prevState]; newState[index] = true; return newState; });
						}}
						data-testid="add-place-button"
						disabled={props.buttonClicked[index]}>
						{props.buttonClicked[index] ? "Added" : "Add place"}
					</Button></td></div>
			))}
			</Col>
			<ButtonFooter {...props}/>
			</ModalBody>
	);
}
export function TypeMenu(props) {
	const [dropdownOpen, setDropdownOpen] = useState(false);
	const toggleDropdown = () => {
		setDropdownOpen(!dropdownOpen);
	}
	const typeExists = isFeatureImplemented(props.serverSettings, "type");
	if(!typeExists){
		return(<></>);
	}
	return(
		<Dropdown isOpen={dropdownOpen} toggle={toggleDropdown} align="left" direction="down"  data-testid='type-dropdown'>
			<DropdownToggle caret color="primary">
			{props.selectedType.charAt(0).toUpperCase() + props.selectedType.slice(1)}
			</DropdownToggle>
			<DropdownMenu>
				<DropdownItem data-testid='all-type-click' onClick={() => {props.setSelectedType('All Types')}}>All Types</DropdownItem>
				<DropdownItem data-testid='airport-click' onClick={() => {props.setSelectedType('airport')}}>Airport</DropdownItem>
				<DropdownItem data-testid='heliport-click' onClick={() => {props.setSelectedType('heliport')}}>Heliport</DropdownItem>
				<DropdownItem data-testid='balloon-click' onClick={() => {props.setSelectedType('balloonport')}}>Balloonport</DropdownItem>
				<DropdownItem data-testid='other-click' onClick={() => {props.setSelectedType('other')}}>Other</DropdownItem>
			</DropdownMenu>
		</Dropdown>
		);
}
export function WhereMenu(props){
	const [dropdownOpen, setDropdownOpen] = useState(false);
	const toggleDropdown = () => {
		setDropdownOpen(!dropdownOpen);
	}
	const whereExists = isFeatureImplemented(props.serverSettings, "where");
	if(!whereExists){
		return(<></>);
	}
	useEffect(() => {getWhereOptions(props)}, [props.serverSettings]);
	return(
		<Dropdown isOpen={dropdownOpen} toggle={toggleDropdown} align="left" direction="down"  data-testid='where-dropdown'>
			<DropdownToggle caret color="primary">{props.selectedCountry}</DropdownToggle>
			<DropdownMenu>
			<DropdownItem onClick={() => props.setSelectedCountry("All Countries")}>
				{"All Countries"}
			</DropdownItem>
			{Object.entries(props.countries).map(([index]) => (
				<DropdownItem key={index} onClick={() => props.setSelectedCountry(props.countries[index])}>
				{props.countries[index]}
            </DropdownItem>
			))}
			</DropdownMenu>
		</Dropdown>
	);
}

export async function getWhereOptions(props){
		const request = {
			"requestType": "config"
		}
		const response = await sendAPIRequest(request, props.serverSettings.serverUrl);
		if(response){
			props.setCountries(response.where);
		}
		else{
			props.setCountries([]);
		}
}

function isLatLngValid(lat,lng) {
	return (lat !== undefined && lng !== undefined);
}

function isCoordinateText(inputString) {
    return /^-*[0-9]*.[0-9]*,* *-*[0-9]*.[0-9]*$/.test(inputString);
}