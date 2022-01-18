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
    const[amountOfAdultsError, setAmountOfAdultsError] = useState<string>("");
    const[amountOfChildrenError, setAmountOfChildrenError] = useState<string>("");

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
        let amountOfAdultsErrorMsg: string = '';
        let amountOfChildrenErrorMsg: string= '';

        let birthDate: Date = new Date(values.birthDate);
        let birthDateLimit: Date = new Date();
        birthDateLimit.setFullYear(birthDateLimit.getFullYear() - 18);

        if(values.gender === 'Default' || values.gender === '') {
            isValid = false;
            genderErrorMsg = 'You have to select your gender!';
        }

        if(values.firstName === '') {
            isValid = false;
            firstNameErrorMsg = 'You have to provide your first name!';
        }

        if(values.lastName === '') {
            isValid = false;
            lastNameErrorMsg = 'You have to provide your last name!';
        }

        if(values.eMail === '') {
            isValid = false;
            eMailErrorMsg = 'You have to provide your e-mail!';
        }

        if(values.phoneNumber === '') {
            isValid = false;
            phoneNumberErrorMsg = 'You have to provide your phone number!';
        }

        if(values.birthDate === '') {
            isValid = false;
            birthDateErrorMsg = 'You have to provide your birth date!';
        }

        if(birthDate > birthDateLimit) {
            isValid = false;
            birthDateErrorMsg = 'You have to be at least 18 years old!';
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

        if(values.amountOfAdults == 0) {
            isValid = false;
            amountOfAdultsErrorMsg = 'You have to provide at least 1 number of persons';
        }

        if(values.amountOfAdults < 0) {
            isValid = false;
            amountOfAdultsErrorMsg = 'You can not provide negative number of adults!'
        }

        if(values.amountOfChildren < 0) {
            isValid = false;
            amountOfChildrenErrorMsg = 'You can not provide negative number of children!'
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
            setAmountOfAdultsError(amountOfAdultsErrorMsg);
            setAmountOfChildrenError(amountOfChildrenErrorMsg)
        } else {
            nextStep();
        }
    }

    const progressBarStyle = {
        width: "80%"
    };

    return (
        <div className="container h-100 p-5 ">
            <div className="card w-75 h-100 m-auto">
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
                <div className="card-body overflow-auto">
                    <div className="p-4">
                        <div className="d-flex justify-content-between">
                            <div className="form-floating w-25">
                                <select value={values.gender}
                                        onChange={(e) => handleChange('gender', e.target.value)}
                                        className="form-select">
                                    <option value='Male'>Male</option>
                                    <option value='Female'>Female</option>
                                    <option value='Divers'>Divers</option>
                                </select>
                                <label htmlFor="gender">Gender<span className="text-danger">*</span></label>
                                <span className="text-danger">{genderError}</span>
                            </div>
                        </div>

                        <br />

                        <div className="d-flex pb-2">
                            <div className="form-floating w-50 me-2">
                                <input className="form-control"
                                       type="text"
                                       placeholder=" "
                                       value={values.firstName}
                                       onChange={(e) => handleChange('firstName', e.target.value)}
                                />

                                <label htmlFor="firstname">Firstname<span className="text-danger">*</span></label>
                                <span className="text-danger">{firstNameError}</span>
                            </div>
                            <div className="form-floating w-50 ms-2">
                                <input className="form-control"
                                       type="text"
                                       placeholder=" "
                                       value={values.lastName}
                                       onChange={(e) => handleChange('lastName', e.target.value)}
                                />
                                <label htmlFor="lastname">Last Name<span className="text-danger">*</span></label>
                                <span className="text-danger">{lastNameError}</span>
                            </div>
                        </div>

                        <div className="d-flex py-2">
                            <div className="form-floating w-50 me-2">
                                <input className="form-control"
                                       type="text"
                                       placeholder=" "
                                       value={values.streetName}
                                       onChange={(e) => handleChange('streetName', e.target.value)}
                                />
                                <label htmlFor="streetname">Street name<span className="text-danger">*</span></label>
                                <span className="text-danger">{streetNameError}</span>
                            </div>
                            <div className="form-floating w-50 ms-2">
                                <input className="form-control"
                                       type="text"
                                       placeholder=" "
                                       value={values.streetNumber}
                                       onChange={(e) => handleChange('streetNumber', e.target.value)}
                                />
                                <label htmlFor="streetnumber">Street number<span className="text-danger">*</span></label>
                                <span className="text-danger">{streetNumberError}</span>
                            </div>
                        </div>

                        <div className="d-flex py-2">
                            <div className="form-floating w-50 me-2">
                                <input className="form-control"
                                       type="text"
                                       placeholder=" "
                                       value={values.zipCode}
                                       onChange={(e) => handleChange('zipCode', e.target.value)}
                                />
                                <label htmlFor="zipcode">Zip code<span className="text-danger">*</span></label>
                                <span className="text-danger">{zipCodeError}</span>
                            </div>
                            <div className="form-floating w-50 ms-2">
                                <input className="form-control"
                                       type="text"
                                       placeholder=" "
                                       value={values.city}
                                       onChange={(e) => handleChange('city', e.target.value)}
                                />
                                <label htmlFor="city">City<span className="text-danger">*</span></label>
                                <span className="text-danger">{cityError}</span>
                            </div>
                        </div>

                        <div className="d-flex py-2">
                            <div className="form-floating w-50 pe-2">
                                <input className="form-control"
                                       type="text"
                                       placeholder=" "
                                       value={values.country}
                                       onChange={(e) => handleChange('country', e.target.value)}
                                />
                                <label htmlFor="country">Country<span className="text-danger">*</span></label>
                                <span className="text-danger">{countryError}</span>
                            </div>
                        </div>

                        <br />

                        <div className="d-flex">
                            <div className="form-floating w-50 pe-2">
                                <input className="form-control"
                                       type="date"
                                       placeholder=" "
                                       value={values.birthDate}
                                       onChange={(e) => handleChange('birthDate', e.target.value)}
                                />
                                <label htmlFor="birthdate">Date of birth<span className="text-danger">*</span></label>
                                <span className="text-danger">{birthDateError}</span>
                            </div>
                        </div>

                        <br />

                        <div className="d-flex py-2">
                            <div className="form-floating w-50 me-2">
                                <input className="form-control"
                                       type="email"
                                       placeholder=" "
                                       value={values.eMail}
                                       onChange={(e) => handleChange('eMail', e.target.value)}
                                />
                                <label htmlFor="email">Email<span className="text-danger">*</span></label>
                                <span className="text-danger">{eMailError}</span>
                            </div>
                            <div className="form-floating w-50 ms-2" >
                                <input className="form-control"
                                       type="tel"
                                       placeholder=" "
                                       value={values.phoneNumber}
                                       onChange={(e) => handleChange('phoneNumber', e.target.value)}
                                />
                                <label htmlFor="phone">Phone number<span className="text-danger">*</span></label>
                                <span className="text-danger">{phoneNumberError}</span>
                            </div>
                        </div>

                        <br />
                        <br />
                        <br />

                        <div className="d-flex">
                            <div className="input-group me-2">
                                <span className="input-group-text col">Adults [15-99 yrs.]</span>
                                <input className="form-control"
                                       type="number"
                                       value={values.amountOfAdults}
                                       min={1}
                                       onChange={(e) => handleChange('amountOfAdults', e.target.value)}
                                />
                            </div>
                            <div className="input-group ms-2">
                                <span className="input-group-text col">Children [0-14 yrs.]</span>
                                <input className="form-control"
                                       type="number"
                                       value={values.amountOfChildren}
                                       min={0}
                                       onChange={(e) => handleChange('amountOfChildren', e.target.value)}
                                />
                            </div>
                        </div>
                        <div className="d-flex">
                            <div className="input-group me-1">
                                <span className="text-danger">{amountOfAdultsError}</span>
                            </div>
                            <div className="input-group ms-1">
                                <span className="text-danger">{amountOfChildrenError}</span>
                            </div>
                        </div>
                    </div>




                </div>
                <div className="card-footer">
                    <button className="btn btn-primary" type="submit" onClick={() => prevStep()}>Back</button>
                    <button className="btn btn-primary float-end" onClick={() => handleSubmit()}>Next</button>
                </div>
            </div>
        </div>
    );
}

export default GuestComponent;