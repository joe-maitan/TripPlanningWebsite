import React from 'react';
import {
	DropdownItem,
	Dropdown,
	DropdownMenu,
	DropdownToggle,
} from 'reactstrap';
import { GiHamburgerMenu } from 'react-icons/gi';
import { FaPlus, FaTrashAlt, FaFolderOpen, FaSave } from 'react-icons/fa';
import { BsFillPeopleFill } from 'react-icons/bs';
import { AiFillSetting } from 'react-icons/ai'
import { useToggle } from '@hooks/useToggle';
import { SaveTrip } from '@utils/saveTrip';

export default function Menu(props) {
	const [menuOpen, toggleMenu] = useToggle(false);
	const menuButtons = buildMenuButtons(props);

	return (
		<Dropdown isOpen={menuOpen} toggle={toggleMenu}>
			<DropdownToggle color='primary' data-testid='menu-toggle'>
				<GiHamburgerMenu size={32} />
			</DropdownToggle>
			<DropdownMenu data-testid='menu-button-container' end>
				<MenuItems menuButtons={menuButtons} />
			</DropdownMenu>
		</Dropdown>
	);
}

function MenuItems(props) {
	return (
		<>
			{props.menuButtons.map((menuButtonProps) => (
				<MenuButton key={menuButtonProps.dataTestId} {...menuButtonProps} />
			))}
		</>
	);
}

class MenuButtonProps {
	constructor(
		dataTestId,
		buttonAction,
		buttonIcon,
		buttonText,
		disabled = false
	) {
		this.dataTestId = dataTestId;
		this.buttonAction = buttonAction;
		this.buttonIcon = buttonIcon;
		this.buttonText = buttonText;
		this.disabled = disabled;
	}
}

function buildMenuButtons(props) {
	return [
		new MenuButtonProps('about-button', props.toggleAbout, <BsFillPeopleFill />, 'About'),
		new MenuButtonProps('add-place-button', props.toggleAddPlace, <FaPlus />, 'Add Place'),
		new MenuButtonProps('load-trip-button', props.toggleLoadFile, <FaFolderOpen />, 'Load Trip'),
		new MenuButtonProps('save-trip-button', () => {SaveTrip(props.tripName, JSON.stringify({"places": props.places}))}, <FaSave />, 'Save Trip'),
		new MenuButtonProps('remove-all-button', props.placeActions.removeAll, <FaTrashAlt />, 'Remove All', props.disableRemoveAll),
		new MenuButtonProps('settings-button', props.toggleSettings, <AiFillSetting />, 'Settings'),
	];
}

function MenuButton({
	dataTestId,
	buttonAction,
	buttonIcon,
	buttonText,
	disabled,
}) {
	return (
		<DropdownItem
			data-testid={dataTestId}
			disabled={disabled}
			onClick={() => buttonAction()}
		>
			<div className='menu-item'>
				{buttonIcon}
				&nbsp;&nbsp; {buttonText}
			</div>
		</DropdownItem>
	);
}
