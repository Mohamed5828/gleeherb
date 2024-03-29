import React from "react";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import L from "leaflet"; // Import Leaflet library
import "leaflet/dist/leaflet.css"; // Import Leaflet CSS

// Import marker icon image
import markerIcon from "leaflet/dist/images/marker-icon.png";
import markerIconRetina from "leaflet/dist/images/marker-icon-2x.png";
import markerShadow from "leaflet/dist/images/marker-shadow.png";

// Create custom marker icon
const defaultIcon = L.icon({
  iconUrl: markerIcon,
  iconRetinaUrl: markerIconRetina,
  shadowUrl: markerShadow,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
});

const MapComponent = ({ latitude, longitude }) => {
  // Log latitude and longitude for debugging
  console.log("Latitude:", latitude);
  console.log("Longitude:", longitude);

  return (
    <MapContainer
      center={[latitude, longitude]}
      zoom={13}
      style={{ height: "400px", width: "100%" }}
    >
      {/* Make sure the TileLayer URL is correct */}
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
      />
      {/* Ensure Marker position is correct and use custom icon */}
      <Marker position={[latitude, longitude]} icon={defaultIcon}>
        <Popup>A marker on Leaflet map!</Popup>
      </Marker>
    </MapContainer>
  );
};

export default MapComponent;
