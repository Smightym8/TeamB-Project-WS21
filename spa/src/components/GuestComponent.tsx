import React, {Component, useState} from 'react';
import {
    Link
} from "react-router-dom";


const GuestComponent = () => {
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");

    const [gender, setGender] = useState<string>("");
    const [eMail, setEmail] = useState<string>("");
    const [phoneNumber, setPhoneNumber] = useState<string>("");
    const [birthDate, setBirthDate] = useState<string>("");
    const [streetName, setStreetName] = useState<string>("");
    const [streetNumber, setStreetNumber] = useState<string>("");
    const [zipCode, setZipCode] = useState<string>("");
    const [city, setCity] = useState<string>("");
    const [country, setCountry] = useState<string>("");

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
                        <select value={gender} onChange={(e) => {setGender(e.target.value)}} className="form-select">
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
                        <input className="form-control" type="text" placeholder=" " name="firstName" value={firstName}
                               onChange={(e) => {setFirstName(e.target.value)}}/>
                        <label htmlFor="firstname">Firstname<span>*</span></label>
                    </div>

                    <div className="form-floating ms-1">
                        <input className="form-control" type="text" placeholder=" " name="lastName" value={lastName}
                               onChange={(e) => {setLastName(e.target.value)}} />
                        <label htmlFor="lastname">Last Name<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex py-1">
                    <div className="form-floating me-1">
                        <input className="form-control" type="text" placeholder=" " value={streetName}
                               onChange={(e) => {setStreetName(e.target.value)}}/>
                        <label htmlFor="streetname">Street name<span>*</span></label>
                    </div>
                    <div className="form-floating ms-1">
                        <input className="form-control" type="text" placeholder=" " value={streetNumber}
                               onChange={(e) => {setStreetNumber(e.target.value)}}/>
                        <label htmlFor="streetnumber">Street number<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex py-1">
                    <div className="form-floating me-1">
                        <input className="form-control" type="text" placeholder=" " value={zipCode}
                               onChange={(e) => {setZipCode(e.target.value)}}/>
                        <label htmlFor="zipcode">Zip code<span>*</span></label>
                    </div>
                    <div className="form-floating ms-1">
                        <input className="form-control" type="text" placeholder=" " value={city}
                               onChange={(e) => {setCity(e.target.value)}}/>
                        <label htmlFor="city">City<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex pt-1">
                    <div className="form-floating pe-1">
                        <input className="form-control" type="text" placeholder=" " value={country}
                               onChange={(e) => {setCountry(e.target.value)}}/>
                        <label htmlFor="country">Country<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex">
                    <div className="form-floating pe-1">
                        <input className="form-control" type="date" placeholder=" " value={birthDate}
                               onChange={(e) => {setBirthDate(e.target.value)}}/>
                        <label htmlFor="birthdate">Birthdate<span>*</span></label>
                    </div>
                </div>

                <div className="d-flex">
                    <div className="form-floating me-1">
                        <input className="form-control" type="email" placeholder=" " value={eMail}
                               onChange={(e) => {setEmail(e.target.value)}}/>
                        <label htmlFor="email">Email</label>
                    </div>
                    <div className="form-floating ms-1" >
                        <input className="form-control" type="tel" placeholder=" " value={phoneNumber}
                               onChange={(e) => {setPhoneNumber(e.target.value)}}/>
                        <label htmlFor="phone">Phone number</label>

                    </div>
                </div>

            </div>
            <div className="card-footer">
                <Link to={'/chooseroomcategories'}>
                    <button className="btn btn-primary">Back</button>
                </Link>
                <Link to={'/'}>
                    <button className="btn btn-primary float-end">Next</button>
                </Link>
            </div>
        </div>
    );
}

export default GuestComponent;