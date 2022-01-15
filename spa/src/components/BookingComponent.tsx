import React, {Component} from 'react';
import DateComponent from "./DateComponent";
import RoomCategoryComponent from "./RoomCategoryComponent";
import ServiceComponent from "./ServiceComponent";
import GuestComponent from "./GuestComponent";
import {BookingSummaryComponent} from "./BookingSummaryComponent";
import {BookingSuccessComponent} from "./BookingSuccessComponent";

type BookingState = {
    step?: number,
    checkInDate?: Date,
    checkOutDate?: Date,
    roomCategoryIds?: string[],
    roomCategoryNames?: string[],
    roomCategoryAmounts?: Number[],
    serviceIds?: string[],
    serviceNames?: string[],
    servicePrices?: Number[],
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
    country?: string
};

class BookingComponent extends Component<{}, BookingState> {
    state = {
        step: 1,
        checkInDate: new Date(),
        checkOutDate: new Date(),
        roomCategoryIds: [],
        roomCategoryNames: [],
        roomCategoryAmounts: [],
        serviceIds: [],
        serviceNames: [],
        servicePrices: [],
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
        country: ''
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

    // TODO: Specify appropriate data type
    handleChange = (input: any) => (value: any) => {
        this.setState({
            [input]: value
        });
    }

    render() {
        const step = this.state.step;
        const {
            checkInDate, checkOutDate, roomCategoryIds, roomCategoryNames, roomCategoryAmounts, serviceIds,
            serviceNames, servicePrices, firstName, lastName, gender, eMail, phoneNumber, birthDate, streetName,
            streetNumber, zipCode, city, country
        } = this.state;
        const values = {
            checkInDate, checkOutDate, roomCategoryIds, roomCategoryNames, roomCategoryAmounts, serviceIds,
            serviceNames, servicePrices, firstName, lastName, gender, eMail, phoneNumber, birthDate, streetName,
            streetNumber, zipCode, city, country
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
                    <ServiceComponent />
                )
            case 4:
                return (
                    <GuestComponent />
                )
            case 5:
                return (
                    <BookingSummaryComponent />
                )
            case 6:
                return (
                    <BookingSuccessComponent />
                )
            default:
                // do nothing
        }
    }
}

export default BookingComponent;