import React, {useState} from 'react';
import {Link, useNavigate} from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const DateComponent = () => {
    const[checkInDate, setCheckInDate] = useState<Date | null>(new Date());
    const[checkOutDate, setCheckOutDate] = useState<Date | null>(new Date());
    const[checkInDateError, setCheckInDateError] = useState<string>("");
    const[checkOutDateError, setCheckOutDateError] = useState<string>("");
    const navigate = useNavigate();

    const handleSubmit = (): void => {
        let isValid: boolean = true;
        let checkInDateErrorMsg: string = '';
        let checkOutDateErrorMsg: string = '';

        let now: Date = new Date();
        now.setDate(now.getDate() - 1);

        if(checkInDate === null) {
            isValid = false;
            checkInDateErrorMsg = 'You have to enter a check in date!';
        } else if(checkInDate < now) {
            isValid = false;
            checkInDateErrorMsg = 'Check in date has to be in the future!';
        }

        if(checkOutDate === null) {
            isValid = false;
            checkOutDateErrorMsg = 'You have to enter a check out date!';
        } else if((checkInDate != null) && (checkOutDate <= checkInDate)) {
            isValid = false;
            checkOutDateErrorMsg = 'Check out date has to be after check in date!';
        }

        if(!isValid) {
            setCheckInDateError(checkInDateErrorMsg);
            setCheckOutDateError(checkOutDateErrorMsg);
        } else {
            navigate("/chooseroomcategories");
        }
    }

    const cardStyle = {
        minHeight: "50vh",
        margin: "auto"
    };

    const progressBarStyle = {
        width: "20%"
    };

    const formStyle = {
        width: "40%",
        margin: "auto"
    };

    return (
        <div className="card" style={cardStyle}>
            <div className="card-header">
                <div className="d-flex justify-content-between align-items-center">
                    <div>
                        <span className="h4 align-middle">Create booking - dates</span>
                    </div>
                </div>
                <br />
                <div className="progress">
                    <div className="progress-bar" role="progressbar" style={progressBarStyle}>1/5</div>
                </div>
            </div>
            <div className="card-body">
                <div style={formStyle}>
                    <div className="mb-3">
                        <div className="input-group">
                            <span className="input-group-text col">Check-in date</span>
                            <DatePicker
                                className="form-control"
                                dateFormat="dd / MM / yyyy"
                                selected={checkInDate}
                                onChange={(date) => setCheckInDate(date)}
                                minDate={new Date()}
                                maxDate={checkOutDate}
                            />
                        </div>
                        <span className="text-danger">{checkInDateError}</span>
                    </div>

                    <div>
                        <div className="input-group">
                            <span className="input-group-text col">Check-out date</span>
                            <DatePicker
                                className="form-control"
                                dateFormat="dd / MM / yyyy"
                                selected={checkOutDate}
                                onChange={(date) => setCheckOutDate(date)}
                                minDate={checkInDate}
                            />
                        </div>
                        <span className="text-danger">{checkOutDateError}</span>
                    </div>
                </div>
            </div>
            <div className="card-footer">
                <Link to={'/'}>
                    <button className="btn btn-primary" type="submit">Back</button>
                </Link>

                <button className="btn btn-primary float-end" onClick={() => handleSubmit()}>Next</button>
            </div>
        </div>
    );
}

export default DateComponent;