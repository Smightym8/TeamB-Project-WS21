import React, {Component} from 'react';
import DateComponent from "./DateComponent";
import RoomCategoryComponent from "./RoomCategoryComponent";
import ServiceComponent from "./ServiceComponent";
import GuestComponent from "./GuestComponent";
import BookingSummaryComponent from "./BookingSummaryComponent";

type BookingState = {
    step?: number,
    checkInDate?: string,
    checkOutDate?: string,
    roomCategoryIds?: string[],
    roomCategoryNames?: string[],
    roomCategoryAmounts?: number[],
    serviceIds?: string[],
    serviceNames?: string[],
    servicePrices?: number[],
    additionalInformation?: string,
    firstName?: string,
    lastName?: string,
    gender?: string,
    eMail?: string,
    phoneNumber?: string,
    birthDate?: string,
    streetName?: string,
    streetNumber?: string,
    zipCode?: string,
    city?: string,
    country?: string,
    amountOfAdults?: number,
    amountOfChildren?: number
};

class BookingComponent extends Component<{}, BookingState> {
    state = {
        step: 1,
        checkInDate: '',
        checkOutDate: '',
        roomCategoryIds: [],
        roomCategoryNames: [],
        roomCategoryAmounts: [],
        serviceIds: [],
        serviceNames: [],
        servicePrices: [],
        additionalInformation: '',
        firstName: '',
        lastName: '',
        gender: '',
        eMail: '',
        phoneNumber: '',
        birthDate: '',
        streetName: '',
        streetNumber: '',
        zipCode: '',
        city: '',
        country: '',
        amountOfAdults: 0,
        amountOfChildren: 0
    };

    // go back to previous step
    prevStep = () => {
        const step: number = this.state.step;

        this.setState({
           step: step - 1
        });
    }

    // got to the next step
    nextStep = () => {
        const step: number = this.state.step;

        this.setState({
            step: step + 1
        });
    }

    handleChange = (stateName: string, value: any) => {
        this.setState({
            [stateName]: value
        } as Partial<BookingState> );
    }

    render() {
        const step = this.state.step;
        const {
            checkInDate, checkOutDate, roomCategoryIds, roomCategoryNames, roomCategoryAmounts, serviceIds,
            serviceNames, servicePrices, additionalInformation, firstName, lastName, gender, eMail, phoneNumber,
            birthDate, streetName, streetNumber, zipCode, city, country, amountOfAdults, amountOfChildren
        } = this.state;
        const values = {
            checkInDate, checkOutDate, roomCategoryIds, roomCategoryNames, roomCategoryAmounts, serviceIds,
            serviceNames, servicePrices, additionalInformation, firstName, lastName, gender, eMail, phoneNumber,
            birthDate, streetName, streetNumber, zipCode, city, country, amountOfAdults, amountOfChildren
        };

        switch(step) {
            case 1:
                return (
                    <DateComponent
                        nextStep={ this.nextStep }
                        handleChange={ this.handleChange }
                        values={ values }
                    />
                )
            case 2:
                return (
                    <RoomCategoryComponent
                        prevStep={ this.prevStep }
                        nextStep={ this.nextStep }
                        handleChange={ this.handleChange }
                        values={ values }
                    />
                )
            case 3:
                return (
                    <ServiceComponent
                        prevStep={ this.prevStep }
                        nextStep={ this.nextStep }
                        handleChange={ this.handleChange }
                        values={ values }
                    />
                )
            case 4:
                return (
                    <GuestComponent
                        prevStep={ this.prevStep }
                        nextStep={ this.nextStep }
                        handleChange={ this.handleChange }
                        values={ values }
                    />
                )
            case 5:
                return (
                    <BookingSummaryComponent
                        prevStep={ this.prevStep }
                        values={ values }
                    />
                )
            default:
                // do nothing
        }
    }
}

export default BookingComponent;