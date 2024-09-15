import React from 'react';
import { Container, Button } from 'reactstrap';
import { CLIENT_TEAM_NAME } from '@utils/constants';
import Menu from './Menu';
import { useToggle } from '@hooks/useToggle';
import AddPlace from './AddPlace';
import LoadFile from './LoadFile'
import Settings from './Settings';
import { IoMdClose } from 'react-icons/io';

export default function Header(props) {
	const [showAddPlace, toggleAddPlace] = useToggle(false);
	const [showSettings, toggleSettings] = useToggle(false);
	const [showLoadFile, toggleLoadFile] = useToggle(false);

	const toggles = {
		toggleAddPlace, toggleSettings, toggleLoadFile, toggleAbout: props.toggleAbout,
	}

	const shows = {
		showAddPlace, showSettings, showLoadFile, showAbout: props.showAbout
	}
	
	return (
		<React.Fragment>
			<HeaderContents
				{...toggles}
				{...props}
			/>
			<AppModals
				{...shows}
				{...toggles}
				{...props}
			/>
		</React.Fragment>
	);
}

function HeaderContents(props) {
	return (
		<div className='full-width header vertical-center'>
			<Container>
				<div className='header-container'>
					<h1
						className='tco-text-upper header-title'
						data-testid='header-title'
					>
						{CLIENT_TEAM_NAME}
					</h1>
					<HeaderButton {...props} />
				</div>
			</Container>
		</div>
	);
}

function HeaderButton(props) {
	return props.showAbout ? (
		<Button
			data-testid='close-about-button'
			color='primary'
			onClick={() => props.toggleAbout()}
		>
			<IoMdClose size={32} />
		</Button>
	) : (
		<Menu {...props}/>
	);
}

function AppModals(props) {
	return (
		<>
			<AddPlace
				{...props}
			/>
			<Settings
				{...props}
			/>
			<LoadFile
				{...props}
			/>
		</>
	);
}
