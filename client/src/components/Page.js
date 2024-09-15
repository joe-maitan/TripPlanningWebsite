import React, { useEffect, useState } from 'react';
import { Collapse } from 'reactstrap';
import Header from './Header/Header';
import About from './About/About';
import Planner from './Trip/Planner';
import { useToggle } from '../hooks/useToggle';
import { usePlaces } from '../hooks/usePlaces';
import { useServerSettings } from '../hooks/useServerSettings'

export default function Page(props) {
	const [showAbout, toggleAbout] = useToggle(false);
	const [serverSettings, processServerConfigSuccess] = useServerSettings(props.showMessage);
	const { places, selectedIndex, placeActions } = usePlaces();
	const [tripName, setTripName] = useState('My Trip');

	const context = {
		showAbout, toggleAbout,
		serverSettings, processServerConfigSuccess,
		places, placeActions,
		tripName, setTripName,
		disableRemoveAll : !places?.length,
		selectedIndex
	}

	return (
		<>
			<Header 
				{...context}
				showMessage={props.showMessage}
			/>
			<MainContentArea 
				{...context}
			/>
		</>
	);
}

function MainContentArea(props) {
	return (
		<div className='body'>
			<Collapse isOpen={props.showAbout}>
				<About closePage={props.toggleAbout}/>
			</Collapse>
			<Collapse isOpen={!props.showAbout} data-testid='planner-collapse'>
				<Planner {...props}/>
			</Collapse>
		</div>
	);
}
