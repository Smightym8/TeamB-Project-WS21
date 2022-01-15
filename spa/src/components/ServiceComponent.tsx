import React, {useEffect, useState} from 'react';
import {ServiceDTO} from "../openapi/ts_openapi_client";
import BookingService from "../services/BookingService";

interface Props {
    prevStep: () => void;
    nextStep: () => void;
    handleChange: (input: any) => (e: any) => void;
    values: any
}

const ServiceComponent = ({ prevStep, nextStep, handleChange, values }: Props) => {
    const [services, setServices] = useState<ServiceDTO[]>();

    useEffect(() => {
        fetchServices();
    }, []);

    const fetchServices = () => {
        BookingService.fetchAllServicesRest().then(response => {
            setServices(response);
        });
    }

    const progressBarStyle = {
        width: "60%"
    };

    const tableOuterDivStyle = {
        backgroundColor: "#f7f7f7"
    };

    const tableInnerDivStyle = {
        minHeight: "62.5vh"
    };

    const textAreaStyle = {
        minWidth: "100%"
    };

    return (
        <div className="card card-height">
            <div className="card-header">
                <div className="d-flex justify-content-between align-items-center">
                    <div>
                        <span className="h4 align-middle">Create booking - services</span>
                    </div>
                </div>

                <br />

                <div className="progress">
                    <div className="progress-bar" role="progressbar" style={progressBarStyle}>3/5</div>
                </div>
            </div>
            <div className="card-body px-5 py-4">
                <div className="p-3" style={tableOuterDivStyle}>

                    <div style={tableInnerDivStyle}>

                        <table id="t1" className="table table-hover">
                            <thead>
                            <tr>
                                <th className="col-1"/>
                                <th className="col-6">Name</th>
                                <th className="col-4">Price per day</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                services?.map(
                                    service =>
                                        <tr key={service.id}>
                                            <td className="align-middle">
                                                <div className="form-check">
                                                    <input className="form-check-input" type="checkbox" value={service.id} />
                                                </div>
                                            </td>
                                            <td className="align-middle">{service.name}</td>
                                            <td className="align-middle">{service.price} €</td>
                                        </tr>
                                )
                            }
                            </tbody>
                        </table>
                    </div>

                    <span>Additional information:</span>
                    <textarea style={textAreaStyle} rows={3}/>
                </div>
            </div>
            <div className="card-footer">
                <button className="btn btn-primary" onClick={() => prevStep()}>Back</button>
                <button className="btn btn-primary float-end" onClick={() => nextStep()}>Next</button>
            </div>
        </div>
    );
}

export default ServiceComponent;