import React, {useEffect, useState} from 'react';
import {ServiceDTO} from "../openapi/ts_openapi_client";
import BookingService from "../services/BookingService";

interface Props {
    prevStep: () => void;
    nextStep: () => void;
    handleChange: (input: string, value: any) => void;
    values: any
}

const ServiceComponent = ({ prevStep, nextStep, handleChange, values }: Props) => {
    const [services, setServices] = useState<ServiceDTO[]>();
    const [serviceIds, setServiceIds] = useState<string[]>(values.serviceIds);
    const [serviceNames, setServiceNames] = useState<string[]>(values.serviceNames);
    const [servicePrices, setServicePrices] = useState<number[]>(values.servicePrices);

    useEffect(() => {
        fetchServices();
    }, []);

    const fetchServices = () => {
        BookingService.fetchAllServicesRest().then(response => {
            setServices(response);
        });
    }

    const handleService = (id: string,
                           name: string | undefined,
                           price: number | undefined,
                           index: number) => {

        let tmpIds = serviceIds;
        let tmpNames = serviceNames;
        let tmpPrices = servicePrices;

        if (name != null && price != null) {
            // If checkbox is checked, id is not in array, so we add it.
            // If checkbox is unchecked, id is already in array, so we remove it.
            if (tmpIds[index] === id) {
                tmpIds.splice(index, 1);
                tmpNames.splice(index, 1);
                tmpPrices.splice(index, 1);
            } else {
                tmpIds[index] = id;
                tmpNames[index] = name;
                tmpPrices[index] = price;
            }
        }

        setServiceIds(tmpIds);
        setServiceNames(tmpNames);
        setServicePrices(tmpPrices);
    }

    const handleNext = () => {
        handleChange('serviceIds', serviceIds);
        handleChange('serviceNames', serviceNames);
        handleChange('servicePrices', servicePrices);
        nextStep();
    }

    const progressBarStyle = {
        width: "60%"
    };

    const textAreaStyle = {
        minWidth: "100%"
    };

    return (
        <div className="container h-100 p-5 ">
            <div className="card w-75 h-100 m-auto">
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
                <div className="card-body overflow-auto">
                    <div className="px-3 h-100">
                        <div className="h-75 overflow-auto">
                            <table id="t1" className="table table-hover table-dark">
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
                                                        <input className="form-check-input"
                                                               type="checkbox"
                                                               value={service.id}
                                                               defaultChecked={values.serviceIds[services?.indexOf(service)]}
                                                               onChange={(e) => handleService(e.target.value, service.name, service.price, services?.indexOf(service))}
                                                        />
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
                        <div className="pt-5 h-25">
                            <span>Additional information:</span>
                            <textarea
                                style={textAreaStyle}
                                value={values.additionalInformation}
                                onChange={(e) => handleChange('additionalInformation', e.target.value)}
                                rows={4}
                            />
                        </div>
                    </div>
                </div>
                <div className="card-footer">
                    <button className="btn btn-primary" onClick={() => prevStep()}>Back</button>
                    <button className="btn btn-primary float-end" onClick={() => handleNext()}>Next</button>
                </div>
            </div>
        </div>
    );
}

export default ServiceComponent;