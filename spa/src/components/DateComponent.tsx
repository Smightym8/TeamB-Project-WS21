import React, {useState} from 'react';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {
    Link
} from "react-router-dom";

interface Props {
    nextStep: () => void;
    handleChange: (input: any) => (e: any) => void;
    values: any
}

const DateComponent = ({ nextStep, handleChange, values }: Props) => {
    const[checkInDateError, setCheckInDateError] = useState<string>("");
    const[checkOutDateError, setCheckOutDateError] = useState<string>("");

    const handleSubmit = (): void => {
        let isValid: boolean = true;
        let checkInDateErrorMsg: string = '';
        let checkOutDateErrorMsg: string = '';

        let now: Date = new Date();
        now.setDate(now.getDate() - 1);

        if(values.checkInDate === null) {
            isValid = false;
            checkInDateErrorMsg = 'You have to enter a check in date!';
        } else if(values.checkInDate < now) {
            isValid = false;
            checkInDateErrorMsg = 'Check in date has to be in the future!';
        }

        if(values.checkOutDate === null) {
            isValid = false;
            checkOutDateErrorMsg = 'You have to enter a check out date!';
        } else if((values.checkInDate != null) && (values.checkOutDate <= values.checkInDate)) {
            isValid = false;
            checkOutDateErrorMsg = 'Check out date has to be after check in date!';
        }

        if(!isValid) {
            setCheckInDateError(checkInDateErrorMsg);
            setCheckOutDateError(checkOutDateErrorMsg);
        } else {
            nextStep();
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
                                selected={values.checkInDate}
                                onChange={handleChange('checkInDate')}
                                minDate={new Date()}
                                maxDate={values.checkOutDate}
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
                                selected={values.checkOutDate}
                                onChange={handleChange('checkOutDate')}
                                minDate={values.checkInDate}
                            />
                        </div>
                        <span className="text-danger">{checkOutDateError}</span>
                    </div>
                </div>
            </div>
            <div className="card-footer">
                <Link to={'/'}>
                    <button className="btn btn-primary" type="submit">Back to Home</button>
                </Link>

                <button className="btn btn-primary float-end" onClick={() => handleSubmit()}>Next</button>
            </div>
        </div>
    );
}

export default DateComponent;