import React, {Component} from 'react';
import {
    Link
} from "react-router-dom";

type GuestState = {
    firstName: string,
    lastName: string,
    gender: string,
    eMail: string,
    phoneNumber: string,
    birthDate: string,
    streetName: string,
    streetNumber: string,
    zipCode: string,
    city: string,
    country: string
}

class GuestComponent extends Component<{}, GuestState> {
    constructor(props: {}) {
        super(props);

        this.state = {
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

        this.handleSubmit = this.handleSubmit.bind(this);
    }


    handleSubmit(event:any) {
        event.preventDefault();
    }

    setFirstName(firstName: string){
        this.setState({firstName: firstName});
    }

    setLastName(lastName: string){
        this.setState({lastName: lastName});
    }

    setGender(gender: string){
        this.setState({gender: gender});
    }

    setEmail(eMail: string){
        this.setState({eMail: eMail});
    }

    setPhoneNumber(phoneNumber: string){
        this.setState({phoneNumber: phoneNumber});
    }

    setBirthDate(birthDate: string){
        this.setState({birthDate: birthDate});
    }

    setStreetName(streetName: string){
        this.setState({streetName: streetName});
    }

    setStreetNumber(streetNumber: string){
        this.setState({streetNumber: streetNumber});
    }

    setZipCode(zipCode: string){
        this.setState({zipCode: zipCode});
    }

    setCity(city: string){
        this.setState({city: city});
    }

    setCountry(country: string){
        this.setState({country: country});
    }

    render() {
        return (
            <div>
                <div className="p-3">
                    <div className="d-flex justify-content-between">
                        <div className="form-floating">
                            <select value={this.state.gender} onChange={(e) => {this.setGender(e.target.value)}} className="form-select">
                                <option value="Male">Male</option>
                                <option value="Female">Female</option>
                                <option value="Divers">Divers</option>
                            </select>
                            <label htmlFor="gender">Gender<span>*</span></label>
                        </div>
                    </div>

                    <br/>

                    <div className="d-flex pb-1">
                        <div className="form-floating me-1">
                            <input className="form-control" type="text" placeholder=" " name="firstName" value={this.state.firstName} onChange={(e) => {this.setFirstName(e.target.value)}}/>
                            <label htmlFor="firstname">Firstname<span>*</span></label>
                        </div>

                        <div className="form-floating ms-1">
                            <input className="form-control" type="text" placeholder=" " name="lastName" value={this.state.lastName} onChange={(e) => {this.setLastName(e.target.value)}} />
                            <label htmlFor="lastname">Last Name:<span>*</span></label>
                        </div>
                    </div>

                    <div className="d-flex py-1">
                        <div className="form-floating me-1">
                            <input className="form-control" type="text" placeholder=" " value={this.state.streetName} onChange={(e) => {this.setStreetName(e.target.value)}}/>
                            <label htmlFor="streetname">Street name<span>*</span></label>
                        </div>
                        <div className="form-floating ms-1">
                            <input className="form-control" type="text" placeholder=" " value={this.state.streetNumber} onChange={(e) => {this.setStreetNumber(e.target.value)}}/>
                            <label htmlFor="streetnumber">Street number<span>*</span></label>
                        </div>
                    </div>

                    <div className="d-flex py-1">
                        <div className="form-floating me-1">
                            <input className="form-control" type="text" placeholder=" " value={this.state.zipCode} onChange={(e) => {this.setZipCode(e.target.value)}}/>
                            <label htmlFor="zipcode">Zip code<span>*</span></label>
                        </div>
                        <div className="form-floating ms-1">
                            <input className="form-control" type="text" placeholder=" " value={this.state.city} onChange={(e) => {this.setCity(e.target.value)}}/>
                            <label htmlFor="city">City<span>*</span></label>
                        </div>
                    </div>

                    <div className="d-flex pt-1">
                        <div className="form-floating pe-1">
                            <input className="form-control" type="text" placeholder=" " value={this.state.country} onChange={(e) => {this.setCountry(e.target.value)}}/>
                            <label htmlFor="country">Country<span>*</span></label>
                        </div>
                    </div>

                    <div className="d-flex">
                        <div className="form-floating pe-1">
                            <input className="form-control" type="date" placeholder=" " value={this.state.birthDate} onChange={(e) => {this.setBirthDate(e.target.value)}}/>
                            <label htmlFor="birthdate">Birthdate<span>*</span></label>
                        </div>
                    </div>

                    <div className="d-flex">
                        <div className="form-floating me-1">
                            <input className="form-control" type="email" placeholder=" " value={this.state.eMail} onChange={(e) => {this.setEmail(e.target.value)}}/>
                                <label htmlFor="email">Email</label>
                        </div>
                        <div className="form-floating ms-1" >
                            <input className="form-control" type="tel" placeholder=" " value={this.state.phoneNumber} onChange={(e) => {this.setPhoneNumber(e.target.value)}}/>
                                <label htmlFor="phone">Phone number</label>

                        </div>
                    </div>

                        <br/>

                    <Link to={'/chooseroomcategories'}>
                        <button className="btn btn-primary">Back</button>
                    </Link>
                    <Link to={'/'}>
                        <button className="btn btn-primary" onClick={this.handleSubmit}>Next</button>
                    </Link>
                </div>
            </div>
        );
    }
}

export default GuestComponent;