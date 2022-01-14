import React, {Component} from 'react';
import {
    Link
} from "react-router-dom";

type DateState = {
    checkInDate: string,
    checkoutDate: string
}

class BookingDateComponent extends Component<{}, DateState> {
    constructor(props: {}) {
        super(props);

        this.state = {
            checkInDate: new Date().toDateString(),
            checkoutDate: ''
        };
    }

    setCheckInDate(checkInValue: string) {
        let checkInDateInput: string = checkInValue;

    }

    render() {
        return (
            <div>
                <input type={"date"} onChange={(e) => {this.setCheckInDate(e.target.value)}}/>

                <input type={"date"}/>

                <Link to={'/'}>
                    <button className="btn btn-primary">Back</button>
                </Link>
            </div>
        );
    }
}

export default BookingDateComponent;