import React, {useEffect, useState} from 'react';
import {RoomCategoryDTO} from "../openapi/ts_openapi_client";
import BookingService from "../services/BookingService";

import SR01 from '../images/SR/SR01.jpg'
import SR02 from '../images/SR/SR02.jpg'
import SR03 from '../images/SR/SR03.jpg'

import DR01 from '../images/DR/DR01.jpg'
import DR02 from '../images/DR/DR02.jpg'
import DR03 from '../images/DR/DR03.jpg'

import JS01 from '../images/JS/JS01.jpg'
import JS02 from '../images/JS/JS02.jpg'
import JS03 from '../images/JS/JS03.jpg'

import SS01 from '../images/SS/SS01.jpg'
import SS02 from '../images/SS/SS02.jpg'
import SS03 from '../images/SS/SS03.jpg'

interface Props {
    prevStep: () => void;
    nextStep: () => void;
    handleChange: (input: string, value: any) => void;
    values: any
}

const RoomCategoryComponent = ({ prevStep, nextStep, handleChange, values }: Props) => {
    const [roomCategories, setRoomCategories] = useState<RoomCategoryDTO[]>();
    const [categoryAmounts, setCategoryAmounts] = useState<number[]>(values.roomCategoryAmounts);
    const [categoryNames, setCategoryNames] = useState<string[] | undefined[]>(values.roomCategoryNames);
    const [categoryIds, setCategoryIds] = useState<string[] | undefined[]>(values.roomCategoryIds);
    const [categoryError, setCategoryError] = useState<string>('');

    useEffect(() => {
        fetchRoomCategories();
    }, []);

    const fetchRoomCategories = () => {
        BookingService.fetchAllRoomCategoriesRest().then(response => {
            setRoomCategories(response);
        });
    }

    const handleAmount = (value: number, index: number) => {
        let tmpAmounts = categoryAmounts;
        let tmpNames = categoryNames;
        let tmpIds = categoryIds;

        if (value > 0 && roomCategories) {
            tmpAmounts[index] = value;
            tmpIds[index] = roomCategories[index].id;
            tmpNames[index] = roomCategories[index].name;
        } else {
            tmpAmounts.splice(index, 1);
            tmpIds.splice(index, 1);
            tmpNames.splice(index, 1);
        }

        setCategoryAmounts(tmpAmounts);
        setCategoryNames(tmpNames);
        setCategoryIds(tmpIds);
    }

    const handleNext = () => {
        let isValid = true;
        let categoryErrorMsg: string = '';

        let sum = 0;
        if (categoryAmounts.length > 0) {
            sum = categoryAmounts.reduce((a, b) => a + b);
        }

        if (sum === 0) {
            isValid = false;
            categoryErrorMsg = 'You have to select at least 1 room category!'
        }

        if (!isValid) {
            setCategoryError(categoryErrorMsg);
        } else {
            handleChange('roomCategoryIds', categoryIds);
            handleChange('roomCategoryNames', categoryNames);
            handleChange('roomCategoryAmounts', categoryAmounts);
            nextStep();
        }
    }

    const [FIPath] = useState<string[]>([SR01, DR01, JS01, SS01]);
    const [SIPath] = useState<string[]>([SR02, DR02, JS02, SS02]);
    const [TIPath] = useState<string[]>([SR03, DR03, JS03, SS03]);

    const progressBarStyle = {
        width: "40%"
    };

    return (
        <div className="container h-100 p-5 ">
            <div className="card w-75 h-100 m-auto">
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
                <div className="card-body overflow-auto">
                    <div className="px-2 h-100">
                        <span className="text-danger">{categoryError}</span>
                            {
                                roomCategories?.map(roomCategory =>
                                    <div className="d-flex py-4">
                                        <div className="w-50 pe-2">
                                            <div className="d-flex input-group" key={roomCategory.id}>
                                                <span className="input-group-text col">{roomCategory.name}</span>
                                                <input
                                                    defaultValue={values.roomCategoryAmounts[roomCategories?.indexOf(roomCategory)]}
                                                    onChange={(e) => handleAmount(parseInt(e.target.value), roomCategories?.indexOf(roomCategory))}
                                                    className="form-control text-end maxlen"
                                                    type="number"
                                                    min="0"
                                                    placeholder="0"
                                                />
                                            </div>
                                            <div className="d-flex p-3">
                                                <span>{roomCategory.description}</span>
                                            </div>
                                        </div>

                                        <div className="w-50 ps-2">
                                            <div id={"carousel" + roomCategories?.indexOf(roomCategory)} className="carousel slide carousel-fade overflow-hidden" data-bs-interval="false" data-interval="false">
                                                <div className="carousel-inner h-100">
                                                    <div className="carousel-item h-100 active">
                                                        <img src={FIPath[roomCategories?.indexOf(roomCategory)]} className="d-block pic" alt="image"/>
                                                    </div>
                                                    <div className="carousel-item h-100">
                                                        <img src={SIPath[roomCategories?.indexOf(roomCategory)]} className="d-block pic" alt="image"/>
                                                    </div>
                                                    <div className="carousel-item h-100">
                                                        <img src={TIPath[roomCategories?.indexOf(roomCategory)]} className="d-block pic" alt="image"/>
                                                    </div>
                                                </div>
                                                <button className="carousel-control-prev" data-bs-target={"#carousel" + roomCategories?.indexOf(roomCategory)} data-bs-slide="prev">
                                                    <span className="carousel-control-prev-icon"/>
                                                </button>
                                                <button className="carousel-control-next" data-bs-target={"#carousel" + roomCategories?.indexOf(roomCategory)} data-bs-slide="next">
                                                    <span className="carousel-control-next-icon"/>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                )
                            }
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

export default RoomCategoryComponent;