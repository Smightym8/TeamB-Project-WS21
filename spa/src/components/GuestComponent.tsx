import React, {useState} from 'react';

interface Props {
    prevStep: () => void;
    nextStep: () => void;
    handleChange: (input: string, value: any) => void;
    values: any
}

const GuestComponent = ({ prevStep, nextStep, handleChange, values }: Props) => {
    const progressBarStyle = {
        width: "80%"
    };

    return (
        <div className="card card-height">
            <div className="card-header">
                <div className="d-flex justify-content-between align-items-center">
                    <div>
                        <span className="h4 align-middle">Create booking - guest details</span>
                    </div>
                </div>
                <br />
                <div className="progress">
                    <div className="progress-bar" role="progressbar" style={progressBarStyle}>4/5</div>
                </div>
            </div>
            <div className="card-body px-5 py-4">
                <div className="d-flex justify-content-between">
                    <div className="form-floating">
                        <select value={values.gender} className="form-select">
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Divers">Divers</option>
                        </select>
                        <label htmlFor="gender">Gender<span>*</span></label>
                    </div>
                </div>

                <br />

                <div className="d-flex pb-1">
                    <div className="form-floating me-1">
                        <input className="form-control" type="text" placeholder=" " name="firstName" value={values.firstName} />
                        <label htmlFor="firstname">Firstname<span>*</span></label>
                    </div>

                    <div className="form-floating ms-1">
                        <input className="form-control" type="text" placeholder=" " name="lastName" value={values.lastName} />
                        <label htmlFor="lastname">Last Name<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex py-1">
                    <div className="form-floating me-1">
                        <input className="form-control" type="text" placeholder=" " value={values.streetName} />
                        <label htmlFor="streetname">Street name<span>*</span></label>
                    </div>
                    <div className="form-floating ms-1">
                        <input className="form-control" type="text" placeholder=" " value={values.streetNumber} />
                        <label htmlFor="streetnumber">Street number<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex py-1">
                    <div className="form-floating me-1">
                        <input className="form-control" type="text" placeholder=" " value={values.zipCode} />
                        <label htmlFor="zipcode">Zip code<span>*</span></label>
                    </div>
                    <div className="form-floating ms-1">
                        <input className="form-control" type="text" placeholder=" " value={values.city} />
                        <label htmlFor="city">City<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex pt-1">
                    <div className="form-floating pe-1">
                        <input className="form-control" type="text" placeholder=" " value={values.country} />
                        <label htmlFor="country">Country<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex">
                    <div className="form-floating pe-1">
                        <input className="form-control" type="date" placeholder=" " value={values.birthDate} />
                        <label htmlFor="birthdate">Date of birth<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex">
                    <div className="form-floating me-1">
                        <input className="form-control" type="email" placeholder=" " value={values.eMail} />
                        <label htmlFor="email">Email</label>
                    </div>
                    <div className="form-floating ms-1" >
                        <input className="form-control" type="tel" placeholder=" " value={values.phoneNumber} />
                        <label htmlFor="phone">Phone number</label>

                    </div>
                </div>

            </div>
            <div className="card-footer">
                <button className="btn btn-primary" onClick={() => prevStep()}>Back</button>
                <button className="btn btn-primary float-end" onClick={() => nextStep()}>Next</button>
            </div>
        </div>
    );
}

export default GuestComponent;