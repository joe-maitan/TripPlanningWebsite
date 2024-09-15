import React from 'react';
import { Button, ButtonGroup } from 'reactstrap';
import { FaTrash } from 'react-icons/fa';

export default function PlaceActions(props) {
	return (
		<ButtonGroup size='sm'>
			<Button
				color='primary'
				onClick={() => props.placeActions.removeAtIndex(props.index)}
				data-testid='remove-place-button'
			>
				<FaTrash />
			</Button>
		</ButtonGroup>
	);
}
