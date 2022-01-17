import React, {useEffect, useState} from 'react';
import {RoomCategoryDTO} from "../openapi/ts_openapi_client";
import BookingService from "../services/BookingService";

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
                    <div className="p-2 w-50 m-auto">
                            {
                                roomCategories?.map(roomCategory =>
                                    <div className="input-group p-3" key={roomCategory.id}>
                                        <span className="input-group-text col">{roomCategory.name}</span>
                                        <input
                                            defaultValue={values.roomCategoryAmounts[roomCategories?.indexOf(roomCategory)]}
                                            onChange={(e) => handleAmount(parseInt(e.target.value), roomCategories?.indexOf(roomCategory))}
                                            className="form-control"
                                            type="number"
                                            min="0"
                                            placeholder="0"
                                        />
                                    </div>
                                )
                            }
                            <span className="text-danger">{categoryError}</span>
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