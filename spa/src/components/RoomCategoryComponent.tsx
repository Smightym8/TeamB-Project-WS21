import React, {useEffect, useState} from 'react';
import {RoomCategoryDTO} from "../openapi/ts_openapi_client";
import BookingService from "../services/BookingService";

interface Props {
    prevStep: () => void;
    nextStep: () => void;
    handleChange: (input: any) => (e: any) => void;
    values: any
}

const RoomCategoryComponent = ({ prevStep, nextStep, handleChange, values }: Props) => {
    const [roomCategories, setRoomCategories] = useState<RoomCategoryDTO[]>();

    useEffect(() => {
        fetchRoomCategories();
    }, []);

    const fetchRoomCategories = () => {
        BookingService.fetchAllRoomCategoriesRest().then(response => {
            setRoomCategories(response);
        });
    }

    const cardStyle = {
        minHeight: "50vh",
        margin: "auto"
    }

    const progressBarStyle = {
        width: "40%"
    };

    return (
        <div className="div-width-s">
            <div className="card" style={cardStyle}>
                <div className="card-header">
                    <div className="d-flex justify-content-between align-items-center">
                        <div>
                            <span className="h4 align-middle">Create booking - room categories</span>
                        </div>
                    </div>
                    <br />
                    <div className="progress">
                        <div className="progress-bar" role="progressbar" style={progressBarStyle}>2/5</div>
                    </div>
                </div>

                <div className="card-body px-5 py-4">
                    {
                        roomCategories?.map(roomCategory =>
                            <div className="input-group mb-3" key={roomCategory.id}>
                                <span className="input-group-text col-5">{roomCategory.name}</span>
                                <input
                                    onChange={handleChange('roomCategoryAmounts')}
                                    className="form-control"
                                    type="number"
                                    min="0"
                                    placeholder="0"/>
                            </div>
                        )
                    }
                </div>

                <div className="card-footer">
                        <button className="btn btn-primary" onClick={() => prevStep()}>Back</button>
                        <button className="btn btn-primary float-end" onClick={() => nextStep()}>Next</button>
                </div>
            </div>

        </div>
    );
}

export default RoomCategoryComponent;