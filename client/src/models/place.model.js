/*
The Place constructor is a simple form of ETL, used to harmonize different data sources (reverse Geocode API and Database).
https://en.wikipedia.org/wiki/Extract,_transform,_load
*/
export class Place {
	constructor(placeObj) {
		this.name = placeObj.name ?? placeObj.leisure ?? placeObj.building ?? placeObj.amenity ?? placeObj.man_made;
        this.streetAddress = this.buildStreetAddress(placeObj);
		this.latitude = placeObj.latitude ?? placeObj.lat;
		this.longitude = placeObj.longitude ?? placeObj.lng;
		this.municipality = placeObj.municipality ?? placeObj.city ?? placeObj.village ?? placeObj.state_district ?? placeObj.town ?? placeObj.county;
		this.region = placeObj.region ?? placeObj.state;
		this.country = placeObj.country;
        this.postcode = placeObj.postcode;
        this.defaultDisplayName = this.buildDefaultDisplayName();
	}

    formatPlace(){
        return this.hasName() + this.hasStreetAddress() + this.hasMunicipality() + this.hasRegion() + this.hasCountry() + this.hasPostcode();
    }

    buildStreetAddress(placeObj){
        return this.hasHouseNumber(placeObj) + this.hasRoad(placeObj) + this.hasSuburb(placeObj);
    }

    buildDefaultDisplayName(){
        return this.formatPlace().split(',')[0];
    }

    hasName(){
        return this.placeHasProps(this.name, []);
    }

    hasStreetAddress(){
        return this.placeHasProps(this.streetAddress, [this.hasName()]);
    }

    hasMunicipality(){
        return this.placeHasProps(this.municipality, [this.hasName(), this.hasStreetAddress()]);
    }

    hasRegion(){
        return this.placeHasProps(this.region, [this.hasName(), this.hasStreetAddress(), this.hasMunicipality()]);
    }

    hasCountry(){
        return this.placeHasProps(this.country, [this.hasName(), this.hasStreetAddress(), this.hasMunicipality(), this.hasRegion()]);
    }

    hasPostcode(){
        return this.placeHasProps(this.postcode, [this.hasName(), this.hasStreetAddress(), this.hasMunicipality(), this.hasRegion(), this.hasCountry()]);
    }

    hasHouseNumber(placeObj){
        if(placeObj.house_number){
            return placeObj.house_number;
        }
        return '';
    }

    hasRoad(placeObj){
        if(placeObj.road){
            if(this.hasHouseNumber(placeObj)){
                return ' ' + placeObj.road;
            }
            return placeObj.road;
        }
        return '';
    }

    hasSuburb(placeObj){
        return this.placeHasProps(placeObj.suburb, [this.hasHouseNumber(placeObj), this.hasRoad(placeObj)]);
    }

    placeHasProps(placeProp, precedingProps){
        if(placeProp){
            return this.evaluatePlaceProps(placeProp, precedingProps);
        }
        return '';
    }

    evaluatePlaceProps(placeProp, precedingProps){
        for(let i = 0; i < precedingProps.length; i++){
            if(precedingProps[i]){
                return `, ${placeProp}`;
            }
        }
        return placeProp;
    }
}
