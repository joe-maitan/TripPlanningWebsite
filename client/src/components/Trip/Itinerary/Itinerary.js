import React, { useState } from 'react';
import { useToggle } from '@hooks/useToggle';
import { Table, Collapse, Button } from 'reactstrap';
import { latLngToText, placeToLatLng } from '@utils/transformers';
import { BsChevronDown } from 'react-icons/bs';
import PlaceActions from './PlaceActions';
import { isFeatureImplemented, sendAPIRequest } from '@utils/restfulAPI';
import { Place } from "@models/place.model";
import { useDistances } from '../../../hooks/useDistances';

export default function Itinerary(props) {
	const {distances} = useDistances(props.places, 3959.0, props.serverSettings);
	const placeListProps = {
		places: props.places,
		placeActions: props.placeActions,
		selectedIndex: props.selectedIndex,
		serverSettings: props.serverSettings,
		distances: distances
	}
	const total = distances.total;
	return (
		<Table responsive>
			<TripHeader
				tripName ={props.tripName} {...placeListProps}
			/>
			<DistanceHeader total={total} />
			<PlaceList 
				{...placeListProps}
			/>
		</Table>
	);
}

function TripHeader(props) {
	return (
		<thead>
			<tr>
				<th
					className='trip-header-title'
					data-testid='trip-header-title'
				>
					{props.tripName}
				</th>
				<OptimizeButtons {...props}/>
			</tr>
		</thead>
	);
}

function DistanceHeader(props){
	return(
		<thead>
		<tr>
			<th data-testid='total-distance'>
				{props.total}
				{" miles"}
			</th>
		  <td data-testid='leg-header' className="text-end">{"leg"}</td>
		  <td data-testid='cumulative-header' className="text-end">{"Σ"}</td>
			<td></td>
		</tr>
	  </thead>
	);
}

export function OptimizeButtons(props){
	const optimizeImplemented = isFeatureImplemented(props.serverSettings, "tour");
	if(!optimizeImplemented){
		return(<td colspan={3}></td>);
	}
	return(
			<td colspan={3}>
			<Button
				color='primary'
				data-testid='optimize-button'
				onClick={() => {
					optimizeTrip(props)
				}}
				size='sm'
			>
				Optimize
			</Button>
			</td>
	);
}

export async function optimizeTrip(props){
	const request = {
		"requestType": "tour",
		"earthRadius": 3960,
		"response": 1,
		"places": props.places
	}
	const response = await sendAPIRequest(request, props.serverSettings.serverUrl);
	if(response){
		const convertedPlaces = response.places.map((obj) => convertObjectToPlace(obj));
		props.placeActions.setPlaces(convertedPlaces);
	}
}

export function convertObjectToPlace(obj) {
	const newPlace = new Place({name: obj.name,
		latitude: obj.latitude,
		longitude: obj.longitude,
		municipality: obj.municipality,
		country: obj.country,});
	return newPlace;
  }

function PlaceList(props) {
	return (
		<tbody>
			{props.places.map((place, index) => (
				<PlaceRow
					{...props}
					key={`table-${JSON.stringify(place)}-${index}`}
					place={place}
					index={index}
				/>
			))}
		</tbody>
	);
}

function PlaceRow(props) {
	const [showFullName, toggleShowFullName] = useToggle(false);
	const name = props.place.defaultDisplayName;
	const location = latLngToText(placeToLatLng(props.place));
	return (
		<tr className={props.selectedIndex === props.index ? 'selected-row' : ''}>
			<td
				data-testid={`place-row-${props.index}`}
				onClick={() =>
					placeRowClicked(
						toggleShowFullName,
						props.placeActions.selectIndex,
						props.index
					)
				}
			>
				<strong>{name}</strong>
				<AdditionalPlaceInfo {...props} showFullName={showFullName} location={location}/>
			</td>
			<td className="text-end"> {props.distances.leg[props.index]}</td>
			<td className="text-end"> {props.distances.cumulative[props.index]}</td>
			<RowArrow toggleShowFullName={toggleShowFullName} index={props.index}/>
		</tr>
	);
}

function AdditionalPlaceInfo(props) {
	return (
		<Collapse isOpen={props.showFullName}>
			{props.place.formatPlace().replace(`${props.place.defaultDisplayName}, `, '')}
			<br />
			{props.location}
			<br />
			<PlaceActions placeActions={props.placeActions} index={props.index} />
		</Collapse>
	);
}

function placeRowClicked(toggleShowFullName, selectIndex, placeIndex) {
	toggleShowFullName();
	selectIndex(placeIndex);
}

function RowArrow(props) {
	return (
		<td>
			<BsChevronDown data-testid={`place-row-toggle-${props.index}`} onClick={props.toggleShowFullName}/>
		</td>
	);
}