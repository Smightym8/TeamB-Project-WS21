import * as React from 'react';

interface Props {
    prevStep: () => void;
    nextStep: () => void;
    values: any
}

const BookingSummaryComponent = ({ prevStep, nextStep, values }: Props) => {
    const {
        checkInDate, checkOutDate, roomCategoryNames, roomCategoryAmounts,
        serviceNames, servicePrices, additionalInformation, firstName, lastName,
        streetName, streetNumber, zipCode, city, country
    } = values;

    const guestInformationStyle = {
        width: "25%"
    };

    const tableStyle = {
        border: "hidden"
    };

    const textFieldStyle = {
        minWidth: "100%"
    };

    return (
        <div className="card card-height">
            <div className="card-header">
                <div className="d-flex justify-content-between align-items-center">
                    <div>
                        <span className="h4 align-middle">Create booking - summary</span>
                    </div>
                </div>
            </div>
            <div className="card-body px-5 py-4">
                <div className="d-flex justify-content-between">
                    <div style={guestInformationStyle}>
                        <p className="h5">Booking</p>
                        <table>
                            <tbody>
                            <tr>
                                <td>Check-in:</td>
                                <td>{checkInDate.getDate()} / {checkInDate.getMonth() + 1} / {checkInDate.getFullYear()}</td>
                            </tr>
                            <tr>
                                <td>Check-out:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td>{checkOutDate.getDate()} / {checkOutDate.getMonth() + 1} / {checkOutDate.getFullYear()}</td>
                            </tr>
                            <tr>
                                <td>Adults:</td>
                                <td>0</td>
                            </tr>
                            <tr>
                                <td>Children:</td>
                                <td>0</td>
                            </tr>
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
            </div>
        </div>
    );
}

export default BookingSummaryComponent;