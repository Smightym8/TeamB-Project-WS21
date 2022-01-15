import React, {useState} from 'react';

interface Props {
    prevStep: () => void;
    nextStep: () => void;
    handleChange: (input: string, value: any) => void;
    values: any
}

const GuestComponent = ({ prevStep, nextStep, handleChange, values }: Props) => {
    const[firstNameError, setFirstNameError] = useState<string>("");
    const[lastNameError, setLastNameError] = useState<string>("");
    const[genderError, setGenderError] = useState<string>("");
    const[eMailError, setEMailError] = useState<string>("");
    const[phoneNumberError, setPhoneNumberError] = useState<string>("");
    const[birthDateError, setBirthDateError] = useState<string>("");
    const[streetNameError, setStreetNameError] = useState<string>("");
    const[streetNumberError, setStreetNumberError] = useState<string>("");
    const[zipCodeError, setZipCodeError] = useState<string>("");
    const[cityError, setCityError] = useState<string>("");
    const[countryError, setCountryError] = useState<string>("");


    const progressBarStyle = {
        width: "80%"
    };

    const handleSubmit = (): void => {
        let isValid: boolean = true;
        let firstNameErrorMsg: string = '';
        let genderErrorMsg: string = '';
        let lastNameErrorMsg: string = '';
        let eMailErrorMsg: string = '';
        let phoneNumberErrorMsg: string = '';
        let birthDateErrorMsg: string = '';
        let streetNameErrorMsg: string = '';
        let streetNumberErrorMsg: string = '';
        let zipCodeErrorMsg: string = '';
        let cityErrorMsg: string = '';
        let countryErrorMsg: string = '';

        let now: Date = new Date();
        now.setDate(now.getFullYear() - 18);

        if(values.firstName === '') {
            isValid = false;
            firstNameErrorMsg = 'You have to provide your first name!';
        }

        if(values.lastName === '') {
            isValid = false;
            lastNameErrorMsg = 'You have to provide your last name!';
        }

        if(values.gender === 'Default') {
            isValid = false;
            genderErrorMsg = 'You have to select your gender!';
        }

        if(values.eMail === '') {
            isValid = false;
            eMailErrorMsg = 'You have to provide your e-mail!';
        }

        if(values.phoneNumber === '') {
            isValid = false;
            phoneNumberErrorMsg = 'You have to provide your phone number!';
        }

        if(values.birthDate < now) {
            isValid = false;
            birthDateErrorMsg = 'You have to be at least 18 years old!';
        }

        if(values.birthDate === '') {
            isValid = false;
            birthDateErrorMsg = 'You have to provide your birth date!';
        }

        if(values.streetName === '') {
            isValid = false;
            streetNameErrorMsg = 'You have to provide your street name!';
        }

        if(values.streetNumber === '') {
            isValid = false;
            streetNumberErrorMsg = 'You have to provide your street number!';
        }

        if(values.zipCode === '') {
            isValid = false;
            zipCodeErrorMsg = 'You have to provide your zip code!';
        }

        if(values.city === '') {
            isValid = false;
            cityErrorMsg = 'You have to provide your city!';
        }

        if(values.country === '') {
            isValid = false;
            countryErrorMsg = 'You have to provide your country!';
        }

        if(!isValid) {
            setFirstNameError(firstNameErrorMsg);
            setLastNameError(lastNameErrorMsg);
            setGenderError(genderErrorMsg);
            setEMailError(eMailErrorMsg);
            setPhoneNumberError(phoneNumberErrorMsg);
            setBirthDateError(birthDateErrorMsg);
            setStreetNameError(streetNameErrorMsg);
            setStreetNumberError(streetNumberErrorMsg);
            setZipCodeError(zipCodeErrorMsg);
            setCityError(cityErrorMsg);
            setCountryError(countryErrorMsg);
        } else {
            nextStep();
        }
    }

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
                        <select value={values.gender}
                                onChange={(e) => handleChange('gender', e.target.value)}
                                className="form-select">
                            <option value='Default'>Select a gender</option>
                            <option value='Male'>Male</option>
                            <option value='Female'>Female</option>
                            <option value='Divers'>Divers</option>
                        </select>
                        <label htmlFor="gender">Gender<span>*</span></label>
                        <span className="text-danger">{genderError}</span>
                    </div>
                </div>

                <br />

                <div className="d-flex pb-1">
                    <div className="form-floating me-1">
                        <input className="form-control"
                               type="text"
                               placeholder=" "
                               value={values.firstName}
                               onChange={(e) => handleChange('firstName', e.target.value)}
                        />

                        <label htmlFor="firstname">Firstname<span>*</span></label>
                        <span className="text-danger">{firstNameError}</span>
                    </div>

                    <div className="form-floating ms-1">
                        <input className="form-control"
                               type="text"
                               placeholder=" "
                               value={values.lastName}
                               onChange={(e) => handleChange('lastName', e.target.value)}
                        />
                        <label htmlFor="lastname">Last Name<span>*</span></label>
                        <span className="text-danger">{lastNameError}</span>
                    </div>
                </div>

                <div className="d-flex py-1">
                    <div className="form-floating me-1">
                        <input className="form-control"
                               type="text"
                               placeholder=" "
                               value={values.streetName}
                               onChange={(e) => handleChange('streetName', e.target.value)}
                        />
                        <label htmlFor="streetname">Street name<span>*</span></label>
                        <span className="text-danger">{streetNameError}</span>
                    </div>

                    <div className="form-floating ms-1">
                        <input className="form-control"
                               type="text"
                               placeholder=" "
                               value={values.streetNumber}
                               onChange={(e) => handleChange('streetNumber', e.target.value)}
                        />
                        <label htmlFor="streetnumber">Street number<span>*</span></label>
                        <span className="text-danger">{streetNumberError}</span>
                    </div>
                </div>

                <div className="d-flex py-1">
                    <div className="form-floating me-1">
                        <input className="form-control"
                               type="text"
                               placeholder=" "
                               value={values.zipCode}
                               onChange={(e) => handleChange('zipCode', e.target.value)}
                        />
                        <label htmlFor="zipcode">Zip code<span>*</span></label>
                        <span className="text-danger">{zipCodeError}</span>
                    </div>
                    <div className="form-floating ms-1">
                        <input className="form-control"
                               type="text"
                               placeholder=" "
                               value={values.city}
                               onChange={(e) => handleChange('city', e.target.value)}
                        />
                        <label htmlFor="city">City<span>*</span></label>
                        <span className="text-danger">{cityError}</span>
                    </div>
                </div>

                <div className="d-flex py-1">
                    <div className="form-floating pe-1">
                        <input className="form-control"
                               type="text"
                               placeholder=" "
                               value={values.country}
                               onChange={(e) => handleChange('country', e.target.value)}
                        />
                        <label htmlFor="country">Country<span>*</span></label>
                        <span className="text-danger">{countryError}</span>
                    </div>
                </div>

                <div className="d-flex py-1">
                    <div className="form-floating pe-1">
                        <input className="form-control"
                               type="date"
                               placeholder=" "
                               value={values.birthDate}
                               onChange={(e) => handleChange('birthDate', e.target.value)}
                        />
                        <label htmlFor="birthdate">Date of birth<span>*</span></label>
                        <span className="text-danger">{birthDateError}</span>
                    </div>
                </div>

                <div className="d-flex py-1">
                    <div className="form-floating me-1">
                        <input className="form-control"
                               type="email"
                               placeholder=" "
                               value={values.eMail}
                               onChange={(e) => handleChange('eMail', e.target.value)}
                        />
                        <label htmlFor="email">Email</label>
                        <span className="text-danger">{eMailError}</span>
                    </div>
                    <div className="form-floating ms-1" >
                        <input className="form-control"
                               type="tel"
                               placeholder=" "
                               value={values.phoneNumber}
                               onChange={(e) => handleChange('phoneNumber', e.target.value)}
                        />
                        <label htmlFor="phone">Phone number</label>
                        <span className="text-danger">{phoneNumberError}</span>
                    </div>
                </div>

            </div>
            <div className="card-footer">
                <button className="btn btn-primary" type="submit" onClick={() => prevStep()}>Back</button>
                <button className="btn btn-primary float-end" onClick={() => handleSubmit()}>Next</button>
            </div>
        </div>
    );
}

export default GuestComponent;