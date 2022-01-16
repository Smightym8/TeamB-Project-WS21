import * as React from 'react';
import BookingService from "../services/BookingService";
import {BookingData} from "../openapi/ts_openapi_client/model/BookingData";
import {useState} from "react";
import {Link} from "react-router-dom";

interface Props {
    prevStep: () => void;
    values: any
}

const BookingSummaryComponent = ({ prevStep, values }: Props) => {
    const [isBookingCreated, setIsBookingCreated] = useState<boolean>(false);
    const [bookingId, setBookingId] = useState<string>("");
    const [isError, setIsError] = useState<boolean>(false);

    const {
        checkInDate, checkOutDate, roomCategoryIds, roomCategoryNames, roomCategoryAmounts, serviceIds,
        serviceNames, servicePrices, additionalInformation, firstName, lastName, gender, eMail, phoneNumber,
        birthDate, streetName, streetNumber, zipCode, city, country, amountOfAdults, amountOfChildren
    } = values;

    const book = () => {
        let finalRoomCategoryIds: string[] = [];
        let finalRoomCategoryAmounts: number[] = [];
        let finalServiceIds: string[] = [];

        // Create new arrays because the other arrays must contain null values
        // To match the index of the corresponding html element
        roomCategoryIds.forEach((roomCategoryId: string) => {
            if(roomCategoryId != null) {
                finalRoomCategoryIds.push(roomCategoryId);
            }
        })

        roomCategoryAmounts.forEach((roomCategoryAmount: number) => {
           if(roomCategoryAmount != null) {
               finalRoomCategoryAmounts.push(roomCategoryAmount);
           }
        });

        serviceIds.forEach((serviceId: string) => {
           if(serviceId != null) {
               finalServiceIds.push(serviceId);
           }
        });

        let bookingData: BookingData = {
            firstName,
            lastName,
            gender,
            eMail,
            phoneNumber,
            birthDate,
            streetName,
            streetNumber,
            zipCode,
            city,
            country,
            finalRoomCategoryIds,
            finalRoomCategoryAmounts,
            finalServiceIds,
            checkInDate,
            checkOutDate,
            amountOfAdults,
            amountOfChildren,
            additionalInformation
        };

        BookingService.createBookingRest(bookingData).then(response => {
            setBookingId(response);
            setIsBookingCreated(true);
        }).catch(error => {
                console.log(error);
                setIsError(true);
        });
    }

    const guestInformationStyle = {
        width: "25%"
    };

    const tableStyle = {
        border: "hidden"
    };

    const textFieldStyle = {
        minWidth: "100%"
    };

    const progressBarStyle = {
        width: "100%"
    };

    const showProgressBar = () => {
        if(isBookingCreated) {
            return (
                <div className="progress">
                    <div className="progress-bar bg-success" role="progressbar" style={progressBarStyle}>5/5</div>
                </div>
            );
        } else {
            return (
                <div className="progress">
                    <div className="progress-bar" role="progressbar" style={progressBarStyle}>5/5</div>
                </div>
            )
        }
    }

    const showSuccessOrErrorMessage = () => {
        if(isBookingCreated) {
            return (
                <div className="alert alert-success" role="alert">
                    Booking {bookingId} successfully created!
                </div>
            );
        } else if(isError) {
            return (
                <div className="alert alert-danger" role="alert">
                    An error occured. Please try again later or contact the hotel.
                </div>
            );
        }
    }

    const showChildrenAmount = () => {
        if(amountOfChildren > 0) {
            return (
                <tr>
                    <td>Children:</td>
                    <td>{amountOfChildren}</td>
                </tr>
            );
        }
    }

    const showNextButton = () => {
        if(isBookingCreated || isError) {
            return (
                <Link to={'/'}>
                    <button className="btn btn-primary float-end" type="submit">Back to Home</button>
                </Link>
            );
        } else {
            return (
                <button className="btn btn-primary float-end" type="submit" onClick={() => book()}>Book</button>
            );
        }
    }

    return (
        <div className="card card-height">
            <div className="card-header">
                <div className="d-flex justify-content-between align-items-center">
                    <div>
                        <span className="h4 align-middle">Create booking - summary</span>
                    </div>
                </div>

                <br />
                <div>
                    { showProgressBar() }
                </div>

                {showSuccessOrErrorMessage()}

            </div>
            <div className="card-body px-5 py-4">
                <div className="d-flex justify-content-between">
                    <div style={guestInformationStyle}>
                        <p className="h5">Booking</p>
                        <table>
                            <tbody>
                            <tr>
                                <td>Check-in:</td>
                                <td>{checkInDate}</td>
                            </tr>
                            <tr>
                                <td>Check-out:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td>{checkOutDate}</td>
                            </tr>
                            <tr>
                                <td>Adults:</td>
                                <td>{amountOfAdults}</td>
                            </tr>

                            { showChildrenAmount() }

                            </tbody>
                        </table>
                    </div>
                    <div style={guestInformationStyle}>
                        <p className="h5">Guest</p>
                        <table>
                            <tbody>
                            <tr>
                                <td> {firstName} {lastName} </td>
                            </tr>
                            <tr>
                                <td> {streetName} {streetNumber} </td>
                            </tr>
                            <tr>
                                <td> {zipCode} {city} </td>
                            </tr>
                            <tr>
                                <td> {country} </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>

                <hr className="my-4" />

                <div className="mb-4 mt-2">
                    <p className="h5">Room Categories</p>
                    <table className="table table-sm table-hover">
                        <tbody>
                        {
                            roomCategoryNames.map((roomCategoryName: string, index: number) =>
                                <tr style={tableStyle}>
                                    <td className="align-middle col">{roomCategoryName}</td>
                                    <td className="align-middle col-3">{roomCategoryAmounts[index]}</td>
                                </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>

                <div className="mb-4 mt-2">
                    <p className="h5">Services</p>
                    <table className="table table-sm table-hover">
                        <tbody>
                            {
                                serviceNames.map((serviceName: string, index: number) =>
                                    <tr style={tableStyle}>
                                        <td className="align-middle col">{serviceName}</td>
                                        <td className="align-middle col-3">€ {servicePrices[index]} per day/room</td>
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>

                <div>
                    <p className="h5">Additional information</p>
                    <textarea style={textFieldStyle} disabled>{additionalInformation}</textarea>
                </div>

            </div>
            <div className="card-footer">
                <button className="btn btn-primary" type="submit" onClick={() => prevStep()}>Back</button>
                { showNextButton() }
            </div>
        </div>
    );
}

export default BookingSummaryComponent;