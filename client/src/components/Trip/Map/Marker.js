import React from 'react';
import { Marker as LeafletMarker, Popup } from 'react-leaflet';
import { latLngToText, placeToLatLng } from '@utils/transformers';
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';

const MARKER_ICON = L.icon({ iconUrl: icon, shadowUrl: iconShadow, iconAnchor: [12, 40] });

export default function Marker(props) {
    function showMarkerPopup(ref) {
        if (ref) {
            ref.leafletElement.openPopup();
        }
    }

    if (!props.place) {
        return null;
    }

    return (
        <LeafletMarker ref={(ref) => showMarkerPopup(ref)} position={placeToLatLng(props.place)} icon={MARKER_ICON}>
            <Popup offset={[0, -18]} className="font-weight-bold">
                {props.place.formatPlace()}
                <br/>
                <small className="text-muted">
                    {latLngToText(placeToLatLng(props.place), 6)}
                </small>
            </Popup>
        </LeafletMarker>
    );
}

