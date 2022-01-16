import * as React from 'react';

interface Props {
    prevStep: () => void;
    nextStep: () => void;
    values: any
}

const BookingSummaryComponent = ({ prevStep, nextStep, values }: Props) => {
        return (
            <div>
                <p>{values.firstName}</p>
            </div>
        );
}

export default BookingSummaryComponent;