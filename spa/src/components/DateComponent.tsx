import React, {useState} from 'react';
import Popup from "./Popup";
import {useNavigate} from "react-router-dom";

interface Props {
    nextStep: () => void;
    handleChange: (input: string, value: any) => void;
    values: any
}

const DateComponent = ({ nextStep, handleChange, values }: Props) => {
    const[checkInDateError, setCheckInDateError] = useState<string>("");
    const[checkOutDateError, setCheckOutDateError] = useState<string>("");
    const[isPopupOpen, setIsPopupOpen] = useState<boolean>(false);
    const navigate = useNavigate();

    const handleSubmit = (): void => {
        let isValid: boolean = true;
        let checkInDateErrorMsg: string = '';
        let checkOutDateErrorMsg: string = '';
        let checkInDate: Date = new Date(values.checkInDate);
        let checkOutDate: Date = new Date(values.checkOutDate);
        let now: Date = new Date();
        // Subtract one day so today can also be used as check in date
        now.setDate(now.getDate() - 1);

        if(values.checkInDate === '') {
            isValid = false;
            checkInDateErrorMsg = "You have to enter a check in date!";
        }

        if(values.checkOutDate === '') {
            isValid = false;
            checkOutDateErrorMsg = "You have to enter a check out date!";
        }

        if(checkInDate < now) {
            isValid = false;
            checkInDateErrorMsg = "Check in date can not be in the past!";
        }

        if(checkInDate >= checkOutDate) {
            isValid = false;
            checkOutDateErrorMsg = "Check out date has to be after check in date";
        }

        if(!isValid) {
            setCheckInDateError(checkInDateErrorMsg);
            setCheckOutDateError(checkOutDateErrorMsg);
        } else {
            nextStep();
        }
    }

    const progressBarStyle = {
        width: "20%"
    };

    return (
        <div className="container h-100 p-5 ">
            <div className="card w-75 h-50 m-auto">
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
                <div className="card-body overflow-auto">
                    <div className="p-2 w-50 m-auto">
                        <div className="p-3">
                            <div className="input-group">
                                <span className="input-group-text col">Check-in date</span>
                                <input className="form-control"
                                       type="date"
                                       placeholder=" "
                                       value={values.checkInDate}
                                       onChange={(e) => handleChange('checkInDate', e.target.value)}
                                />
                            </div>
                            <span className="text-danger">{checkInDateError}</span>
                        </div>
                        <div className="p-3">
                            <div className="input-group">
                                <span className="input-group-text col">Check-out date</span>
                                <input className="form-control"
                                       type="date"
                                       placeholder=" "
                                       value={values.checkOutDate}
                                       onChange={(e) => handleChange('checkOutDate', e.target.value)}
                                />
                            </div>
                            <span className="text-danger">{checkOutDateError}</span>
                        </div>
                    </div>
                </div>
                <div className="card-footer">
                    <button className="btn btn-primary" onClick={() => setIsPopupOpen(true)}>Back home</button>

                    <button className="btn btn-primary float-end" onClick={() => handleSubmit()}>Next</button>
                </div>
            </div>

             <Popup
                 content={"Do you want to go back to the home screen? All data will be lost."}
                 handleClose={() => setIsPopupOpen(false)}
                 handleAccept={() => navigate("/")}
                 show={isPopupOpen}
             />

        </div>
    );
}

export default DateComponent;