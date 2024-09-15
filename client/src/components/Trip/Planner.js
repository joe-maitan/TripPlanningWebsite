import React from 'react';
import { Col, Container, Row } from 'reactstrap';
import Map from './Map/Map';
import Itinerary from './Itinerary/Itinerary';

export default function Planner(props) {
	return (
		<Container>
			<Section>
				<Map
					{...props}
				/>
			</Section>
			<Section>
				<Itinerary
					{...props}
				/>
			</Section>
		</Container>
	);
}

function Section(props) {
	return (
		<Row>
			<Col sm={12}>{props.children}</Col>
		</Row>
	);
}
